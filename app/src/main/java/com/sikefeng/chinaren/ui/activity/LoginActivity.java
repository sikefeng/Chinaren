package com.sikefeng.chinaren.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityLoginBinding;
import com.sikefeng.chinaren.entity.event.PermissionEvent;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.presenter.LoginPresenter;
import com.sikefeng.chinaren.presenter.vm.LoginViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.PermissionUtils;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.mvpvmlib.base.RBasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 文件名：LoginActivity <br>
 * 创建时间： 2017/7/21 0021 下午16:27 <br>
 * 文件描述：<br>
 * 用户登录类
 *
 * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
@Route(path = Constants.LOGIN_URL)
public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements View.OnClickListener {
    /**
     * 用户实体类
     */
    private UserBean userBean;
    /**
     * 用户登录数据协调器
     */
    private LoginPresenter presenter;

    /**
     * 显示权限设置
     */
    private boolean showAuth = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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
            presenter = new LoginPresenter(this, new LoginViewModel());
        }
        return presenter;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(false);//设置是否可滑动
        ARouter.getInstance().inject(this);

        userBean = new UserBean();
        getBinding().setUser(userBean);
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());
//        SharePreferenceUtils.get(MyApplication.getContext(), Constants.TOKEN, "");
//        SharePreferenceUtils.get(MyApplication.getContext(), Constants.ISLOGIN, false);
//        String loginName = (String) SharePreferenceUtils.get(context, Constants.LOGINNAME, "");
//        String newPassword = (String) SharePreferenceUtils.get(context, Constants.NEWPASSWORD, "");
//        getBinding().loginName.setText(loginName);
//        getBinding().newPassword.setText(newPassword);

        getBinding().tvRegister.setOnClickListener(this);
        getBinding().tvForgetPwd.setOnClickListener(this);


        getBinding().btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(Constants.MAIN_URL).navigation();
                ToastUtils.showBottom("登录成功");
            }
        });
//        ARouter.getInstance().build(Constants.MAIN_URL).navigation();
//        startActivity(new Intent(LoginActivity.this,CaptureActivity.class));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //注册
            case R.id.tvRegister:
                ARouter.getInstance().build(Constants.REGISTER_URL).navigation();
                break;
            //忘记密码
            case R.id.tvForgetPwd:
                ARouter.getInstance().build(Constants.FORGET_URL).navigation();
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









