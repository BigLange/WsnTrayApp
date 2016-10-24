package com.example.retrofitliber.service;


import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by hu on 2016/5/17.
 */
public interface RetrofitHttpService {



    /**
     * 最简单的get请求
     * @param url
     * @return 返回的是call<ResponseBody>对象
     */
    @GET()
    Call<ResponseBody> doGet(
            @Url String url
    );

    /**
     * get请求，传递一个map进来，会自动变成url后面的键值对
     * @param url
     * @param queryMap
     * @return 返回的是call<ResponseBody>对象
     */
    @GET()
    Call<ResponseBody> doGetValueToQueryMap(
            @Url String url,
            @QueryMap Map<String, Object> queryMap);


    /**
     * 简单的post请求
     * @param url
     * @return 返回的是call<ResponseBody>对象
     */
    @POST()
    Call<ResponseBody> doPost(
            @Url String url
    );


    /**
     * 简单的post对象，会将请求参数丢在请求头
     * @param url
     * @param headerMap
     * @return 返回的是call<ResponseBody>对象
     */
    @POST()
    Call<ResponseBody>  doPostValueToHeader(
            @Url String url,
            @HeaderMap Map<String, Object> headerMap
    );



    /**
     *  post请求，将键值对丢到body里面
     * @param url
     * @param headerMap
     * @return
     */
    @POST()
    @FormUrlEncoded
    Call<ResponseBody>  doPostValueToBody(
            @Url String url,
            @FieldMap Map<String, Object> headerMap
    );

    /**
     * post提交，但是header和body里面都能提交键值对参数
     * @param url
     * @param headerMap
     * @param fieldMap
     * @return 返回的是call<ResponseBody>对象
     */
    @POST()
    @FormUrlEncoded
    Call<ResponseBody> doPostValueToHeaderAndBody(
            @Url String url,
            @HeaderMap Map<String, Object> headerMap,
            @FieldMap Map<String, Object> fieldMap
    );

    /**
     * post提交，带着query，但是header和body里面都能提交键值对参数
     * @param url
     * @param queryMap
     * @param fieldMap
     * @return 返回的是call<ResponseBody>对象
     */
    @POST()
    @FormUrlEncoded
    Call<ResponseBody> doPostValueToBodyAndQuery(
            @Url String url,
            @QueryMap Map<String, Object> queryMap,
            @FieldMap Map<String, Object> fieldMap
    );

    /**
     * post请求，直接上传字符串，不按照键值对的形式上传
     * @param url
     * @param Body
     * @return 返回的是call<ResponseBody>对象
     */
    @POST()
    Call<ResponseBody> doPostBody(
            @Url String url,
            @Body String Body
    );

    /**
     * post请求,带着url后缀键值对，直接上传字符串，不按照键值对的形式上传
     * @param url
     * @param Body
     * @return 返回的是call<ResponseBody>对象
     */
    @POST()
    Call<ResponseBody> doPostBodyAndQuery(
            @Url String url,
            @QueryMap HashMap<String, Object> queryMap,
            @Body String Body
    );


    /**
     *  文件上传
     * @param url
     * @param partMap
     * @return 返回的是call<ResponseBody>对象
     */
    @POST()
    @Multipart
    Call<ResponseBody> upLoadFile(
            @Url String url,
            @PartMap Map<String, ResponseBody> partMap
    );






    /*-----------------我是分隔符---------------------*/



    /**
     * 最简单的get请求
     * @param url
     * @return 返回的是Observable<JSONObject>对象
     */
    @GET()
    Observable<ResponseBody> doGetRetObs(
            @Url String url
    );

    /**
     * get请求，传递一个map进来，会自动变成url后面的键值对
     * @param url
     * @param queryMap
     * @return 返回的是Observable<JSONObject>对象
     */
    @GET()
    Observable<ResponseBody> doGetValueToQueryMapRetObs(
            @Url String url,
            @QueryMap Map<String, Object> queryMap);


    /**
     * 简单的post请求
     * @param url
     * @return 返回的是Observable<JSONObject>对象
     */
    @POST()
    Observable<ResponseBody> doPostRetObs(
            @Url String url
    );


    /**
     * 简单的post对象，会将请求参数丢在请求头
     * @param url
     * @param headerMap
     * @return 返回的是Observable<JSONObject>对象
     */
    @POST()
    Observable<ResponseBody>  doPostValueToHeaderRetObs(
            @Url String url,
            @HeaderMap Map<String, Object> headerMap
    );


    /**
     *  post请求，将键值对丢到body里面
     * @param url
     * @param headerMap
     * @return
     */
    @POST()
    @FormUrlEncoded
    Observable<ResponseBody>  doPostValueToBodyRetObs(
            @Url String url,
            @FieldMap Map<String, Object> headerMap
    );

    /**
     * post提交，但是header和body里面都能提交键值对参数
     * @param url
     * @param headerMap
     * @param fieldMap
     * @return 返回的是Observable<JSONObject>对象
     */
    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> doPostValueToHeaderAndBodyRetObs(
            @Url String url,
            @HeaderMap Map<String, Object> headerMap,
            @FieldMap Map<String, Object> fieldMap
    );

    /**
     * post提交，带着query，但是header和body里面都能提交键值对参数
     * @param url
     * @param queryMap
     * @param fieldMap
     * @return 返回的是Observable<JSONObject>对象
     */
    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> doPostValueToBodyAndQueryRetObs(
            @Url String url,
            @QueryMap Map<String, Object> queryMap,
            @FieldMap Map<String, Object> fieldMap
    );

    /**
     * post请求，直接上传字符串，不按照键值对的形式上传
     * @param url
     * @param Body
     * @return 返回的是Observable<JSONObject>对象
     */
    @POST()
    Observable<ResponseBody> doPostBodyRetObs(
            @Url String url,
            @Body String Body
    );

    /**
     * post请求,带着url后缀键值对，直接上传字符串，不按照键值对的形式上传
     * @param url
     * @param Body
     * @return 返回的是Observable<JSONObject>对象
     */
    @POST()
    Observable<ResponseBody> doPostBodyAndQueryRetObs(
            @Url String url,
            @QueryMap Map<String, Object> queryMap,
            @Body String Body
    );


    /**
     *  文件上传
     * @param url
     * @param partMap
     * @return 返回的是Observable<JSONObject>对象
     */
    @POST()
    @Multipart
    Observable<ResponseBody> upLoadFileRetObs(
            @Url String url,
            @PartMap Map<String, RequestBody> partMap
    );

}
