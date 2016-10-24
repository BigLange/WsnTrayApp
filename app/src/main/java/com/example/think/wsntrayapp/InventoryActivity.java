package com.example.think.wsntrayapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.adapterlib.adapter.LGAdapter;
import com.example.think.wsntrayapp.activity.BaseActivity;
import com.example.think.wsntrayapp.adapter.InventoryConverter;
import com.example.think.wsntrayapp.bean.InventoryMsgBean;

import java.util.ArrayList;

public class InventoryActivity extends BaseActivity {

    private ArrayList<InventoryMsgBean> inventoryMsgBeanArr;
    private ListView listView;
    private LGAdapter<InventoryMsgBean> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createValue();
        adapter = new LGAdapter<>(
                this,
                R.layout.inventory_list_item,
                new InventoryConverter(),
                inventoryMsgBeanArr);
        listView.setAdapter(adapter);
    }

    private void createValue() {
        inventoryMsgBeanArr = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            InventoryMsgBean data = new InventoryMsgBean();
            data.setTitle("测试数据"+i);
            inventoryMsgBeanArr.add(data);
        }
    }

    @Override
    protected void initView() {
        listView = findView(R.id.inventory_list);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inventory;
    }

    @Override
    protected int getHeadColor() {
        return R.color.colorGray_9;
    }
}
