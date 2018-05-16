/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.mvpvmlib.register;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;


public class ActivityRegister {

    /**
     * 视图数据绑定
     */
    private ViewDataBinding binding;
    /**
     * 数据协调器
     */
    private RBasePresenter[] presenters;

    /**
     * 初始化
     *
     * @param activity Activity
     * @param layoutId 视图ID
     * @return ActivityRegister
     */
    public ActivityRegister initBinding(Activity activity, @LayoutRes int layoutId) {
        if (null == binding) {
            binding = DataBindingUtil.setContentView(activity, layoutId);
        }
        return this;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    /**
     * register时就会启动presenter的onCreate
     *
     * @param presenters RBasePresenter
     * @return ActivityRegister
     */
    public ActivityRegister register(RBasePresenter... presenters) {
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
