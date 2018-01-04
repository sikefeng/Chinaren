/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.presenter.vm;

import com.sikefeng.chinaren.core.BaseViewModel;
import com.sikefeng.chinaren.databinding.ActivitySettingsBinding;
import com.sikefeng.chinaren.entity.model.UserBean;


public class SettingsViewModel extends BaseViewModel {

    /**
     *  我界面数据绑定类
     */
    private ActivitySettingsBinding currentBinding;
    /**
     *  用户实体类
     */
    private UserBean user = new UserBean();

    public UserBean getUser() {
        return user;
    }

    public void setCurrentBinding(ActivitySettingsBinding currentBinding) {
        this.currentBinding = currentBinding;
    }





}