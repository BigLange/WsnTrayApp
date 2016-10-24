package com.example.retrofitliber.http_factory;

import android.content.Context;

import com.example.retrofitliber.cache.CacheProvide;
import com.example.retrofitliber.interceptor.CacheInterceptor;
import com.example.retrofitliber.interceptor.HttpLoggingInterceptor;
import com.example.retrofitliber.interceptor.RetryAndChangeIpInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by hu on 2016/10/10.
 */

public enum OKHttpFactory {

    INSTANCE;

    private OkHttpClient client;

    private static final int TIMEOUT_READ = 25;
    private static final int TIMEOUT_CONNECTION = 25;


    public OkHttpClient getClient(Context context){
        if (null != client) return client;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(CacheProvide.provideCache(context))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        return client;
    }
}
