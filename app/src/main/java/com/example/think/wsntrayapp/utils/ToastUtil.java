package com.example.think.wsntrayapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Think on 2016/6/22.
 */
public class ToastUtil {

    public static void showToast(Context context, String showValue){
        Toast.makeText(context, showValue, Toast.LENGTH_SHORT).show();
    }
}
