///**
// * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
// */
//package com.sikefeng.chinaren.ui.activity;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.support.design.widget.TabLayout;
//import android.support.v4.BuildConfig;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.jude.swipbackhelper.SwipeBackHelper;
//import com.sikefeng.chinaren.R;
//import com.sikefeng.chinaren.core.BaseActivity;
//import com.sikefeng.chinaren.databinding.ActivityMainBinding;
//import com.sikefeng.chinaren.entity.event.MainEvent;
//import com.sikefeng.chinaren.entity.event.RvScrollEvent;
//import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
//import com.sikefeng.chinaren.ui.adapter.SimpleFragmentPagerAdapter;
//import com.sikefeng.chinaren.ui.fragment.ContactsFragment;
//import com.sikefeng.chinaren.ui.fragment.DiscoverFragment;
//import com.sikefeng.chinaren.ui.fragment.HomeFragment;
//import com.sikefeng.chinaren.ui.fragment.MyFragment;
//import com.sikefeng.chinaren.utils.AppExit2Back;
//import com.sikefeng.chinaren.utils.Constants;
//import com.sikefeng.chinaren.utils.ResUtils;
//import com.sikefeng.chinaren.utils.ToastUtils;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Route(path = Constants.MAIN_URL)
//public class MainActivity extends BaseActivity<ActivityMainBinding> {
//
//    /**
//     * TAB名称集合
//     */
//    private String[] tabTitles = ResUtils.getArrStr(R.array.tabTitles);
//    /**
//     * SimpleFragmentPagerAdapter适配器
//     */
//    private SimpleFragmentPagerAdapter pagerAdapter;
//
//    private DrawerLayout drawer_layout;
//
//    /**
//     * 图片数组
//     */
//    private int[] mImgs = new int[]{R.drawable.selector_tab_weixin, R.drawable.selector_tab_friends,R.drawable.selector_tab_search,
//            R.drawable.selector_tab_me};
//
//    /**
//     * 订阅事件
//     *
//     * @param event 自定义事件类
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MainEvent event) {
//
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected RBasePresenter getPresenter() {
//        return null;
//    }
//
//
//    @Override
//    protected void init(Bundle savedInstanceState) {
//
//        drawer_layout = getBinding().drawerLayout;
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, getBinding().toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer_layout.addDrawerListener(toggle);
//        toggle.syncState();
//
//
//        EventBus.getDefault().register(this);
//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads().detectDiskWrites().detectNetwork()
//                    .penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//                    .penaltyLog().penaltyDeath().build());
//        }
////        SwipeBackUtils.disableSwipeActivity(this);
//
//        SwipeBackHelper.getCurrentPage(this)//获取当前页面
//                .setSwipeBackEnable(false);//设置是否可滑动
//        SwipeBackHelper.getCurrentPage(this).setSwipeRelateEnable(false);
//        List<Fragment> fragmentList = new ArrayList<>();
//        fragmentList.add(new HomeFragment());
//        fragmentList.add(new ContactsFragment());
////        fragmentList.add(NewsTabLayout.getInstance());
//        fragmentList.add(new DiscoverFragment());
////        fragmentList.add(new newsFragment());
//        fragmentList.add(new MyFragment());
//        getBinding().viewpager.setOffscreenPageLimit(fragmentList.size());
//        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, java.util.Arrays.asList(tabTitles));
//
//        getBinding().viewpager.setAdapter(pagerAdapter);//给ViewPager设置适配器
//        getBinding().tablayout.setupWithViewPager(getBinding().viewpager);//将TabLayout和ViewPager关联起来
//
//        getBinding().tablayout.setSelectedTabIndicatorHeight(0);
//        for (int i = 0; i < tabTitles.length; i++) {
//            //获得到对应位置的Tab
//            TabLayout.Tab itemTab = getBinding().tablayout.getTabAt(i);
//            if (itemTab != null) {
//                //设置自定义的标题
//                itemTab.setCustomView(R.layout.item_tab);
//                TextView textView = (TextView) itemTab.getCustomView().findViewById(R.id.tv_name);
//                textView.setText(tabTitles[i]);
//                ImageView imageView = (ImageView) itemTab.getCustomView().findViewById(R.id.iv_img);
//                imageView.setImageResource(mImgs[i]);
//            }
//        }
//
//        getBinding().tablayout.getTabAt(0).getCustomView().setSelected(true);
//
//        //演示“发送事件” （功能可以用FragmentA的实例调用内部方法实现滑动到顶部，eg: fragmentA.scrollToTop(); 在FragmentA中实现滚动方法即可）
//        getBinding().toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //list回到顶部
//                int tabIndex = getBinding().viewpager.getCurrentItem();
//                EventBus.getDefault().post(new RvScrollEvent(tabIndex, 0));
//            }
//        });
//        getBinding().toolbarTitle.setText(tabTitles[0]);
//        getBinding().viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                getBinding().toolbarTitle.setText(tabTitles[position]);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//        test();
//    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            AppExit2Back.exitApp(MainActivity.this);//双击退出应用程序
//        }
//        return false;
//    }
//
//
//    private void test(){
//        //网络监听
//        IntentFilter intentFilter = new IntentFilter();
//        //addAction
//        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(new NetworkChangeReceive(), intentFilter);
//    }
//    //网络连接监听
//    class NetworkChangeReceive extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            if (networkInfo != null && networkInfo.isAvailable()) {
//                ToastUtils.showShort("网络已连接!");
//            } else {
//                ToastUtils.showShort("网络已断开,请重新连接!");
//            }
//        }
//    }
//
////    private MyHandler myHandler = new MyHandler(this);
////    private static class MyHandler extends Handler {
////        private WeakReference<Activity> reference;
////        public MyHandler(Activity activity) {
////            reference = new WeakReference<>(activity);
////        }
////        @Override
////        public void handleMessage(Message msg) {
////            Activity activity = reference.get();
////            if(activity == null){
////                return;
////            }
////        }
////    }
////    @Override
////    protected void onDestroy() {
////        super.onDestroy();
////        myHandler.removeCallbacksAndMessages(null);
////    }
//
//
//
//}
