package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityImageListBinding;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.ImageListPresenter;
import com.sikefeng.chinaren.presenter.vm.ImageListViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ImageListActivity extends BaseActivity<ActivityImageListBinding> {
    private Context mContext;
    private ImageListPresenter presenter;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {

    }

    @Override
    protected boolean isEnableEventBus() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_list;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new ImageListPresenter(this, new ImageListViewModel(getBinding()));
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext=this;
        setToolbar(getBinding().toolbar);
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().setLayoutManager(layoutManager);
    }
}
