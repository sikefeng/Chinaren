/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseFragment;
import com.sikefeng.chinaren.databinding.FragmentDiscoverBinding;
import com.sikefeng.chinaren.presenter.DiscoverPresenter;
import com.sikefeng.chinaren.presenter.vm.DiscoverViewModel;
import com.sikefeng.chinaren.utils.ResUtils;
import com.sikefeng.chinaren.widget.PopupDialog;
import com.sikefeng.chinaren.widget.WheelView;
import com.sikefeng.chinaren.widget.qrcode.CaptureActivity;
import com.sikefeng.mvpvmlib.base.RBasePresenter;
import com.sikefeng.mvpvmlib.utils.LogUtils;

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
        getBinding().checkWorkAres.setOnClickListener(this);

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
//                startActivity(new Intent(getActivity(), WebActivity.class));
                showWheelView(view);
                break;
            case R.id.checkWorkAres:
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
            default:
                break;
        }
    }

    private void showWheelView(View view){
        PopupDialog popupDialog = new PopupDialog(getActivity(), R.layout.popup_wheelview);
        popupDialog.setAnimation(android.R.style.Animation_InputMethod);
        popupDialog.showAtLocation(view, Gravity.CENTER);
        WheelView wheelView=popupDialog.getView(R.id.wheel);
        wheelView.setTextColor(ResUtils.getColor(R.color.theme_color));
        wheelView.addData("轰趴馆");
        wheelView.addData("台球");
        wheelView.addData("密室逃脱");
        wheelView.addData("卡丁车");
        wheelView.addData("桌游");
        wheelView.addData("真人CS");
        wheelView.addData("DIY");
        wheelView.setCenterItem(4);

        popupDialog.getView(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i("nowData--->"+wheelView.getCenterItem());
            }
        });

    }

}





