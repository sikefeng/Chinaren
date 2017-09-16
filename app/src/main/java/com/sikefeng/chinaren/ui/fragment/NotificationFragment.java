/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseFragment;
import com.sikefeng.chinaren.databinding.FragmentNotificationBinding;
import com.sikefeng.chinaren.entity.event.RvScrollEvent;
import com.sikefeng.chinaren.presenter.NotificationPresenter;
import com.sikefeng.chinaren.presenter.vm.NotificationViewModel;
import com.sikefeng.mvpvmlib.base.RBasePresenter;
import com.sikefeng.mvpvmlib.utils.LogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class NotificationFragment extends BaseFragment<FragmentNotificationBinding> {

    /**
     * 引入NotificationPresenter
     */
    private NotificationPresenter presenter;

    /**
     * 定义RecyclerView样式为LinearLayoutManager
     */
    private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

    /**
     * 功能描述：监听消息事件，例如滚动条滚动位置
     * <br>创建时间： 2017-07-03 10:22:58

     * @param event 滚动条事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RvScrollEvent event) {
        if (event.getTabIndex() == 1) {
            layoutManager.scrollToPositionWithOffset(event.getPos(), 0);
        }
    }

    @Override
    protected boolean isEnableEventBus() {
        return true;
    }

    @Override
    protected RBasePresenter getPresenter() {
        LogUtils.d(" =========>> "+ getBinding());
        if(null == presenter){
            presenter = new NotificationPresenter(this, new NotificationViewModel(getBinding()));
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        LogUtils.d(" ---------> init B.");
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());
        getBinding().setLayoutManager(layoutManager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification;
    }
}
