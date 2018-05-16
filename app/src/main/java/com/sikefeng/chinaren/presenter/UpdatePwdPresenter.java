package com.sikefeng.chinaren.presenter;

import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.event.UpdatePwdEvent;
import com.sikefeng.chinaren.entity.model.UserData;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.mvpvmlib.utils.LogUtils;
import com.sikefeng.chinaren.presenter.vm.UpdatePwdViewModel;
import com.sikefeng.chinaren.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件名：UpdatePwdPresenter <br>
 * 创建时间： 24/7/17 下午PM4:57 <br>
 * 文件描述：<br>
 * 修改密码数据协调器，主要用于组织数据
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class UpdatePwdPresenter extends BasePresenter<IRBaseView, UpdatePwdViewModel> {

    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public UpdatePwdPresenter(IRBaseView view, UpdatePwdViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {

    }

    /**
     * 功能描述：用户通过旧密码修改密码
     * <br>创建时间： 2017-07-24 16:40:45

     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param confirmPassword 确认密码
     */
    public void updatePwd(String oldPassword, String newPassword,String confirmPassword) {
        StyledDialog.buildLoading(getContext().getString(R.string.isLogining)).show();
        addDisposable(ServiceHelper.getUsersAS().updatePwd(oldPassword,newPassword,confirmPassword)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UserData>() {
                            @Override
                            public void onNext(UserData value) {
                                int status = value.getStatus();
                                String msg = value.getMsg();
                                LogUtils.i(status + "=========" + msg);
                                if (status == 0 && msg.equals("success")) {
                                    ToastUtils.showShort(getContext().getString(R.string.update_pwd_success));
                                    EventBus.getDefault().post(new UpdatePwdEvent().withType("success"));
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

