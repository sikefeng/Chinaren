/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    /**
     * 存放Fragment列表
     */
    private List<Fragment> list;
    /**
     * 存放Fragment相应的TAB名称
     */
    private List<String> titles;

    /**
     * 构造方法
     *
     * @param fm     FragmentManager
     * @param list   Fragment列表
     * @param titles Fragment相应的TAB名称集合
     */
    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    /**
     * 返回显示的Fragment总数
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 返回要显示的Fragment的某个实例
     */
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    /**
     * 返回每个Tab的标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
