package com.example.think.wsntrayapp.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.think.wsntrayapp.R;
import com.example.think.wsntrayapp.utils.SystemStatusManager;


/**
 * Created by Think on 2016/8/11.
 */
public abstract class BaseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        int headColor = getHeadColor();
        if (headColor==-1) headColor = R.color.transparent;
        new SystemStatusManager(this).setTranslucentStatus(headColor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityManager.getInstance().addActivity(this);
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract int getLayoutId();

    protected abstract int getHeadColor();

    protected <T extends View>  T findView(int id){
        return (T) findViewById(id);
    }


}
