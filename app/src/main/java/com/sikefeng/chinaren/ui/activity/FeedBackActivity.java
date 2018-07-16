package com.sikefeng.chinaren.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityFeedBackBinding;
import com.sikefeng.chinaren.databinding.ActivityTestBinding;
import com.sikefeng.chinaren.entity.event.PermissionEvent;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.FeedBackPresenter;
import com.sikefeng.chinaren.presenter.TestPresenter;
import com.sikefeng.chinaren.presenter.vm.FeedBackViewModel;
import com.sikefeng.chinaren.presenter.vm.TestViewModel;
import com.sikefeng.chinaren.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FeedBackActivity extends BaseActivity<ActivityFeedBackBinding> {

    private FeedBackPresenter presenter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPermissionEvent(PermissionEvent event) {

    }

    @Override
    protected boolean isEnableEventBus() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new FeedBackPresenter(this, new FeedBackViewModel());
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setToolbar();
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());
        getBinding().btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = getBinding().etContent.getText().toString().trim();
                if ("".equals(content)){
                    ToastUtils.showShort("请输入反馈内容");
                    return;
                }
                presenter.feedBack(content);
            }
        });
    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("应用反馈");
        setSupportActionBar(toolbar);
        try {
            //给左上角图标的左边加上一个返回的图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
