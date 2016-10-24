package com.example.retrofitliber.cache;

import android.content.Context;

import okhttp3.Cache;

/**
 * Created by hu on 2016/8/12.
 */
public class CacheProvide {

    public static Cache provideCache(Context context) {
        return new Cache(context.getCacheDir(), 50*1024 * 1024);
    }
}
