/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.fragment;

import android.content.Intent;
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
import com.sikefeng.chinaren.presenter.MyFragmentPresenter;
import com.sikefeng.chinaren.presenter.vm.MyFragmentViewModel;
import com.sikefeng.chinaren.ui.activity.BaseMapActivity;
import com.sikefeng.chinaren.ui.activity.LocationActivity;
import com.sikefeng.mvpvmlib.base.RBasePresenter;

import java.io.File;
import java.io.IOException;

import static android.R.attr.path;

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

        getBinding().headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
                        startActivity(new Intent(getActivity(), BaseMapActivity.class));
                    }

                    @Override
                    public void onSecond() {
                        startActivity(new Intent(getActivity(), LocationActivity.class));
                    }
                }).setBtnText("确定", "取消").show();
                break;
            case R.id.updatePwd:
//                ARouter.getInstance().build(Constants.UPDAT_PWD_URL).navigation();
                update();
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





