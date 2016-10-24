package com.example.think.wsntrayapp.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcV;
import android.os.Handler;
import android.util.Log;

import com.example.think.wsntrayapp.utils.ToastUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Think on 2016/10/20.
 */

public class NfcUtil {

    private final String TAG = "nfcUti tag";

    //nfc的读取和写入模式
    public static final byte s_device_read_single_block_command = 0x20;
    public static final byte s_device_read_multi_block_command = 0x23;
    public static final byte s_device_write_single_block_command = 0x21;

    //当前NFC的连接的flag
    public static final byte s_device_flag = 0x22;
    public static final byte s_device_flag_with_sec = 0x62;

    //三种ENF的状态模式
    public static final String NFC_CONNECTED_MESSAGE = "cn.wsn.parkingproject.nfc.connected";
    public static final String NFC_DISCONNECTED_MESSAGE = "cn.wsn.parkingproject.nfc.disconnected";
    public static final String NFC_REFRESH_MESSAGE = "cn.wsn.parkingproject.nfc.refresh";

    private Activity activity;
    private Handler handler = new Handler();
    private Timer queryTimer;
    private TimerTask queryTimerTask;

    private NfcV nfcV;
    private NfcAdapter adapter;

    private NfcStateCallback callback;

    public NfcUtil(NfcStateCallback callback,Activity activity){
        this.callback = callback;
        this.activity = activity;
        initAdapter();
    }

    public void startNfcListener() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                final PendingIntent m_pending_intent = PendingIntent
                        .getActivity(activity, 0, new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                adapter.enableForegroundDispatch(activity, m_pending_intent,
                        new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
                                new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)},
                        new String[][]{new String[]{Ndef.class.getName()}});
            }
        });
    }

    private void initAdapter() {
        adapter = NfcAdapter.getDefaultAdapter(activity);
        if (adapter == null){
            ToastUtil.showToast(activity,"不支持NFC");
            activity.finish();
            return;
        }
        if (!adapter.isEnabled()){
            ToastUtil.showToast(activity,"请先打开NFC");
            activity.finish();
            return;
        }

        startNfcListener();
    }


    /**
     * 接收到NFC的意图,并根据action做不同的处理
     * @param intent
     */
    public void ReceiveIntent(Intent intent){
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)){
            connectionNfc(intent);
        }else if (NfcAdapter.ACTION_ADAPTER_STATE_CHANGED.equals(action)){
            nfcStateChanged(intent);
        }
    }

    private void nfcStateChanged(Intent intent) {
        int state = intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE,-1);
        if (NfcAdapter.STATE_TURNING_OFF == state) {
            nfcInteractiveFail();
        }
    }


    public void nfcInteractiveFail(){
        stopTimer();
        callback.nfcStateChanged(NFC_DISCONNECTED_MESSAGE);
    }


    //停止NFC的监听
    public void stopNfcListener() {
        if(adapter != null && adapter.isEnabled())
            adapter.disableForegroundDispatch(activity);
    }

    /**
     * 连接NFC 并且打印出NFC的一些信息
     * @param intent
     */
    private void connectionNfc(Intent intent){
        Tag tagFormIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        for (String tech:tagFormIntent.getTechList()) {
            Log.e(TAG, tech);
        }

        NdefFormatable ndef = NdefFormatable.get(tagFormIntent);
        if (null == ndef) return;
        try {
            ndef.close();
            nfcV = NfcV.get(tagFormIntent);
            if (nfcV.isConnected()) nfcV.close();//如果正在连接，就断开
            nfcV.connect();
            callback.nfcStateChanged(NFC_CONNECTED_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public byte[] readDataMulti(byte startBtye,byte dataLength){
        byte[] cmd = new byte[12];
        byte[] nfcId = nfcV.getTag().getId();
        cmd[0] = s_device_flag;
        cmd[1] = s_device_read_multi_block_command;
        System.arraycopy(nfcId, 0, cmd, 2, nfcId.length);
        setStartAddress(cmd,startBtye);
        cmd[11] = dataLength;
        try {
            byte[] responseByte = nfcV.transceive(cmd);
            if (0!=responseByte[0]) {
                startNfcListener();
                return null;
            }
            return  responseByte;
        } catch (IOException e) {
            nfcInteractiveFail();
            e.printStackTrace();
            return null;
        }
    }


    public byte[] readDataSingle(byte startByte){
        byte[] cmd = new byte[11];
        byte[] nfcId = nfcV.getTag().getId();
        cmd[0] = s_device_flag;
        cmd[1] = s_device_read_single_block_command;
        System.arraycopy(nfcId, 0, cmd, 2, nfcId.length);
        setStartAddress(cmd,startByte);
        try {
            byte[] responseByte = nfcV.transceive(cmd);
            if (0!=responseByte[0]) {
                nfcInteractiveFail();
                return null;
            }
            return  responseByte;
        } catch (IOException e) {
            nfcInteractiveFail();
            e.printStackTrace();
            return null;
        }
    }


    public boolean writeData(byte startByte,byte data[]){
        byte[] cmd = new byte[11+data.length];
        byte[] nfcId = nfcV.getTag().getId();
        cmd[0] = s_device_flag;
        cmd[1] = s_device_write_single_block_command;
        System.arraycopy(nfcId, 0, cmd, 2, nfcId.length);
        setStartAddress(cmd,startByte);
        System.arraycopy(data, 0, cmd, 11, data.length);
        try {
            byte[] response = nfcV.transceive(cmd);
            if (0 != response[0]) {
                nfcInteractiveFail();
                return false;
            }
            return true;
        } catch (IOException e) {
            nfcInteractiveFail();
            e.printStackTrace();
            return false;
        }
    }


    public void stopTimer(){
        if(queryTimerTask != null)
        {
            queryTimerTask.cancel();
            queryTimerTask = null;
        }

        if(queryTimer != null) {
            queryTimer.cancel();
            queryTimer = null;
        }
    }


    private void setStartAddress(byte[] sendData,byte startArr){
        sendData[10] =startArr;
    }




    public interface NfcStateCallback{

        void nfcStateChanged(String type);
    }

}
