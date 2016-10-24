package com.example.adapterlib.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
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

public class ViewHolder implements Holder {

    private Context context;
    private final SparseArray<View> views;
    private View convertView;
    private int position;


    /**
     * 构造方法，初始化各个参数
     * @param context
     * @param layoutId
     * @param parent
     * @param position
     */
    protected ViewHolder(Context context, int layoutId, ViewGroup parent, int position){
        views = new SparseArray<>();
        this.context = context;
        this.position = position;
        convertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
        convertView.setTag(this);
    }


    /**
     * 获取当前ViewHolder
     * @param context
     * @param convertView
     * @param layoutId
     * @param parent
     * @param position
     * @return
     */
    public static ViewHolder get(Context context,View convertView,int layoutId,ViewGroup parent,int position){
        if (null == convertView){
            return new ViewHolder(context,layoutId,parent,position);
        }
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.position = position;
        return viewHolder;
    }

    /**
     * 通过id 直接获取View
     * @param viewId
     * @param <T>
     * @return
     */
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
    public ViewHolder setTextValue(int viewId, String value){
        TextView textView = getView(viewId);
        textView.setText(value);
        return this;
    }
    /**
     * 设置通过id直接给imageView类型的组件设置Bitmap
     * @param viewId
     * @param bitmap
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 通过id直接给imageView类型的组件设置资源id值
     * @param viewId
     * @param resId
     */
    public ViewHolder setImageResource(int viewId,int resId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 通过id直接给imageView 用glide加载url
     * @param viewId
     * @param url
     */
    public ViewHolder setImageUrl(int viewId,String url){
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
    public ViewHolder setImageUrl(int viewId,String url,int placeholderImgId,int errorImgId){
        ImageView imageView = getView(viewId);
        Glide.with(context)
                .load(url)
                .placeholder(placeholderImgId)
                .error(errorImgId)
                .into(imageView);
        return this;
    }


    /**
     * 获取到布局的view
     * @return
     */
    @Override
    public View getConvertView() {
        return convertView;
    }

    /**
     * 获取到当前view所对应的position
     * @return
     */
    @Override
    public int getPosition() {
        return position;
    }
}
