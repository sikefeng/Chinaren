
package com.sikefeng.chinaren.presenter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.entity.model.UserData;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.mvpvmlib.utils.LogUtils;
import com.sikefeng.chinaren.presenter.vm.LoginViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.LoginUtil;
import com.sikefeng.chinaren.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenter extends BasePresenter<IRBaseView, LoginViewModel> {

    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public LoginPresenter(IRBaseView view, LoginViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {

    }

    /**
     * 功能描述：用户登录方法
     * <br>创建时间： 2017-07-21 16:25:56
     *
     * @param userBean 用户实体类
     */
    public void login(UserBean userBean) {
        StyledDialog.buildLoading(getContext().getString(R.string.isLogining)).show();
        addDisposable(ServiceHelper.getUsersAS().login(userBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserData>() {
                    @Override
                    public void onNext(UserData value) {
                        int status = value.getStatus();
                        String msg = value.getMsg();
                        LogUtils.i(status + "=========" + msg);
                        if (status == 0 && msg.equals("success")) {
                            UserBean bean = value.getData();
                            bean.setLoginName(userBean.getLoginName());
                            bean.setNewPassword(userBean.getNewPassword());
                            LoginUtil.saveUserData(getContext(), bean);
                            ARouter.getInstance().build(Constants.MAIN_URL).navigation();
                            getContext().finish();
                        } else {
                            ToastUtils.showShort(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        StyledDialog.dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                        StyledDialog.dismissLoading();
                    }
                })
        );
    }

}

