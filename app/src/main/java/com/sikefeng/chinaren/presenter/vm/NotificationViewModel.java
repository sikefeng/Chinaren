/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.presenter.vm;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseViewModel;
import com.sikefeng.chinaren.databinding.FragmentNotificationBinding;
import com.sikefeng.chinaren.databinding.ItemFragmentNotificationBinding;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.entity.model.UserListData;
import com.sikefeng.chinaren.utils.CommonUtils;
import com.sikefeng.mvpvmlib.utils.LogUtils;

import cn.bingoogolapple.androidcommon.adapter.BGABindingRecyclerViewAdapter;


public class NotificationViewModel extends BaseViewModel {

    /**
     *  定义FragmentNotificationBinding
     */
    private FragmentNotificationBinding currentBinding;
    /**
     * 定义用户
     */
    private UserBean user = new UserBean();

    public UserBean getUser() {
        return user;
    }

    /**
     * 赋值FragmentNotificationBinding
     * @param binding FragmentNotificationBinding
     */
    public NotificationViewModel(FragmentNotificationBinding binding){
        currentBinding = binding;

    }

    /**
     * 列表适配器
     */
    private BGABindingRecyclerViewAdapter<UserBean, ItemFragmentNotificationBinding> adapter = new BGABindingRecyclerViewAdapter<>(R.layout.item_fragment_notification);

    public BGABindingRecyclerViewAdapter<UserBean, ItemFragmentNotificationBinding> getAdapter() {
        return adapter;
    }

    /**
     * 装载数据
     * @param data 数据实体
     * @param isRefresh 是否刷新
     */
    public void setData(UserListData data, boolean isRefresh){
        setDataState(CommonUtils.isEmpty(data.getData()));
        if(isRefresh){
            adapter.clear();
            adapter.addNewData(data.getData());
        }else {
            adapter.addMoreData(data.getData());
        }

    }

    /**
     * 设置用户数据
     * @param name String
     */
    public void setUserData(String name){
        currentBinding.setUserBean(user);
        LogUtils.d(" ########3 set loginname. ");
        user.setLoginName(name);
    }

}
