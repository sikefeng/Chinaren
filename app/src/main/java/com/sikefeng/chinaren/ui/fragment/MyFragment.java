/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.sikefeng.chinaren.ui.activity.NoteListActivity;
import com.sikefeng.chinaren.utils.ImageUtils;
import com.sikefeng.chinaren.widget.qrcode.CaptureActivity;

import java.io.File;
import java.io.IOException;

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

    private Context mContext;


    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new MyFragmentPresenter(this, new MyFragmentViewModel());
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext=getActivity();
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());

        getBinding().baiduLayout.setOnClickListener(this);
        getBinding().scanCode.setOnClickListener(this);
        getBinding().exitLogin.setOnClickListener(this);
        getBinding().linearNote.setOnClickListener(this);


        String url_path = "http://img1.imgtn.bdimg.com/it/u=3525092935,1107570256&fm=27&gp=0.jpg";
        getBinding().headView.setImageURI(Uri.parse(url_path));

        ImageUtils.previewImage(getActivity(), getBinding().headView, url_path);


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
            case R.id.baiduLayout:

                break;
            case R.id.scanCode:
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
            case R.id.exitLogin:
                StyledDialog.buildIosAlert("退出登录提示", "是否确认退出当前账号?", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        startActivity(new Intent(mContext, NoteListActivity.class));

                    }

                    @Override
                    public void onSecond() {


                    }
                }).setBtnText("确定", "取消").show();
                break;
            case R.id.linear_note:
                startActivity(new Intent(mContext, NoteListActivity.class));

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



}





