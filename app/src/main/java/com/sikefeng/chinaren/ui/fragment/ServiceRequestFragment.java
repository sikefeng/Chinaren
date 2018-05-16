/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseFragment;
import com.sikefeng.chinaren.databinding.FragmentServiceRequestBinding;
import com.sikefeng.chinaren.entity.event.RvScrollEvent;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.NotificationPresenter;
import com.sikefeng.chinaren.presenter.vm.NotificationViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ServiceRequestFragment extends BaseFragment<FragmentServiceRequestBinding> {

    /**
     * 引入NotificationPresenter
     */
    private NotificationPresenter presenter;

    /**
     * 实现瀑布流效果的Manager
     */
    private StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

    /**
     * 功能描述：监听消息事件，例如滚动条滚动位置
     * <br>创建时间： 2017-07-03 10:22:58

     * @param event 滚动条事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RvScrollEvent event) {
        if (event.getTabIndex() == 2) {
            layoutManager.scrollToPositionWithOffset(event.getPos(), 0);
        }
    }

    @Override
    protected boolean isEnableEventBus() {
        return true;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if(null == presenter){
            presenter = new NotificationPresenter(this, new NotificationViewModel(null));
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());
        getBinding().setLayoutManager(layoutManager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_request;
    }
}
