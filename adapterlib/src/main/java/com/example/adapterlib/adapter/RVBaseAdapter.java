package com.example.adapterlib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.adapterlib.converter.RVConverter;
import com.example.adapterlib.holder.RViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Think on 2016/10/19.
 */

public abstract class RVBaseAdapter<T> extends Adapter<RViewHolder> {

    private Context context;
    private List<T> list;
    private int layoutId;
    private boolean hasHeader;
    private boolean hasFooter;

    private View headerView;
    private View footerView;

    public RVBaseAdapter(Context context){
        this(context,null);
    }

    public RVBaseAdapter(Context context, List<T> list){
       this(context,list,0);
    }


    public RVBaseAdapter(Context context, List<T> list, int layoutId){
        this.context = context;
        if (0 == layoutId)
            layoutId = new LinearLayout(context).getId();
        this.layoutId = layoutId;
        if (null == list)
            list = new ArrayList<>();
        this.list = list;
    }

    public RVBaseAdapter list(List<T> list){
        this.list = list;
        return this;
    }

    public RVBaseAdapter layoutId(int layoutId){
        this.layoutId = layoutId;
        return this;
    }


    public List<? super T> getList() {
        return this.list;
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
    public RVBaseAdapter<T> addHeaderView(View headerView) {
        hasHeader = true;
        this.headerView = headerView;
        return this;
    }

    public RVBaseAdapter<T> addFooterView(View footerView) {
        hasFooter = true;
        this.footerView = footerView;
        return this;
    }

    public View getHeaderView() {
        return headerView;
    }

    public View getFooterView() {
        return footerView;
    }



    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (hasHeader&&viewType==0)
            return new RViewHolder(headerView,context);
        if (hasFooter && (list.size() + (hasHeader ? 1 : 0)) == viewType)
            return new RViewHolder(footerView,context);
        return RViewHolder.get(context,parent,layoutId);
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        if (hasHeader && 0 == position)
            return;
        if (hasFooter && (list.size() + (hasHeader ? 1 : 0)) == position)
            return;
        convert(holder,list.get(position),position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public abstract void convert(RViewHolder holder, T t, int position);
}
