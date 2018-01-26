package com.sikefeng.chinaren.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.utils.ToastUtils;

import java.util.ArrayList;


public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.MyViewHolder> {

    private ArrayList<ShareBean> data = null;

    public RecyclerGridAdapter(ArrayList<ShareBean> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.share_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        viewHolder.position = position;
        ShareBean shareBean = data.get(position);
        viewHolder.tv_share_name.setText(shareBean.getShareName());
        viewHolder.ivIcon.setImageResource(shareBean.getImgRes());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivIcon;
        public TextView tv_share_name;

        // 埋入一个值position,记录在RecyclerView的位置。
        int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            // 为RecyclerView的子item增加点击事件。
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareBean shareBean=data.get(position);
                    ToastUtils.showShort(shareBean.getShareName());
                }
            });

            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_share_name = (TextView) itemView.findViewById(R.id.tv_share_name);
        }
    }


    public static class ShareBean {
        int imgRes;
        String shareName;

        public int getImgRes() {
            return imgRes;
        }

        public void setImgRes(int imgRes) {
            this.imgRes = imgRes;
        }

        public String getShareName() {
            return shareName;
        }

        public void setShareName(String shareName) {
            this.shareName = shareName;
        }
    }


}