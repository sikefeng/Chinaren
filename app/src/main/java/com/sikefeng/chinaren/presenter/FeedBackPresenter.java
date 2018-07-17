
package com.sikefeng.chinaren.presenter;

import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.BaseData;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.mvpvmlib.utils.LogUtils;
import com.sikefeng.chinaren.presenter.vm.FeedBackViewModel;
import com.sikefeng.chinaren.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.sikefeng.chinaren.utils.Constants.userID;


public class FeedBackPresenter extends BasePresenter<IRBaseView, FeedBackViewModel> {


    public FeedBackPresenter(IRBaseView view, FeedBackViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {

    }

    public void feedBack(String content,String type){
        addDisposable(ServiceHelper.getCommonAPI().feedBack(userID,content,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseData>() {
                    @Override
                    public void onNext(BaseData value) {
                        int status = value.getStatus();
                        String msg = value.getMsg();
                        LogUtils.i(status + "=========" + msg);
                        if (status == 0) {
                            ToastUtils.showShort("反馈成功！");
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

