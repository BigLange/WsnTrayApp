package com.example.think.wsntrayapp.base_view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.think.wsntrayapp.R;


/**
 * Created by Think on 2016/6/3.
 */
public class HeadView extends LinearLayout implements View.OnClickListener{
    private Context context;

    private ImageView backImgView;
    private TextView textView;
    private String textValue;

    public HeadView(Context context) {
        super(context);
        this.context  = context;
    }

    public HeadView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context  = context;
        TypedArray mTypeArray = context.obtainStyledAttributes(attrs, R.styleable.HeadView);
        textValue = mTypeArray.getString(R.styleable.HeadView_text);
        initView();
        initEvent();
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.head_view_moban,this);
        textView = (TextView)view.findViewById(R.id.id_head_text);
        backImgView = (ImageView)view.findViewById(R.id.id_head_back_img);
        textView.setText(textValue);
    }

    private void initEvent(){
        backImgView.setOnClickListener(this);
    }

    public void setBackImg(Bitmap bitmap){
        backImgView.setImageBitmap(bitmap);
    }

    public void setBackImg(int resID){
        backImgView.setImageResource(resID);
    }

    public void setTitleTextAttribute(int textColor,int textSize){
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
    }

    public void setBackIconHide(){
        backImgView.setVisibility(GONE);
    }

    @Override
    public void onClick(View view) {
        Activity activity = (Activity) context;
        activity.finish();
    }

    public void setText(String text){
        textView.setText(text);
    }

}
