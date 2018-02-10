/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseFragment;
import com.sikefeng.chinaren.databinding.FragmentDiscoverBinding;
import com.sikefeng.chinaren.presenter.DiscoverPresenter;
import com.sikefeng.chinaren.presenter.vm.DiscoverViewModel;
import com.sikefeng.chinaren.widget.dialog.CommomDialog;
import com.sikefeng.chinaren.widget.qrcode.CaptureActivity;
import com.sikefeng.mvpvmlib.base.RBasePresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件名：Discover <br>
 * 创建时间： 24/7/17 下午PM4:51 <br>
 * 文件描述：<br>
 * 我
 *
 * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class DiscoverFragment extends BaseFragment<FragmentDiscoverBinding> implements View.OnClickListener {

    /**
     * 我界面数据协调器
     */
    private DiscoverPresenter presenter;


    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new DiscoverPresenter(this, new DiscoverViewModel());
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());

        getBinding().baiduLayout.setOnClickListener(this);
        getBinding().scanCode.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.baiduLayout:
                CommomDialog commomDialog= CommomDialog.getInstance();
                commomDialog.show(getActivity(),R.layout.dialog_commom);
                TextView textView=commomDialog.getView(R.id.tv_title);
                textView.setText("KKK");
                break;
            case R.id.scanCode:
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
            default:
                break;
        }
    }


    private boolean reg(String regex,String content){
        Pattern pattern=Pattern.compile("");
        Matcher matcher=pattern.matcher(content);
        return matcher.find();
    }



}





