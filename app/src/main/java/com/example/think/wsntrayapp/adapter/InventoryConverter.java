package com.example.think.wsntrayapp.adapter;

import android.view.View;
import android.widget.ImageView;

import com.example.adapterlib.converter.Converter;
import com.example.adapterlib.holder.Holder;
import com.example.adapterlib.holder.ViewHolder;
import com.example.think.wsntrayapp.R;
import com.example.think.wsntrayapp.bean.InventoryMsgBean;

/**
 * Created by Think on 2016/10/23.
 */

public class InventoryConverter implements Converter<InventoryMsgBean> {
    @Override
    public void convert(Holder holder, InventoryMsgBean item) {
        ViewHolder viewHolder = (ViewHolder)holder;
//        viewHolder.setTextValue(R.id.tray_item_title,item.getTitle());
        viewHolder.setTextValue(R.id.inventory_goods_name_value,item.getTitle())
                .setTextValue(R.id.inventory_grade_name_value,item.getTitle())
                .setTextValue(R.id.inventory_number_value,item.getTitle())
                .setTextValue(R.id.inventory_goods_class_value,item.getTitle())
                .setTextValue(R.id.inventory_manufactor_value,item.getTitle())
                .setTextValue(R.id.inventory_spec_value,item.getTitle())
                .setTextValue(R.id.inventory_batch_value,item.getTitle())
                .setTextValue(R.id.inventory_goods_suttler_value,item.getTitle())
                .setTextValue(R.id.inventory_validity_time_value,item.getTitle())
                .setTextValue(R.id.inventory_childbirth_data_value,item.getTitle())
                .setTextValue(R.id.inventory_storage_condition_value,item.getTitle());
        View inventoryDataView = viewHolder.getView(R.id.inventory_list_item_msg);
        InventoryListClickListener clickListener = (InventoryListClickListener) inventoryDataView.getTag();
        if (null == clickListener){
            View detailedDataView = viewHolder.getView(R.id.inventory_list_item_id_msg);
            ImageView showDetailedDataIcon = viewHolder.getView(R.id.inventory_list_item_msg_icon);
            clickListener = new InventoryListClickListener(detailedDataView,showDetailedDataIcon);
            inventoryDataView.setOnClickListener(clickListener);
            inventoryDataView.setTag(clickListener);
        }
    }


    private class InventoryListClickListener implements View.OnClickListener{
        private View detailedDataView;
        private ImageView showDetailedDataIcon;

        public InventoryListClickListener(View detailedDataView,ImageView showDetailedDataIcon){
            this.detailedDataView = detailedDataView;
            this.showDetailedDataIcon = showDetailedDataIcon;
        }


        @Override
        public void onClick(View view) {
            int visibilityId = detailedDataView.getVisibility();
            if (View.GONE == visibilityId){
                detailedDataView.setVisibility(View.VISIBLE);
                showDetailedDataIcon.setImageResource(R.drawable.donw_icon);
            }else {
                detailedDataView.setVisibility(View.GONE);
                showDetailedDataIcon.setImageResource(R.drawable.right_icon);
            }
        }
    }
}
