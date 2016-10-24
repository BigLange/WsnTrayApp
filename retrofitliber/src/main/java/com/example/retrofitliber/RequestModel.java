package com.example.retrofitliber;

import android.content.Context;

import com.example.retrofitliber.exception.LackOfConditionsException;
import com.example.retrofitliber.http_factory.RetrofitServiceFactory;
import com.example.retrofitliber.my_rxjava.MyFunc1;
import com.example.retrofitliber.my_rxjava.MySubscriber;
import com.example.retrofitliber.utils.StringUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hu on 2016/10/13.
 */

public class RequestModel<T> {


    private Builder builder;

    RequestModel(Builder builder){
        this.builder = builder;
    }


    /**
     *  普通的get请求   service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void doGetRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .doGetRetObs(builder.urlSuffix);
        publicSteps(observable);
    }


    /**
     *  get请求，请求参数衔接在url后面   service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void doGetValueToQueryMapRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .doGetValueToQueryMapRetObs(builder.urlSuffix,builder.queryMap);
        publicSteps(observable);
    }

    /**
     *  post请求  service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void doPostRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .doPostRetObs(builder.urlSuffix);
        publicSteps(observable);
    }


    /**
     *  post请求 请求参数丢在header里面  service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void doPostValueToHeaderRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .doPostValueToHeaderRetObs(builder.urlSuffix,builder.headerMap);
        publicSteps(observable);
    }


    /**
     *  post请求 请求参数丢在body里面 service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void doPostValueToBodyRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .doPostValueToBodyRetObs(builder.urlSuffix,builder.fieldMap);
        publicSteps(observable);
    }


    /**
     *  post请求 请求参数丢在body和header  service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void doPostValueToHeaderAndBodyRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .doPostValueToHeaderAndBodyRetObs(builder.urlSuffix,builder.headerMap,builder.fieldMap);
        publicSteps(observable);
    }

    /**
     *  post请求 请求参数丢在body和query里面  service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void doPostValueToBodyAndQueryRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .doPostValueToBodyAndQueryRetObs(builder.urlSuffix,builder.queryMap,builder.fieldMap);
        publicSteps(observable);
    }


    /**
     *  post请求 请求值丢在body里面，不是键值对形式 service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void doPostBodyRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .doPostBodyRetObs(builder.urlSuffix,builder.bodyVlaue);
        publicSteps(observable);
    }

    /**
     *  post请求 请求参数丢在body和query里面，并且能够传递query  service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void doPostBodyAndQueryRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .doPostBodyAndQueryRetObs(builder.urlSuffix,builder.queryMap,builder.bodyVlaue);
        publicSteps(observable);
    }

    /**
     *  post请求 上传文件  service 返回 Observable
     * @throws LackOfConditionsException 缺少条件时抛出
     */
    public void upLoadFileRetObs()throws LackOfConditionsException{
        isLackOfConditiion();
        Observable<ResponseBody> observable = RetrofitServiceFactory.INSTANCE.getService(builder.application,builder.url)
                .upLoadFileRetObs(builder.urlSuffix,builder.partMap);
        publicSteps(observable);
    }



    /**
     * 请求后返回Observable的处理步骤
     * @param observable
     */
    private void publicSteps(Observable<ResponseBody> observable){
        observable.map(createMyFunc1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber(builder.callback));
    }


    /**
     * 判断这次请求必要的条件是否满足，不满足则抛出异常
     * @throws LackOfConditionsException
     */
    private void isLackOfConditiion()throws LackOfConditionsException{
        if (StringUtil.isEmpty(builder.urlSuffix))
            throw new LackOfConditionsException("this url is empty");
        if (StringUtil.isEmpty(builder.urlSuffix))
            throw new LackOfConditionsException("this url is empty");
        if (null == builder.callback)
            throw new LackOfConditionsException("this url is empty");
    }

    /**
     *  创建Func1对象
     * @return
     */
    private MyFunc1 createMyFunc1(){
        MyFunc1 myFunc1 = new MyFunc1.Builder()
                .setResponseCodeKey(builder.responseCodeKey)
                .setErrorMsgKey(builder.responseMsgKey)
                .setDataKey(builder.responseDataKey)
                .setSuccessCode(builder.successCode)
                .setType(createType())
                .builder();
        return myFunc1;
    }

    /**
     * 创建解析的type类型
     * @return
     */
    private Type createType(){
        return new TypeToken<T>(){}.getType();
    }


    /**
     * builder类，用于构建整个Request对象
     */
    public static class Builder{
        private Map<String,Object> queryMap = new HashMap<>();
        private Map<String,Object> headerMap = new HashMap<>();
        private Map<String,Object> fieldMap = new HashMap<>();
        private Map<String, RequestBody> partMap = new HashMap<>();

        private ModelCallback callback;

        private String bodyVlaue;
        private String urlSuffix;
        private String url;
        private String responseCodeKey;
        private String responseMsgKey;
        private String responseDataKey;
        private String successCode;

        private Context application;


        public Builder(Context context){
            application = context.getApplicationContext();
        }
        public Builder setUrlSuffix(String url){
            urlSuffix = url;
            return this;
        }

        public Builder setUrl(String url){
            this.url = url;
            return this;
        }

        public Builder setQueryMap(Map<String,Object> queryMap){
            this.queryMap = queryMap;
            return this;
        }

        public Builder setHeaderMap(Map<String,Object> headerMap){
            this.headerMap = headerMap;
            return this;
        }

        public Builder setFieldMap(Map<String,Object> fieldMap){
            this.fieldMap = fieldMap;
            return this;
        }

        public Builder setPartMap(Map<String, RequestBody> partMap){
            this.partMap = partMap;
            return this;
        }

        public Builder setBodyValue(String bodyValue){
            this.bodyVlaue = bodyValue;
            return this;
        }

        public Builder setFromJsonKey(String responseCodeKey,String responseMsgKey,String responseDataKey){
            this.responseCodeKey = responseCodeKey;
            this.responseMsgKey = responseMsgKey;
            this.responseDataKey = responseDataKey;
            return this;
        }

        public Builder setCallback(ModelCallback callback){
            this.callback = callback;
            return this;
        }

        public Builder setSuccessCode(String successCode){
            this.successCode = successCode;
            return this;
        }


        public RequestModel builder(){
            return new RequestModel(this);
        }

    }


    public interface ModelCallback{
        void requestSuccess(Object data);

        void requestFail(Map<String, Object> errorMsg);
    }

}
