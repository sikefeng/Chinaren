package com.sikefeng.chinaren.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by sikefeng on 2016/8/22.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private int mPosition;

    public  ViewHolder(Context context, ViewGroup parent, int layoutId, int position){
           this.mViews=new SparseArray<View>();
           this.mPosition=position;
           mConvertView= LayoutInflater.from(context).inflate(layoutId,parent,false);
           mConvertView.setTag(this);
    }
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){
        if (convertView==null){
            return new ViewHolder(context,parent,layoutId,position);
        }else{
            ViewHolder holder= (ViewHolder) convertView.getTag();
            holder.mPosition=position;
            return holder;
        }
    }
    //通过viewId获取控件
    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if (view==null){
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }

    //设置TextView
    public ViewHolder setText(int viewId,String text){
        TextView tv=getView(viewId);
        tv.setText(text);
        return this;
    }
    //设置ImageView
    public ViewHolder setImageResource(int viewId,int resId){
        ImageView imageView=getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView imageView=getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }
    public ViewHolder setImageURL(int viewId, String url){
        ImageView imageView=getView(viewId);
//        ImageLoader.getInstance().displayImage(url, imageView);
        return this;
    }
}
