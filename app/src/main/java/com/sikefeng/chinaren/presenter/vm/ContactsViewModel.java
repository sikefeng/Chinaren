/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.presenter.vm;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseViewModel;
import com.sikefeng.chinaren.databinding.FragmentContactsBinding;
import com.sikefeng.chinaren.databinding.ItemFragmentContactsBinding;
import com.sikefeng.chinaren.entity.model.GradeBean;
import com.sikefeng.chinaren.entity.model.GradeListData;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.utils.CommonUtils;

import cn.bingoogolapple.androidcommon.adapter.BGABindingRecyclerViewAdapter;


public class ContactsViewModel extends BaseViewModel {
    /**
     *  我界面数据绑定类
     */
    private FragmentContactsBinding currentBinding;
    /**
     *  用户实体类
     */
    private UserBean user = new UserBean();

    public UserBean getUser() {
        return user;
    }

    public void setCurrentBinding(FragmentContactsBinding currentBinding) {
        this.currentBinding = currentBinding;
    }

    /**
     * 列表适配器
     */
    private BGABindingRecyclerViewAdapter<GradeBean, ItemFragmentContactsBinding> adapter = new BGABindingRecyclerViewAdapter<>(R.layout.item_grade_adapter);

    public BGABindingRecyclerViewAdapter<GradeBean, ItemFragmentContactsBinding> getAdapter() {
        return adapter;
    }
    /**
     * 装载数据
     * @param data 数据实体
     * @param isRefresh 是否刷新
     */
    public void setData(GradeListData data, boolean isRefresh){
        setDataState(CommonUtils.isEmpty(data.getData()));
        if(isRefresh){
            adapter.clear();
            adapter.addNewData(data.getData());
        }else {
            adapter.addMoreData(data.getData());
        }

    }



}