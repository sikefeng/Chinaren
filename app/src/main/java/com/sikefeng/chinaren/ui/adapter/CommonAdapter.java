package com.sikefeng.chinaren.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by sikefeng on 2016/8/22.
 */
public abstract  class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    private int layoutId;
    protected LayoutInflater inflater;

    public CommonAdapter(Context mContext, List<T> mDatas, int layoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.layoutId=layoutId;
        inflater= LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder=ViewHolder.get(mContext,convertView,parent,layoutId,position);
        convert(holder,getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder,T t);
}
