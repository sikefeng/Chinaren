/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sikefeng.chinaren.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;


public class SimpleSheetAdapter extends BGARecyclerViewAdapter<String> {

    /**
     * 功能描述：构造函数
     * <br>创建时间： 2017-07-03 10:11:15

     * @param recyclerView RecyclerView组件
     */
    public SimpleSheetAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.view_simple_sheet);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String s) {
        helper.setText(R.id.tv_txt, s);
        helper.getView(R.id.widLine).setVisibility(position == getItemCount()-1 ? View.VISIBLE : View.GONE);
    }
}
