/**
 * Copyright (C) 2014-2017 <a href="http://www.xxxxtech.com>">XXXXTech</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.presenter;

import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.NoteBean;
import com.sikefeng.chinaren.entity.model.NoteData;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.presenter.vm.NewNoteViewModel;
import com.sikefeng.chinaren.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件名：LoginPresenter <br>
 * 创建时间： 2017-07-11 09:25 <br>
 * 文件描述：<br>
 * 用户登录数据协调器，主要用于组织数据
 *
 * @author <a href="mailto:dawson@xxxxtech.com">Dawson</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class NewNotePresenter extends BasePresenter<IRBaseView, NewNoteViewModel> {


    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public NewNotePresenter(IRBaseView view, NewNoteViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {

    }


    public void saveNote(NoteBean noteBean) {
        StyledDialog.buildLoading("保存中...").show();
        addDisposable(ServiceHelper.getNoteAS().saveNote(noteBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NoteData>() {
                    @Override
                    public void onNext(NoteData value) {
                        int status = value.getStatus();
                        String msg = value.getMsg();
                        if (status == 0) {
                            ToastUtils.showLong("保存成功");
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

    public void updateNote(NoteBean noteBean) {
        StyledDialog.buildLoading("保存中...").show();
        addDisposable(ServiceHelper.getNoteAS().updateNote(noteBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NoteData>() {
                    @Override
                    public void onNext(NoteData value) {
                        int status = value.getStatus();
                        String msg = value.getMsg();
                        if (status == 0) {
                            ToastUtils.showLong("保存成功");
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

