
package com.sikefeng.chinaren.core;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.sikefeng.mvpvmlib.base.RBaseFragment;

import org.greenrobot.eventbus.EventBus;


public abstract class BaseFragment<DB extends ViewDataBinding> extends RBaseFragment<DB> {

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        if(isEnableEventBus() && !EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isEnableEventBus() && EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    //是否启用EventBus
    protected boolean isEnableEventBus(){
        return false;
    }
}
