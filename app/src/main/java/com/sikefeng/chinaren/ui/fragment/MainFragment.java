/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseFragment;
import com.sikefeng.chinaren.databinding.FragmentMainBinding;
import com.sikefeng.chinaren.ui.adapter.SimpleFragmentPagerAdapter;
import com.sikefeng.chinaren.utils.ResUtils;
import com.sikefeng.mvpvmlib.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends BaseFragment<FragmentMainBinding> {

    /**
     * TAB名称
     */
    private String[] titles = ResUtils.getArrStr(R.array.noticTabTitles);
    /**
     * 界面适配器
     */
    private SimpleFragmentPagerAdapter pagerAdapter;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected boolean isEnableEventBus() {
        return true;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NotificationFragment());
        fragmentList.add(new NotificationFragment());
        fragmentList.add(new NotificationFragment());
        getBinding().subviewpager.setOffscreenPageLimit(fragmentList.size());
        pagerAdapter = new SimpleFragmentPagerAdapter(getFragmentManager(), fragmentList, java.util.Arrays.asList(titles));
        getBinding().subviewpager.setAdapter(pagerAdapter);//给ViewPager设置适配器
        getBinding().slidingTabs.setupWithViewPager(getBinding().subviewpager);//将TabLayout和ViewPager关联起来
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {


    }
}
