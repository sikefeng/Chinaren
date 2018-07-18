package com.sikefeng.chinaren.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.sikefeng.chinaren.MyApplication;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityRegisterBinding;
import com.sikefeng.chinaren.entity.event.RegisterEvent;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.RegisterPresenter;
import com.sikefeng.chinaren.presenter.vm.RegisterViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.Network;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.utils.VerificationUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 文件名：RegisterActivity <br>
 * 创建时间： 2017/7/21 0021 下午16:13 <br>
 * 文件描述：<br>
 * 用户注册类
 *
 * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
@Route(path = Constants.REGISTER_URL)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements View.OnClickListener {
    /**
     * 用户实体类
     */
    private UserBean userBean;
    /**
     * 用户注册数据协调器
     */
    private RegisterPresenter presenter;
    /**
     * 倒计时
     */
    private TimeCount timeFirst;
    /**
     * 倒计时时间
     */
    private final int defaultTime = 60000;
    /**
     * 时间间隔1s
     */
    private final int seconds = 1000;
    /**
     * 验证码发送成功标志
     */
    private String sendCodeSuccess = "success";

    /**
     * 订阅事件
     *
     * @param event 自定义事件类
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RegisterEvent event) {
        String type = event.getType();
        if (type.equals(sendCodeSuccess)) {
//            timeFirst = new TimeCount(getBinding().btnSendCode, defaultTime, seconds);
//            timeFirst.start();
        }
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new RegisterPresenter(this, new RegisterViewModel());
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        userBean = new UserBean();
        presenter.getViewModel().setCurrentBinding(getBinding());
        getBinding().setUserBean(userBean);
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());
        setToolbar(getBinding().toolbar);
//        getBinding().btnSendCode.setOnClickListener(this);
//        getBinding().cbVisiblePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    //选择状态 显示明文--设置为可见的密码
//                    getBinding().newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                } else {
//                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
//                    getBinding().newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }
//            }
//        });
        getBinding().btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = getBinding().phone.getText().toString();
                String verCode = getBinding().code.getText().toString();
                String nickName = getBinding().nickName.getText().toString();
                String newPassword = getBinding().password.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort(R.string.phone_empty);
                    return;
                }
                boolean isPhoneNumber = VerificationUtil.isMobile(phone);
                if (!isPhoneNumber) {
                    ToastUtils.showShort(R.string.phone_not_format);
                    return;
                }
                if (TextUtils.isEmpty(verCode)) {
                    ToastUtils.showShort(R.string.code_empty);
                    return;
                }
                if (TextUtils.isEmpty(nickName)) {
                    ToastUtils.showShort(R.string.name_empty);
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
                if (!Network.isAvailable(MyApplication.getContext())) {
                    ToastUtils.showShort(MyApplication.getContext().getString(R.string.network_disconnect));
                    return;
                }
                userBean.setDevice("1"); //登录设备（0.微信小程序 1.Android 2.IOS）
                userBean.setPhone(phone);
                userBean.setCode(verCode);
                userBean.setNickName(nickName);
                userBean.setPassword(newPassword);
                userBean.setLoginName(phone);
                presenter.registerUser(userBean);
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSendCode:
                if (!Network.isAvailable(MyApplication.getContext())) {
                    ToastUtils.showShort(MyApplication.getContext().getString(R.string.network_disconnect));
                    return;
                }
                String loginName = getBinding().phone.getText().toString();
                if (TextUtils.isEmpty(loginName)) {
                    ToastUtils.showShort(R.string.phone_empty);
                    return;
                } else {
                    boolean isPhoneNumber = VerificationUtil.isMobile(loginName);
                    if (!isPhoneNumber) {
                        ToastUtils.showShort(R.string.phone_not_format);
                        return;
                    }
                    userBean.setLoginName(loginName);
                    presenter.getCode(loginName);
                }
                break;
            default:
                break;

        }
    }

    class TimeCount extends CountDownTimer {
        /**
         * 控件
         */
        private Button btnFirstSend;

        /**
         * 功能描述：构造函数设计
         * <br>创建时间： 2017-07-21 16:11:41
         *
         * @param btnFirstSend      按钮控件
         * @param millisInFuture    倒计时总时间
         * @param countDownInterval 时间间隔
         * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a>
         */
        TimeCount(Button btnFirstSend, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.btnFirstSend = btnFirstSend;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnFirstSend.setEnabled(false);
            btnFirstSend.setText(getString(R.string.get_code) + "(" + millisUntilFinished / seconds + ")");
        }

        @Override
        public void onFinish() {
            btnFirstSend.setText(getString(R.string.get_code));
            btnFirstSend.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeFirst != null) {
            timeFirst.cancel();
            timeFirst = null;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }
}
