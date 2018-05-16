/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.mvpvmlib.register;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;


public class FragmentRegister {

    /**
     * 绑定视图数据
     */
    private ViewDataBinding binding;
    /**
     * 声明数据协调器
     */
    private RBasePresenter[] presenters;

    /**
     * 初始化数据绑定
     *
     * @param inflater  LayoutInflater
     * @param container ViewGroup
     * @param layoutId  资源ID
     * @return FragmentRegister
     */
    public FragmentRegister initBinding(LayoutInflater inflater, ViewGroup container, @LayoutRes int layoutId) {
        if (null == binding) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        }
        return this;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    /**
     * 注册
     *
     * @param presenters RBasePresenter
     * @return FragmentRegister
     */
    public FragmentRegister register(RBasePresenter... presenters) {
        this.presenters = presenters;
        if (null != this.presenters) {
            for (RBasePresenter presenter : this.presenters) {
                if (null != presenter) {
                    presenter.onCreate();
                }
            }
        }
        return this;
    }

    /**
     * 解除注册
     */
    public void unRegister() {
        if (null != this.presenters) {
            for (RBasePresenter presenter : this.presenters) {
                if (null != presenter) {
                    presenter.onDestroy();
                }
            }
        }
        binding = null;
    }

}
