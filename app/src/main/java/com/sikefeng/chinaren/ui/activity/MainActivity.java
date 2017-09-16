/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.ui.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityMainBinding;
import com.sikefeng.chinaren.entity.event.MainEvent;
import com.sikefeng.chinaren.entity.event.RvScrollEvent;
import com.sikefeng.chinaren.ui.adapter.SimpleFragmentPagerAdapter;
import com.sikefeng.chinaren.ui.fragment.MainFragment;
import com.sikefeng.chinaren.ui.fragment.MyFragment;
import com.sikefeng.chinaren.ui.fragment.NotificationFragment;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.ResUtils;
import com.sikefeng.chinaren.utils.SwipeBackUtils;
import com.sikefeng.mvpvmlib.base.RBasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


@Route(path = Constants.MAIN_URL, group = Constants.APP_GOUP)
public class MainActivity extends BaseActivity<ActivityMainBinding> {


    /**
     * TAB名称集合
     */
    private String[] titles = ResUtils.getArrStr(R.array.tabTitles);
    /**
     * SimpleFragmentPagerAdapter适配器
     */
    private SimpleFragmentPagerAdapter pagerAdapter;



    /**
     * 图片数组
     */
    private int[] mImgs = new int[]{R.drawable.selector_tab_weixin, R.drawable.selector_tab_friends,
            R.drawable.selector_tab_me};
    /**
     * 关闭MainActivity
     */
    private String finishActivity = "finish";
    /**
     * 订阅事件
     *
     * @param event 自定义事件类
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainEvent event) {
        String type = event.getType();
        if (type.equals(finishActivity)) {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads().detectDiskWrites().detectNetwork()
                    .penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                    .penaltyLog().penaltyDeath().build());
        }
        SwipeBackUtils.disableSwipeActivity(this);

//        getBinding().toolbar.setTitle(getString(R.string.app_name));
        getBinding().toolbarTitle.setText(getString(R.string.app_name));



        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new NotificationFragment());
//        fragmentList.add(new ServiceRequestFragment());
        fragmentList.add(new MyFragment());
        getBinding().viewpager.setOffscreenPageLimit(fragmentList.size());
        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, java.util.Arrays.asList(titles));

        getBinding().viewpager.setAdapter(pagerAdapter);//给ViewPager设置适配器
        getBinding().tablayout.setupWithViewPager(getBinding().viewpager);//将TabLayout和ViewPager关联起来

        getBinding().tablayout.setSelectedTabIndicatorHeight(0);
        for (int i = 0; i < titles.length; i++) {
            //获得到对应位置的Tab
            TabLayout.Tab itemTab = getBinding().tablayout.getTabAt(i);
            if (itemTab != null) {
                //设置自定义的标题
                itemTab.setCustomView(R.layout.item_tab);
                TextView textView = (TextView) itemTab.getCustomView().findViewById(R.id.tv_name);
                textView.setText(titles[i]);
                ImageView imageView = (ImageView) itemTab.getCustomView().findViewById(R.id.iv_img);
                imageView.setImageResource(mImgs[i]);
            }
        }

//        TabLayout.Tab itemTab = getBinding().tablayout.getTabAt(0);
//        new QBadgeView(this).bindTarget(itemTab.getCustomView()).setBadgeNumber(5).setGravityOffset(10, 0, false);
        getBinding().tablayout.getTabAt(0).getCustomView().setSelected(true);

        //演示“发送事件” （功能可以用FragmentA的实例调用内部方法实现滑动到顶部，eg: fragmentA.scrollToTop(); 在FragmentA中实现滚动方法即可）
        getBinding().toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //list回到顶部
                int tabIndex = getBinding().viewpager.getCurrentItem();
                EventBus.getDefault().post(new RvScrollEvent(tabIndex, 0));
            }
        });

        getBinding().viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                getBinding().toolbar.setTitle(tabTitles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }



}