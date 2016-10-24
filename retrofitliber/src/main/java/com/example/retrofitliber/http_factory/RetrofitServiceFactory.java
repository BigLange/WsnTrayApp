package com.example.retrofitliber.http_factory;

import android.content.Context;

import com.example.retrofitliber.service.RetrofitHttpService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Think on 2016/10/10.
 */

public enum RetrofitServiceFactory {

    INSTANCE;

    private RetrofitHttpService service;

    RetrofitServiceFactory(){

    }


    public RetrofitHttpService getService(Context context,String url){
        if (null != service) return service;
        service = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OKHttpFactory.INSTANCE.getClient(context))
                .build()
                .create(RetrofitHttpService.class);
        return service;
    }
}
