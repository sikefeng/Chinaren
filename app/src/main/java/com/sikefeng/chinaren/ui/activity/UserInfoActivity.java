package com.sikefeng.chinaren.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.ServiceHelper;
import com.sikefeng.chinaren.entity.model.BaseData;
import com.sikefeng.chinaren.mvpvmlib.utils.LogUtils;
import com.sikefeng.chinaren.presenter.UserInfoPresenter;
import com.sikefeng.chinaren.presenter.vm.TestViewModel;
import com.sikefeng.chinaren.presenter.vm.UserInfoViewModel;
import com.sikefeng.chinaren.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserInfoActivity extends AppCompatActivity {
     private UserInfoPresenter userInfoPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userInfoPresenter=new UserInfoPresenter(new UserInfoViewModel());
        userInfoPresenter.updateMember("motto","LIVE AND LEARN!!!");
    }




}
