package com.sikefeng.chinaren.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityLoginBinding;
import com.sikefeng.chinaren.entity.event.PermissionEvent;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.LoginPresenter;
import com.sikefeng.chinaren.presenter.vm.LoginViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.PermissionUtils;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;
import com.sikefeng.chinaren.utils.StringUtil;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.widget.PopupDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

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
    private Platform loginPlatform;
    /**
     * 显示权限设置
     */
    private boolean showAuth = true;
    private Boolean isOpenDrawer = false;

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

        ARouter.getInstance().inject(this);

        userBean = new UserBean();
        getBinding().setUser(userBean);
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());

        getBinding().tvRegister.setOnClickListener(this);
        getBinding().tvForgetPwd.setOnClickListener(this);

        getBinding().ibLoginQq.setOnClickListener(this);
        getBinding().ibLoginWx.setOnClickListener(this);
        getBinding().ibLoginWeibo.setOnClickListener(this);


        getBinding().btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginName = getBinding().loginName.getText().toString().trim();
                String password = getBinding().password.getText().toString().trim();
                if (StringUtil.isBlank(loginName) || StringUtil.isBlank(password)) {
                    ToastUtils.showShort("用户名或密码不能为空");
                    return;
                }
                userBean.setLoginName(loginName);
                userBean.setPassword(password);
                userBean.setDevice("1"); //登录设备（0.微信小程序 1.Android 2.IOS）
                presenter.login(userBean);
            }
        });
        getBinding().slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                isOpenDrawer = true;
            }

        });

        getBinding().slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {

            @Override
            public void onDrawerClosed() {
                isOpenDrawer = false;
            }

        });
//        ARouter.getInstance().build(Constants.MAIN_URL).navigation();

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
//                ARouter.getInstance().build(Constants.FORGET_URL).navigation();
                PopupDialog popupDialog=new PopupDialog(LoginActivity.this, R.layout.popup_url);
                popupDialog.showAtLocation(v, Gravity.CENTER);
                EditText edUrl=popupDialog.getView(R.id.edUrl);
                Button btnSubmit=popupDialog.getView(R.id.btnSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupDialog.dismiss();
                        String url=edUrl.getText().toString().trim();
                        SharePreferenceUtils.put(LoginActivity.this,"URL",url);
                    }
                });
                break;
            case R.id.ib_login_qq:
                threeLogin(0);
                break;
            case R.id.ib_login_wx:
                threeLogin(1);
                break;
            case R.id.ib_login_weibo:
                threeLogin(2);
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
            PermissionUtils.getInstace().authorization(this, showAuth, true, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA); //6.0+授权访问
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


    private void threeLogin(int platformType) {

        if (platformType == 0) {
            loginPlatform = ShareSDK.getPlatform(QQ.NAME);
        } else if (platformType == 1) {
            loginPlatform = ShareSDK.getPlatform(Wechat.NAME);
        } else {
            loginPlatform = ShareSDK.getPlatform(SinaWeibo.NAME);
        }
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        loginPlatform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onError(Platform platform, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
                System.out.println("arg2=" + arg2);
            }

            @Override
            public void onComplete(Platform platform, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
                String data = platform.getDb().exportData();
                PlatformDb platDB = platform.getDb();//获取数平台数据DB
                //通过DB获取各种数据
                String token = platDB.getToken();
                String gender = platDB.getUserGender();
                String userIcon = platDB.getUserIcon();
                String userId = platDB.getUserId();
                String userName = platDB.getUserName();
                System.out.println("=====" + data);
                System.out.println("=====" + token);
                System.out.println("=====" + gender);
                System.out.println("=====" + userIcon);
                System.out.println("=====" + userId);
                System.out.println("=====" + userName);
                //移除授权
                loginPlatform.removeAccount(true);
            }

            @Override
            public void onCancel(Platform platform, int arg1) {
                // TODO Auto-generated method stub

            }
        });
        //authorize与showUser单独调用一个即可
//        weixin.authorize();//单独授权,OnComplete返回的hashmap是空的
        loginPlatform.showUser(null);//授权并获取用户信息

    }


}









