package com.sikefeng.chinaren.presenter;

import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.entity.model.UserData;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.presenter.vm.UpdateUserInfoViewModel;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;
import com.sikefeng.chinaren.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.sikefeng.chinaren.utils.Constants.GENDER;
import static com.sikefeng.chinaren.utils.Constants.MOTTO;
import static com.sikefeng.chinaren.utils.Constants.NICKNAME;

/**
 * 文件名：ForgetPwdPresenter <br>
 * 创建时间： 2017/7/21 0021 下午16:19 <br>
 * 文件描述：<br>
 * 忘记密码数据协调器，主要用于组织数据
 *
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class UpdateUserInfoPresenter extends BasePresenter<IRBaseView, UpdateUserInfoViewModel> {

    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public UpdateUserInfoPresenter(IRBaseView view, UpdateUserInfoViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {

    }

    public void updateMember(UserBean userBean) {
        StyledDialog.buildLoading("保存中...").show();
        addDisposable(ServiceHelper.getUsersAS().updateMember(userBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserData>() {
                    @Override
                    public void onNext(UserData value) {
                        StyledDialog.dismissLoading();
                        int status = value.getStatus();
                        String msg = value.getMsg();
                        if (status == 0) {
                            SharePreferenceUtils.put(getContext(), NICKNAME, value.getData().getNickName());
                            SharePreferenceUtils.put(getContext(), MOTTO, value.getData().getMotto());
                            SharePreferenceUtils.put(getContext(), GENDER, value.getData().getGender());
                            ToastUtils.showLong("修改成功");
                            getContext().finish();
                            return;
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


