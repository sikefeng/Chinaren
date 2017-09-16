/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.mvpvmlib.base;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sikefeng.mvpvmlib.register.ActivityRegister;


public abstract class RBaseActivity<DB extends ViewDataBinding> extends AppCompatActivity implements IRBaseView {

    /**
     * 绑定泛型类
     */
    private DB binding;
    /**
     * 声明ActivityRegister
     */
    private ActivityRegister mActivityRegister;

    public DB getBinding() {
        return binding;
    }

    /**
     * 注册Activity
     * @return ActivityRegister
     */
    public ActivityRegister getmActivityRegister() {
        return mActivityRegister;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preOnCreate(savedInstanceState);//特殊需求 一般不用重写
        super.onCreate(savedInstanceState);
        //多个Presenter或不想继承本BaseActivity，重写以下两句关键代码即可
        mActivityRegister = new ActivityRegister().initBinding(this, getLayoutId()).register(getPresenter());
        binding = (DB)mActivityRegister.getBinding();

        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityRegister.unRegister();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * 获取LayoutId
     * @return int
     */
    protected abstract int getLayoutId();

    /**
     * 获取RBasePresenter
     * @return RBasePresenter
     */
    protected abstract RBasePresenter getPresenter();

    /**
     * 创建Activity前的事件
     * @param savedInstanceState Bundle
     */
    protected void preOnCreate(Bundle savedInstanceState){}

    /**
     * 初始化Activity
     * @param savedInstanceState Bundle
     */
    protected abstract void init(Bundle savedInstanceState);

}
