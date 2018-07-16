/**
 * Copyright (C) 2014-2017 <a href="http://www.xxxxtech.com>">XXXXTech</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.presenter;

import android.content.Intent;

import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.ImageBean;
import com.sikefeng.chinaren.entity.model.ImageListData;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.mvpvmlib.utils.LogUtils;
import com.sikefeng.chinaren.presenter.vm.ImageListViewModel;
import com.sikefeng.chinaren.ui.activity.GridImageActivity;
import com.sikefeng.chinaren.utils.ResUtils;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件名：ProcessedListPresenter <br>
 * 创建时间： 7/8/17 下午PM9:07 <br>
 * 文件描述：<br>
 * 已处理列表数据协调器
 *
 * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class ImageListPresenter extends BasePresenter<IRBaseView, ImageListViewModel> {
    /**
     * 每页显示数量
     */
    private int count = ResUtils.getInteger(R.integer.load_count);
    /**
     * 当前页码
     */
    private int page = 1;

    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public ImageListPresenter(IRBaseView view, ImageListViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {
        getViewModel().getAdapter().setItemEventHandler(this);
        loadData(true);
    }

    @Override
    public void loadData(final boolean isRefresh) {

        addDisposable(ServiceHelper.getNoteAPI().imageList(String.valueOf(isRefresh ? 1 : page), String.valueOf(count),"updateDate")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ImageListData>() {
                    @Override
                    public void onNext(ImageListData value) {
                        int status = value.getStatus();
                        String msg = value.getMsg();
                        LogUtils.i(status + "=========" + msg);
                        if (status == 0) {
                            page = isRefresh ? 2 : ++page;
                            getViewModel().setData(value,isRefresh);
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

    /**
     * 点击列表某个项的事件
     *
     * @param holder 自定义ViewHolder
     * @param model  数据实体类
     */
    public void onClickItem(BGABindingViewHolder holder, ImageBean model){

        Intent intent=new Intent(getContext(), GridImageActivity.class);
        intent.putExtra("imageList",model.getImageList());
        intent.putExtra("title",model.getTitle());
        getContext().startActivity(intent);

//        String json = model.getImageList();
//        json = json.replaceAll(" ", "");
//        String[] arr = json.split(",");
//        List<String> imageList2= Arrays.asList(arr);
//        ArrayList<String> imageList=new ArrayList<>();
//        imageList.addAll(imageList2);
//        getContext().startActivity(BGAPhotoPreviewActivity.newIntent(getContext(),  null, (ArrayList<String>) imageList, 1));
    }






}

