package com.example.think.wsntrayapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.adapterlib.adapter.LGAdapter;
import com.example.adapterlib.converter.Converter;
import com.example.adapterlib.holder.Holder;
import com.example.adapterlib.holder.ViewHolder;
import com.example.think.wsntrayapp.activity.BaseActivity;
import com.example.think.wsntrayapp.base_view.HeadView;
import com.example.think.wsntrayapp.bean.TrayMsgBean;

import java.util.ArrayList;

public class SelectTrayActivity extends BaseActivity implements Converter<TrayMsgBean>
        ,AdapterView.OnItemClickListener,View.OnClickListener{

    private ListView trayList;
    private HeadView headView;
    private Button inputTrayBtn;

    private LGAdapter<TrayMsgBean> adapter;

    private ArrayList<TrayMsgBean> trayMsgArr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createValue();
        adapter = new LGAdapter<>(this,R.layout.tray_list_item,this,trayMsgArr);
        trayList.setAdapter(adapter);
    }

    private void createValue() {

        trayMsgArr = new ArrayList<>();
        for (int i=0;i<10;i++){
            TrayMsgBean trayMsg = new TrayMsgBean();
            trayMsg.setTrayName("托盘-"+i);
            trayMsgArr.add(trayMsg);
        }
    }

    @Override
    protected void initView() {
        trayList = findView(R.id.select_tray_list);
        inputTrayBtn = findView(R.id.input_tray_btn);
    }

    @Override
    protected void initEvent() {
        trayList.setOnItemClickListener(this);
        inputTrayBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getHeadColor() {
        return R.color.colorGray_9;
    }

    @Override
    public void convert(Holder holder, TrayMsgBean item) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.setTextValue(R.id.tray_item_title,item.getTrayName());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,InventoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,WriterTrayIdActivity.class);
        startActivity(intent);
    }
}
