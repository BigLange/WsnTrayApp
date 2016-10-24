package com.example.adapterlib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.adapterlib.converter.Converter;
import com.example.adapterlib.holder.Holder;
import com.example.adapterlib.holder.ViewHolder;

import java.util.List;
import java.util.Set;

/**
 * Created by HuLang on 2016/10/19.
 */

public abstract class LGBaseAdapter<T> extends BaseAdapter{

    private List<T> list;
    private int layoutId;
    private LayoutInflater layoutInflater;
    private Context context;

    public LGBaseAdapter(Context context, List<T> list){
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutId = new LinearLayout(context).getId();
    }

    public LGBaseAdapter(Context context, int layoutId, List<T> list){
        this.layoutId = layoutId;
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }



    public LGBaseAdapter<T> list(List list) {
        this.list = list;
        return this;
    }

    public LGBaseAdapter<T> layout(int itemLayoutId) {
        this.layoutId= itemLayoutId;
        return this;
    }


    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public T getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void replaceData(List<T> list){
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void addListItem(T t){
        list.add(t);
        this.notifyDataSetChanged();
    }

    public void addList(List<T> list){
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    public void addSet(Set<T> set){
        this.list.addAll(set);
        this.notifyDataSetChanged();
    }

    public List<T> getList(){
        return list;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(context,view,layoutId,viewGroup,i);
        convert(holder,getItem(i));
        return holder.getConvertView();
    }

    public abstract void convert(Holder holder,T item);
}
