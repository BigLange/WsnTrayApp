package com.example.retrofitliber.my_rxjava;

import com.example.retrofitliber.utils.StringUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * Created by hu on 2016/10/12.
 */

public class MyFunc1<T> implements Func1<ResponseBody,Map<String,Object>> {

    public final static String RESPONSE_CODE = "response code map key";
    public final static String RESPONSE_MSG = "response msg map key";
    public final static String RESPONSE_DATA = "response data map key";
    public final static Integer JSON_ANALYSIS_ERROR_CODE = 8899;

    private Gson gson;

    private Map<String,Object> responseMap = new HashMap<>();

    private Builder builder;


    private MyFunc1(Builder builder){
        this.builder = builder;
    }

    @Override
    public Map<String,Object> call(ResponseBody responseBody) {
        try {
            judgeKeyIsEmpty();
            JSONObject jsonObject = new JSONObject(responseBody.string());
            String responseCode = jsonObject.getString(builder.responseCodeKey);
            responseMap.put(RESPONSE_CODE,responseCode);
            if (builder.successCode.equals(responseCode)){
                responseMap.put(RESPONSE_DATA,analysisJson(jsonObject.getString(builder.responseDataKey)));
            }else {
                String errorMsg = jsonObject.getString(builder.responseMsgKey);
                responseMap.put(RESPONSE_MSG,errorMsg);
            }
        } catch (JSONException e) {
            responseMap.put(RESPONSE_CODE,JSON_ANALYSIS_ERROR_CODE);
            responseMap.put(RESPONSE_MSG,"数据解析异常");
        } catch (IOException e) {
            responseMap.put(RESPONSE_CODE,JSON_ANALYSIS_ERROR_CODE);
            responseMap.put(RESPONSE_MSG,"服务器返回异常");
        }
        return responseMap;
    }


    private void judgeKeyIsEmpty() throws JSONException{
        if (StringUtil.isEmpty(builder.responseCodeKey))
            throw new JSONException("json analysis error");
        if (StringUtil.isEmpty(builder.responseMsgKey))
            throw new JSONException("json analysis error");
        if (StringUtil.isEmpty(builder.responseDataKey))
            throw new JSONException("json analysis error");
        if (StringUtil.isEmpty(builder.successCode))
            throw new JSONException("json analysis error");
        if (builder.type==null)
            throw new JSONException("json analysis error");
    }


    private T analysisJson(String json){
        if (gson==null)
            gson = new Gson();
        return gson.fromJson(json,builder.type);
    }

    public static class Builder{
        private String responseCodeKey;
        private String responseMsgKey;
        private String responseDataKey;
        private String successCode;
        private Type type;

        public Builder(){}

        public Builder setResponseCodeKey(String responseCodeKey){
            this.responseCodeKey = responseCodeKey;
            return this;
        }

        public Builder setErrorMsgKey(String responseMsgKey){
            this.responseMsgKey = responseMsgKey;
            return this;
        }

        public Builder setDataKey(String responseDataKey){
            this.responseDataKey = responseDataKey;
            return this;
        }


        public Builder setSuccessCode(String successCode){
            this.successCode = successCode;
            return this;
        }


        public Builder setType(Type type){
            this.type = type;
            return this;
        }

        public MyFunc1 builder(){
            return new MyFunc1<>(this);
        }

    }
}
