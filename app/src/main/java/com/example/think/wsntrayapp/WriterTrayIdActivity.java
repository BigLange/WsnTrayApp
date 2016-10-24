package com.example.think.wsntrayapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.think.wsntrayapp.activity.BaseActivity;
import com.example.think.wsntrayapp.dialog.CustomDialog;
import com.example.think.wsntrayapp.nfc.NfcUtil;
import com.example.think.wsntrayapp.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class WriterTrayIdActivity extends BaseActivity implements View.OnClickListener,NfcUtil.NfcStateCallback{

    private  final String TAG = WriterTrayIdActivity.class.getName();
    private EditText writerEditText;
    private Button writerBtn;
    private CustomDialog customDialog;
    private NfcUtil nfcUtil;

    private boolean startWaitNfc = false;
    private String trayId;

    private Timer timer;
    private TimerTask timerTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcUtil = new NfcUtil(this,this);
    }

    @Override
    protected void initView() {
        writerEditText = findView(R.id.write_id_edit);
        writerBtn = findView(R.id.write_id_btn);
        customDialog = new CustomDialog(this);
    }

    @Override
    protected void initEvent() {
        writerBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_writer_tray_id;
    }

    @Override
    protected int getHeadColor() {
        return R.color.colorGray_9;
    }

    @Override
    public void onClick(View view) {
        String deviceId = writerEditText.getText().toString();
        if (deviceId==null||deviceId.length()==0) {
            ToastUtil.showToast(this,"输入ID错误");
            return;
        }
        customDialog.setDialogTitle("开始录入ID");
        customDialog.dialogShow();
        trayId =  deviceId;
        startWaitNfc = true;
        startTimer();
    }

    @Override
    public void nfcStateChanged(String type) {
        if (!startWaitNfc) return;
        stopTimer();
    }

    private void DeviceWriteOver(){
        customDialog.dialogDismiss();
        startWaitNfc = false;
    }

    private void startTimer(){
        timer = new Timer();
        timerTask = new MyTimerTask();
        timer.schedule(timerTask,10000);
    }

    private void stopTimer(){
        if (null != timer){
            timer.cancel();
            timer = null;
        }
        if (null != timerTask){
            timerTask.cancel();
            timerTask = null;
        }
    }


    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            WriterTrayIdActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DeviceWriteOver();
                    ToastUtil.showToast(WriterTrayIdActivity.this,"写入超时失败");
                }
            });
        }
    }
}
