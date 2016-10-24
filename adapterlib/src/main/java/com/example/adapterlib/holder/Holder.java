package com.example.adapterlib.holder;

import android.view.View;
/**
 * Created by HuLang on 2016/10/19.
 */

public interface Holder {


    /**
     * 通过view id 获取到对应的view
     * @param viewId
     * @param <T>
     * @return
     */
    <T extends View> T getView(int viewId);


    /**
     * 获取到当前的layoutView
     * @return
     */
    View getConvertView();


    /**
     * 获得当前的position
     * @return
     */
    int getPosition();
}
