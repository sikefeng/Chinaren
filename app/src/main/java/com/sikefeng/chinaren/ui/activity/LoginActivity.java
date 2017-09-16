package com.sikefeng.chinaren.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.XXApplication;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityLoginBinding;
import com.sikefeng.chinaren.entity.event.RvScrollEvent;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.presenter.LoginPresenter;
import com.sikefeng.chinaren.presenter.vm.LoginViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.Network;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.utils.VerificationUtil;
import com.sikefeng.mvpvmlib.base.RBasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.hss01248.dialog.StyledDialog.context;

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
@Route(path = Constants.LOGIN_URL, group = Constants.APP_GOUP)
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
     * 功能描述：滑动事件
     * <br>创建时间： 2017-07-25 09:50:18

     * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a>
     * @param event 事件返回值
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RvScrollEvent event) {

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
//        ToastUtils.showLong(" ----------->>> "+ BuildConfig.DEBUG);
        userBean = new UserBean();
        getBinding().setUser(userBean);
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());

        String loginName = (String) SharePreferenceUtils.get(context, Constants.LOGINNAME, "");
        String newPassword = (String) SharePreferenceUtils.get(context, Constants.NEWPASSWORD, "");
        getBinding().loginName.setText(loginName);
        getBinding().newPassword.setText(newPassword);

        getBinding().tvRegister.setOnClickListener(this);
        getBinding().tvForgetPwd.setOnClickListener(this);


        getBinding().btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginName = getBinding().loginName.getText().toString();
                String newPassword = getBinding().newPassword.getText().toString();
                boolean isPhoneNumber = VerificationUtil.isMobile(loginName);
                if (TextUtils.isEmpty(loginName)) {
                    ToastUtils.showShort(R.string.phone_empty);
                    return;
                }
                if (!isPhoneNumber) {
                    ToastUtils.showShort(R.string.phone_not_format);
                    return;
                }
                if (TextUtils.isEmpty(newPassword)) {
                    ToastUtils.showShort(R.string.pwd_empty);
                    return;
                }
                if (newPassword.length() < Constants.PWD_LENGTH) {
                    ToastUtils.showShort(R.string.pwd_length_tips);
                    return;
                }
                if (!Network.isAvailable(XXApplication.getContext())) {
                    ToastUtils.showShort(XXApplication.getContext().getString(R.string.network_disconnect));
                    return;
                }
                userBean.setLoginName(loginName);
                userBean.setNewPassword(newPassword);
                presenter.login(userBean);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvRegister:
                ARouter.getInstance().build(Constants.REGISTER_URL, Constants.APP_GOUP).navigation();
                break;

            case R.id.tvForgetPwd:
                ARouter.getInstance().build(Constants.FORGET_URL, Constants.APP_GOUP).navigation();
                break;
            default:
                break;
        }
    }
}










