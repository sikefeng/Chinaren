/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.presenter.vm;

import com.sikefeng.chinaren.core.BaseViewModel;
import com.sikefeng.chinaren.databinding.FragmentMyBinding;
import com.sikefeng.chinaren.entity.model.UserBean;


public class TestViewModel extends BaseViewModel {

    /**
     *  我界面数据绑定类
     */
    private FragmentMyBinding currentBinding;
    /**
     *  用户实体类
     */
    private UserBean user = new UserBean();

    public UserBean getUser() {
        return user;
    }

    public void setCurrentBinding(FragmentMyBinding currentBinding) {
        this.currentBinding = currentBinding;
    }





}