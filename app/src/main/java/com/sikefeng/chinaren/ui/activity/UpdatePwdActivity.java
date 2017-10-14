package com.sikefeng.chinaren.ui.activity;


import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityUpdatepwdBinding;
import com.sikefeng.chinaren.entity.event.UpdatePwdEvent;
import com.sikefeng.chinaren.presenter.UpdatePwdPresenter;
import com.sikefeng.chinaren.presenter.vm.UpdatePwdViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.mvpvmlib.base.RBasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 文件名：UpdatePwdActivity <br>
 * 创建时间： 24/7/17 下午PM4:58 <br>
 * 文件描述：<br>
 * 修改密码界面
 *
 * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
@Route(path = Constants.UPDATE_PWD_URL)
public class UpdatePwdActivity extends BaseActivity<ActivityUpdatepwdBinding> implements View.OnClickListener {


    /**
     * 修改密码数据协调器
     */
    private UpdatePwdPresenter presenter;

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
    public void onMessageEvent(UpdatePwdEvent event) {

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_updatepwd;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new UpdatePwdPresenter(this, new UpdatePwdViewModel());
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());
        setToolbar(getBinding().toolbar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                String old_pwd = getBinding().oldPassword.getText().toString();
                String new_pwd = getBinding().newPassword.getText().toString();
                String confirm_pwd = getBinding().confirmPassword.getText().toString();
                if (TextUtils.isEmpty(old_pwd)) {
                    ToastUtils.showShort(R.string.old_pwd_input);
                    return;
                }
                if (TextUtils.isEmpty(new_pwd)) {
                    ToastUtils.showShort(R.string.new_pwd_input);
                    return;
                }
                if (TextUtils.isEmpty(confirm_pwd)) {
                    ToastUtils.showShort(R.string.confirm_pwd_input);
                    return;
                }
                if (new_pwd.length() < Constants.PWD_LENGTH || confirm_pwd.length() < Constants.PWD_LENGTH) {
                    ToastUtils.showShort(R.string.pwd_length_tips);
                    return;
                }
                if (!new_pwd.equals(confirm_pwd)) {
                    ToastUtils.showShort(R.string.new_not_confirm);
                    return;
                }
                presenter.updatePwd(old_pwd, new_pwd, confirm_pwd);
                break;
            default:
                break;
        }
    }
}


