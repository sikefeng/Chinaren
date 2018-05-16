/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.mvpvmlib.base;


import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class RBasePresenter<V extends IRBaseView, VM extends RBaseViewModel> {

    /**
     * 视图
     */
    private V view;
    /**
     * 视图模型
     */
    private VM viewModel;

    /**
     * 获取视图
     *
     * @return 视图
     */
    public V getView() {
        return view;
    }

    /**
     * 获取视图模型
     *
     * @return 视图模型
     */
    public VM getViewModel() {
        return viewModel;
    }

    /**
     * 设置视图模型
     * @param viewModel VM
     */
    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * 声明CompositeDisposable，处理内存泄露
     */
    private CompositeDisposable mCompositeDisposable;
    /**
     * 声明RXJAVA 6.0权限适配
     */
    private RxPermissions rxPermissions;

    /**
     * 构造函数
     *
     * @param view      View
     * @param viewModel 视图模型
     */
    public RBasePresenter(V view, VM viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    /**
     * 加入Disposable
     *
     * @param disposable Disposable
     */
    public void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 检查权限
     *
     * @param onNext      DisposableObserver
     * @param permissions 权限组
     */
    public void checkPermissions(DisposableObserver<Boolean> onNext, String... permissions) {
        if (null == rxPermissions) {
            rxPermissions = new RxPermissions(this.view.getActivity());
        }
        addDisposable(rxPermissions.request(permissions).subscribeWith(onNext));
    }

    /**
     * 创建
     */
    public abstract void onCreate();

    /**
     * 销毁事件
     */
    public void onDestroy() {
        this.view = null;
        this.viewModel = null;
        if (null != mCompositeDisposable) {
            this.mCompositeDisposable.clear();
            this.mCompositeDisposable = null;
        }
    }

}
