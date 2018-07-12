/**
 * Copyright (C) 2014-2017 <a href="http://www.xxxxtech.com>">XXXXTech</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.presenter;

import com.google.gson.Gson;
import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.ImageBean;
import com.sikefeng.chinaren.entity.model.ImageListData;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.mvpvmlib.utils.LogUtils;
import com.sikefeng.chinaren.presenter.vm.ImageListViewModel;
import com.sikefeng.chinaren.utils.ResUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
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

        addDisposable(ServiceHelper.getNoteAS().imageList(String.valueOf(isRefresh ? 1 : page), String.valueOf(count),"updateDate")
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
        Gson gson=new Gson();
        String images= null;
        try {
            images = URLEncoder.encode("[http://img0.ph.126.net/3q73pBthPNycdWz8XfGEZA==/6597824031635530833.jpg,http://img2.ph.126.net/TANL1coRj9e7Cnd13lMO5Q==/2603925009570594791.jpg,http://img2.ph.126.net/C7Yfuu0z91zpiFYAwI3eQ==/6599291879658164075.jpg,http://img2.ph.126.net/qa1elCi81Z4Aw4aWrXJRJw==/1287748018471572992.jpg,http://img1.ph.126.net/r9N-sqtRR5oR4Cu3NTVxWA==/1447625805323285290.jpg,http://img1.ph.126.net/tQU--OI6pMAXWNH5Wa6wxg==/1603844417397734017.jpg]", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] arr=gson.fromJson(images,String[].class);
        List<String> imageList2= Arrays.asList(arr);
        System.out.println("kkkkkkkkkkkkkk="+arr[0]);
        ArrayList<String> imageList=new ArrayList<>();
        imageList.addAll(imageList2);
//        Gson gson=new Gson();
//        String[] arr=gson.fromJson(model.getImageList(),String[].class);
//        List<String> imageList= Arrays.asList(arr);
//        System.out.println("kkkkkkkkkkkkkk="+arr[0]);
        getContext().startActivity(BGAPhotoPreviewActivity.newIntent(getContext(),  null, (ArrayList<String>) imageList, 1));
    }






}

