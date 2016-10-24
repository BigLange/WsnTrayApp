package com.example.retrofitliber.exception;

/**
 * 缺少条件的时候throw出来的异常
 * Created by hu on 2016/10/14.
 */

public class LackOfConditionsException extends Exception {

    private String msg;

    public LackOfConditionsException(String msg){
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
