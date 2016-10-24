package com.example.retrofitliber.interceptor;

/**
 * Created by hu on 2016/9/21.
 */
/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
 * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
public final class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
   String OKHTTPTAG= "okhttp"
;    public enum Level {
        /** 没有log */
        NONE,
        /**
         * 日志请求和响应行。
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * 日志请求和响应行和它们各自的头。
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * 日志请求和响应行和它们各自的标题和机构（如果存在）。
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        /** A {@link Logger} defaults output appropriate for the current platform. */
        Logger DEFAULT = new Logger() {
            @Override public void log(String message) {
                Platform.get().log(INFO, message, null);
            }
        };
    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;

    private volatile Level level = Level.NONE;

    /** Change the level at which this interceptor logs. */
    public HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {//如果类型是不打印的枚举类型的话，就直接返回
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;//当前类型是否为打印请求体枚举类型
        boolean logHeaders = logBody || level == Level.HEADERS;//当前类型是否为打印 体信息枚举类型或打印 头信息

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;//请求体是否存在

        Connection connection = chain.connection();//连接
        //判断这次连接是否成功，如果成功，返回自己的连接类型，如果失败则返回http 1.1类型
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        //添加 请求方式  请求路径  请求类型
        String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        //判断如果既不是体信息打印，也不是体信息打印   并且请求体是存在的
        if (!logHeaders && hasRequestBody) {
            //就把请求体的长度添加进这个字段
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        //打印信息
      Log.wtf(OKHTTPTAG,requestStartMessage);


        if (logHeaders) { //当前类型是否为打印 体信息枚举类型或打印 头信息
            if (hasRequestBody) {//请求体是否存在
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {//如果请求体的内容类型不为空的话
                  Log.wtf(OKHTTPTAG,"Content-Type: " + requestBody.contentType());//打印内容类型
                }
                if (requestBody.contentLength() != -1) {//如果请求体内容长度为-1
                  Log.wtf(OKHTTPTAG,"Content-Length: " + requestBody.contentLength());//打印内容长度
                }
            }

            //获得请求请求头
            Headers headers = request.headers();
            //循环请求头
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                //判断请求头键值对的名字不等于Content-Type 不等于Content-Length的时候打印log()
                //equalsIgnoreCase()不考虑大小写，比较两个字符是否相同
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                  Log.wtf(OKHTTPTAG,name + ": " + headers.value(i));
                }
            }

            //如果当前传入的参数是不打印体信息的，或体和头都不打印，则进入判断
            if (!logBody || !hasRequestBody) {
                //打印end 加上请求的方式
              Log.wtf(OKHTTPTAG,"--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {//如果当前请求中的编码格式键值对不为空，且值不等于identity则进入判断
                //答应当前的eng 加上请求方式，加上 (encoded body omitted)
              Log.wtf(OKHTTPTAG,"--> END " + request.method() + " (encoded body omitted)");
            } else {

                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);//这里应该是吧请求体写入的buffer中去

                //
                Charset charset = UTF8;//设置当前字符集类型为UTF-8的类型
                MediaType contentType = requestBody.contentType();//获取当前内容类型
                if (contentType != null) {//如果内容类型不等于空的话
                    charset = contentType.charset(UTF8);//设置字符集为UTF-8的格式
                }

              Log.wtf(OKHTTPTAG,"");//打印一个空行出来
                if (isPlaintext(buffer)) {//判断如果body里面有可读字符(二进制就不可读)
                  Log.wtf(OKHTTPTAG,buffer.readString(charset));//按照UTF-8的类型打印请求体
                  Log.wtf(OKHTTPTAG,"--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");//打印end结束,最后打印内容长度
                } else {
                  Log.wtf(OKHTTPTAG,"--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");//打印请求方式，并且打印end+请求提长度
                }
            }
        }

        long startNs = System.nanoTime();//返回最准确的可用系统计时器的当前值，以毫微秒为单位。
        Response response;
        try {
            response = chain.proceed(request);//获得当前返回的响应体
        } catch (Exception e) {
          Log.wtf(OKHTTPTAG,"<-- HTTP FAILED: " + e);//打印当前错误
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);//请求响应的时间减去请求开始的时间

        ResponseBody responseBody = response.body();//获取到当前的请求体
        long contentLength = responseBody.contentLength();//获取当前请求提的长度
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
      Log.wtf(OKHTTPTAG,"<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (!logHeaders ? ", "
                + bodySize + " body" : "") + ')');//打印出当前获取到的请求体信息

        if (logHeaders) {//当前是否需要打印头和体
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
              Log.wtf(OKHTTPTAG,headers.name(i) + ": " + headers.value(i));//打印请求信息
            }

            //判断如果当前类型为不打印体信息类型，或者没有体信息
            if (!logBody || !HttpHeaders.hasBody(response)) {
              Log.wtf(OKHTTPTAG,"<-- END HTTP");//直接打印end
            } else if (bodyEncoded(response.headers())) {//如果当前的响应头中的 内容编码类型不为空或者不为identity
              Log.wtf(OKHTTPTAG,"<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();//获取到 缓冲源
                source.request(Long.MAX_VALUE); // Buffer the entire body. 缓冲整个身体
                Buffer buffer = source.buffer(); //变成buffer

                Charset charset = UTF8;//获取到UTF-8的编码转换成的字符集对象
                MediaType contentType = responseBody.contentType();//获取到当前的内容类型
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);//获取到当前内容类型的字符集类型
                    } catch (UnsupportedCharsetException e) {
                        //进异常了则打印出异常里面的信息
                      Log.wtf(OKHTTPTAG,"");
                      Log.wtf(OKHTTPTAG,"Couldn't decode the response body; charset is likely malformed.");
                      Log.wtf(OKHTTPTAG,"<-- END HTTP");

                        return response;
                    }
                }

                //判断体信息里面的文字是否为人类可读文字
                if (!isPlaintext(buffer)) {
                    //不能，则打印end 打印一下体信息的长度
                  Log.wtf(OKHTTPTAG,"");
                  Log.wtf(OKHTTPTAG,"<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                //如果体信息长度不等于0的话
                if (contentLength != 0) {
                  Log.wtf(OKHTTPTAG,"");//换行
                  Log.wtf(OKHTTPTAG,buffer.clone().readString(charset));//打印当前请求体信息
                }

                //最后所有工作处理完了，就结束
              Log.wtf(OKHTTPTAG,"<-- END HTTP (" + buffer.size() + "-byte body)");
            }
        }

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     * 如果问题中的身体可能包含人类可读的文本，则返回真。使用一个小样本
     * 代码点检测中常用的二进制文件签名Unicode控制字符。
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    //判断当前的contentEncoding 是否不等于空 且 类型不为identity
    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
