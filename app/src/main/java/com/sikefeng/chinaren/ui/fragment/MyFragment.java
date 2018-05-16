/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.sikefeng.chinaren.MyApplication;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseFragment;
import com.sikefeng.chinaren.databinding.FragmentMyBinding;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.MyFragmentPresenter;
import com.sikefeng.chinaren.presenter.vm.MyFragmentViewModel;
import com.sikefeng.chinaren.ui.adapter.RecyclerGridAdapter;
import com.sikefeng.chinaren.utils.ImageUtils;
import com.sikefeng.chinaren.widget.dialog.CommomDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 文件名：MyFragment <br>
 * 创建时间： 24/7/17 下午PM4:51 <br>
 * 文件描述：<br>
 * 我
 *
 * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class MyFragment extends BaseFragment<FragmentMyBinding> implements View.OnClickListener {
    public static final String SHARED_ELEMENT_NAME = "SHARED_ELEMENT_NAME";
    /**
     * 我界面数据协调器
     */
    private MyFragmentPresenter presenter;


    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new MyFragmentPresenter(this, new MyFragmentViewModel());
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());

        getBinding().exitLogin.setOnClickListener(this);
        getBinding().updatePwd.setOnClickListener(this);
        getBinding().tvLogin.setOnClickListener(this);

        String url_path = "http://img1.imgtn.bdimg.com/it/u=3525092935,1107570256&fm=27&gp=0.jpg";
        getBinding().headView.setImageURI(Uri.parse(url_path));

        ImageUtils.scanImage(getActivity(), getBinding().headView, url_path);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exitLogin:
                StyledDialog.buildIosAlert("退出登录提示", "是否确认退出当前账号?", new MyDialogListener() {
                    @Override
                    public void onFirst() {
//                        startActivity(new Intent(getActivity(), BaseMapActivity.class));
                        initShare(view);
                    }

                    @Override
                    public void onSecond() {
//                        startActivity(new Intent(getActivity(), LocationActivity.class));


                    }
                }).setBtnText("确定", "取消").show();
                break;
            case R.id.updatePwd:
//                ARouter.getInstance().build(Constants.UPDAT_PWD_URL).navigation();
                update();
                break;
            case R.id.tvLogin:

                break;

            default:
                break;
        }
    }

    private static final String APATCH_PATH = "/fix.apatch"; // 补丁文件名

    /**
     * 动态更新，加载补丁文件
     */
    private void update() {
        String patchFileStr = Environment.getExternalStorageDirectory().getAbsolutePath() + APATCH_PATH;
        try {
            MyApplication.mPatchManager.addPatch(patchFileStr);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (new File(patchFileStr).exists()) {
            Log.e("Test", "have some patch");
            try {
                MyApplication.getInstance().getPatchManager().addPatch(patchFileStr);
            } catch (Exception e) {
                Log.e("Test", "Test", e);
            }
        } else {
            Log.e("Test", "have no patch");
        }
    }

    /**
     * 功能描述  ShareSDK分享
     * <br>创建时间： 2018-01-17 23:01:52
     *
     * @param view
     * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a>
     */
    private void initShare(View view) {
        CommomDialog commomDialog = CommomDialog.getInstance();
        commomDialog.show(getActivity(), R.layout.popup_share);
        RecyclerView mRecyclerView = commomDialog.getView(R.id.recyclerView);
        ArrayList<RecyclerGridAdapter.ShareBean> lists = new ArrayList<RecyclerGridAdapter.ShareBean>();

        RecyclerGridAdapter.ShareBean bean = new RecyclerGridAdapter.ShareBean();
        bean.setImgRes(R.mipmap.share_qq);
        bean.setShareName("QQ");
        lists.add(bean);

        RecyclerGridAdapter.ShareBean bean2 = new RecyclerGridAdapter.ShareBean();
        bean2.setImgRes(R.mipmap.share_wx);
        bean2.setShareName("微信");
        lists.add(bean2);

        RecyclerGridAdapter.ShareBean bean3 = new RecyclerGridAdapter.ShareBean();
        bean3.setImgRes(R.mipmap.share_wb);
        bean3.setShareName("微博");
        lists.add(bean3);

        // 两列
        int spanCount = 3;
        // StaggeredGridLayoutManager管理RecyclerView的布局。
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        RecyclerGridAdapter mAdapter = new RecyclerGridAdapter(lists);
        mRecyclerView.setAdapter(mAdapter);
    }



}





