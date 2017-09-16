
package com.sikefeng.chinaren.core;

import android.app.Activity;

import com.classic.common.MultipleStatusView;
import com.sikefeng.mvpvmlib.base.IRBaseView;
import com.sikefeng.mvpvmlib.base.RBasePresenter;


public abstract class BasePresenter<V extends IRBaseView, VM extends BaseViewModel> extends RBasePresenter<V, VM> {

    /**
     * 定义Actiivty
     */
    private Activity context;

    public Activity getContext() {
        return context;
    }

    /**
     * 构造函数设计
     *
     * @param view view视图
     * @param viewModel 视图相关实体
     */
    public BasePresenter(V view, VM viewModel) {
        super(view, viewModel);
        context = view.getActivity();
    }

    /**
     * 请求数据时候设置缩放更新列表状态
     *
     * @param isRefresh 是否刷新
     */
    public void requestData(boolean isRefresh) {
        if (getViewModel().isFirstLoadData()) {
            getViewModel().getStateViewType().set(MultipleStatusView.STATUS_LOADING);
        }
        loadData(isRefresh);
    }

    /**
     * 读取数据
     *
     * @param isRefresh 是否刷新
     */
    public void loadData(boolean isRefresh) {

    }

}
