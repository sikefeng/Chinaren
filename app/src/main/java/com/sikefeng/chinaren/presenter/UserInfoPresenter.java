
package com.sikefeng.chinaren.presenter;

import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.BaseData;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.mvpvmlib.utils.LogUtils;
import com.sikefeng.chinaren.presenter.vm.SettingsViewModel;
import com.sikefeng.chinaren.presenter.vm.UserInfoViewModel;
import com.sikefeng.chinaren.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class UserInfoPresenter extends BasePresenter<IRBaseView, UserInfoViewModel> {


    public UserInfoPresenter(UserInfoViewModel viewModel) {
        super(null,viewModel);
    }

    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public UserInfoPresenter(IRBaseView view, UserInfoViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {

    }

    public void updateMember(String type, String value) {
        addDisposable(ServiceHelper.getUsersAS().updateMember(type, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseData>() {
                    @Override
                    public void onNext(BaseData value) {
                        StyledDialog.dismissLoading();
                        int status = value.getStatus();
                        String msg = value.getMsg();
                        LogUtils.i(status + "=========" + msg);
                        if (status == 0) {
                            ToastUtils.showShort("成功！");
                            getContext().finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        StyledDialog.dismissLoading();
                        getViewModel().onError(e, true);
                    }

                    @Override
                    public void onComplete() {
                        StyledDialog.dismissLoading();
                    }
                })
        );
    }


}

