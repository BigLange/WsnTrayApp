package com.example.think.wsntrayapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.widget.TextView;

import com.example.think.wsntrayapp.R;

/**
 * Created by Think on 2016/7/28.
 */
public class CustomDialog {
    private TextView textView;
    private Dialog dialog;
    private Activity activity;


    public CustomDialog(Activity activity){
        this.activity = activity;
            initDialog();
    }


    private void initDialog() {
        dialog = new Dialog(activity, R.style.Theme_AudioDialog);
        dialog.setContentView(R.layout.common_dlg);
        dialog.setCancelable(false);
        //设置获取dialog的textView对象
        textView = (TextView) dialog.findViewById(R.id.dialog_textview);
    }



    public void setDialogTitle(String title){
        textView.setText(title);
    }

    public void dialogDismiss(){
        dialog.dismiss();
    }

    public void dialogShow(){
        dialog.show();
    }



}
