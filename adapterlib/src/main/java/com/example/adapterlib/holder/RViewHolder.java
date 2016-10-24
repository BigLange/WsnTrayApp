package com.example.adapterlib.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by HuLang on 2016/10/19.
 */

public class RViewHolder extends ViewHolder implements Holder{

    private View convertView;
    private SparseArray<View> views;
    private  Context context;

    /**
     * 构造方法，初始化各个参数
     */
    public RViewHolder(View item,Context context) {
        super(item);
        this.convertView = item;
        this.context = context;
        views = new SparseArray<>();
    }

    public static RViewHolder get(Context context,ViewGroup viewGroup,int layout){
        View item = LayoutInflater.from(context).inflate(layout,viewGroup,false);
        RViewHolder holder = new RViewHolder(item,context);
        return holder;
    }

    @Override
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (null == view){
            view = convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }


    /**
     * 设置通过id直接给text类型的组件设置值
     * @param viewId
     * @param value
     */
    public RViewHolder setTextValue(int viewId,String value){
        TextView textView = getView(viewId);
        textView.setText(value);
        return this;
    }
    /**
     * 设置通过id直接给imageView类型的组件设置Bitmap
     * @param viewId
     * @param bitmap
     */
    public RViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 通过id直接给imageView类型的组件设置资源id值
     * @param viewId
     * @param resId
     */
    public RViewHolder setImageResource(int viewId,int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 通过id直接给imageView 用glide加载url
     * @param viewId
     * @param url
     */
    public RViewHolder setImageUrl(int viewId,String url){
        ImageView imageView = getView(viewId);
        Glide.with(context).load(url).into(imageView);
        return this;
    }

    /**
     * 通过id直接给imageView 用glide加载url
     * @param viewId
     * @param url
     * @param placeholderImgId
     * @param errorImgId
     */
    public RViewHolder setImageUrl(int viewId,String url,int placeholderImgId,int errorImgId){
        ImageView imageView = getView(viewId);
        Glide.with(context)
                .load(url)
                .placeholder(placeholderImgId)
                .error(errorImgId)
                .into(imageView);
        return this;
    }

    @Override
    public View getConvertView() {
        return convertView;
    }
}
