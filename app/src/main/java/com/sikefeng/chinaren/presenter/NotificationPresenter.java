
package com.sikefeng.chinaren.presenter;

import android.content.Intent;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.entity.model.UserListData;
import com.sikefeng.chinaren.entity.other.ImgsInfo;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.presenter.vm.NotificationViewModel;
import com.sikefeng.chinaren.ui.activity.ImagesActivity;
import com.sikefeng.chinaren.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class NotificationPresenter extends BasePresenter<IRBaseView, NotificationViewModel> {

    /**
     * 每页显示数量
     */
    private int count = ResUtils.getInteger(R.integer.load_count);
    /**
     * 当前页码
     */
    private int page = 1;

    /**
     * 图片列表
     */
    private List<String> imgUrls;

    /**
     * 构造函数
     * @param view 基类视图
     * @param viewModel 当前视图模型
     */
    public NotificationPresenter(IRBaseView view, NotificationViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {
        getViewModel().getAdapter().setItemEventHandler(this);
        loadData(true);
    }

    @Override
    public void loadData(final boolean isRefresh) {
        addDisposable(ServiceHelper.getUsersAS().getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserListData>() {
                    @Override
                    public void onNext(UserListData value) {
                        getViewModel().setData(value, isRefresh);
                        getViewModel().setUserData("AHA! OK! ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewModel().onError(e, isRefresh);
                    }

                    @Override
                    public void onComplete() {}
                })
        );
    }

    /**
     * 点击列表某个项的事件
     * @param holder 自定义ViewHolder
     * @param model 数据实体类
     */
    public void onClickItem(BGABindingViewHolder holder, UserBean model) {
        Intent intent = new Intent(getContext(), ImagesActivity.class);
        if(null == imgUrls) {
            imgUrls = new ArrayList<>(count);
        }
        imgUrls.clear();
        for (UserBean bean : getViewModel().getAdapter().getData()) {
            imgUrls.add(bean.getName());
        }

        int curPos = holder.getAdapterPositionWrapper() - 1;

        intent.putExtra(ImgsInfo.KEY, new ImgsInfo(imgUrls, curPos < 0 ? 0 : curPos));
        getContext().startActivity(intent);
    }
}
