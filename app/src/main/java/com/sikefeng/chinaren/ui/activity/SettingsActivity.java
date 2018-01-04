package com.sikefeng.chinaren.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivitySettingsBinding;
import com.sikefeng.chinaren.entity.event.PermissionEvent;
import com.sikefeng.chinaren.presenter.SettingsPresenter;
import com.sikefeng.chinaren.presenter.vm.SettingsViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.PermissionUtils;
import com.sikefeng.mvpvmlib.base.RBasePresenter;
import com.zhy.changeskin.SkinManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = Constants.SETTINGS_URL)
public class SettingsActivity extends BaseActivity<ActivitySettingsBinding> implements View.OnClickListener {

    /**
     * 用户登录数据协调器
     */
    private SettingsPresenter presenter;

    /**
     * 显示权限设置
     */
    private boolean showAuth = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }


    /**
     * 接收授权事件
     *
     * @param event PermissionEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPermissionEvent(PermissionEvent event) {
        showAuth = event.isToSystemAuthorization();
    }

    @Override
    protected boolean isEnableEventBus() {
        return true;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new SettingsPresenter(this, new SettingsViewModel());
        }
        return presenter;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(false);//设置是否可滑动
        ARouter.getInstance().inject(this);

        initView();
    }

    @Override
    public void initView() {
        super.initView();
        //未保存switch状态
        getBinding().switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SkinManager.getInstance().changeSkin(isChecked ? "night" : "");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //注册
            case R.id.tvRegister:

                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (showAuth) {
            showAuth = false;
            PermissionUtils.getInstace().authorization(this, showAuth, true, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA ); //6.0+授权访问
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /*** 点击空白位置 隐藏软键盘*/
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

}









