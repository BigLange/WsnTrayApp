package com.example.retrofitliber.my_rxjava;

import android.util.Log;

import com.example.retrofitliber.RequestModel;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by hu on 2016/10/14.
 */

public class MySubscriber extends Subscriber<Map<String,Object>>{

    private final String TAG = MySubscriber.class.getName();
    public static final Integer NET_WORK_ERROR_CODE = 12345;

    public RequestModel.ModelCallback callback;


    public MySubscriber(RequestModel.ModelCallback callback){
        this.callback = callback;
    }




    @Override
    public void onCompleted() {
        Log.e(TAG,"start completed");
    }

    @Override
    public void onError(Throwable e) {
        Map<String,Object> errorMap = new HashMap<>();
        errorMap.put(MyFunc1.RESPONSE_CODE,NET_WORK_ERROR_CODE.toString());
        errorMap.put(MyFunc1.RESPONSE_MSG,"您的网络可能有点小问题");
        callback.requestFail(errorMap);
    }

    @Override
    public void onNext(Map<String, Object> stringObjectMap) {
        Object data = stringObjectMap.get(MyFunc1.RESPONSE_DATA);
        if (null != data)
            callback.requestSuccess(data);
        else
            callback.requestFail(stringObjectMap);

    }

}
