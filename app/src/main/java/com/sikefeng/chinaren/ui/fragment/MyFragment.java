/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseFragment;
import com.sikefeng.chinaren.databinding.FragmentMyBinding;
import com.sikefeng.chinaren.presenter.MyFragmentPresenter;
import com.sikefeng.chinaren.presenter.vm.MyFragmentViewModel;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;
import com.sikefeng.mvpvmlib.base.RBasePresenter;
import com.zhy.changeskin.SkinManager;

import static com.hss01248.dialog.StyledDialog.context;

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
        getBinding().personalData.setOnClickListener(this);
        getBinding().updatePwd.setOnClickListener(this);
        getBinding().checkWorkAres.setOnClickListener(this);
        getBinding().workStuas.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        //未保存switch状态
        getBinding().switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SkinManager.getInstance().changeSkin(isChecked ? "night" : "");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exitLogin:
                StyledDialog.buildIosAlert("退出登录提示", "是否确认退出当前账号?", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        String token = (String) SharePreferenceUtils.get(context, Constants.TOKEN, "");
                        presenter.exitLogin(token);
                    }

                    @Override
                    public void onSecond() {

                    }


                }).setBtnText("确定", "取消").show();
                break;
            case R.id.personalData:

                break;
            case R.id.updatePwd:
                ARouter.getInstance().build(Constants.UPDATE_PWD_URL, Constants.APP_GOUP).navigation();
                break;
            case R.id.checkWorkAres:

                break;
            case R.id.workStuas:

                break;
            default:
                break;
        }
    }
}





