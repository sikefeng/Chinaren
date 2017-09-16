
package com.sikefeng.chinaren.core;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.sikefeng.chinaren.utils.SwipeBackUtils;
import com.sikefeng.mvpvmlib.base.RBaseActivity;
import com.zhy.changeskin.SkinManager;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;



public abstract class BaseActivity<DB extends ViewDataBinding> extends RBaseActivity<DB> {

    /**
     * Color类里的透明度值，值范围为0~255；
     */
    private static final int COLOR_ALPHA = 112;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SwipeBackHelper.onCreate(this); //初始化右滑返回上一级界面
        SwipeBackUtils.enableSwipeActivity(this, 0.1f); //打开右滑事件
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init(); //设置沉浸式状态栏样式
        if(isEnableEventBus() && !EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        SkinManager.getInstance().register(this); //皮肤管理，可以切换白天和晚上的样式

        if(null != getToolbar()){
            getToolbar().setTitle("");
            setSupportActionBar(getToolbar());
            try {
                //给左上角图标的左边加上一个返回的图标
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }catch (NullPointerException e){}
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this); //右滑事件
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isEnableEventBus() && EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        SkinManager.getInstance().unregister(this);
        SwipeBackHelper.onDestroy(this);

        ImmersionBar.with(this).destroy(); //销毁沉浸式样式
    }

    /**
     * init之前调用的方法 做项目的特殊初始化工作
     * @param savedInstanceState Bundle
     */
    @Override
    protected void preOnCreate(Bundle savedInstanceState) {

    }


    /**
     * 功能描述：设置状态栏图标、文字颜色
     * <br>创建时间： 2017-07-02 11:30:01
     * @param bDark 是否设置系统样式
     */
    protected void setDarkStatusIcon(boolean bDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Class<? extends Window> clazz = getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(getWindow(), bDark ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (getWindow() != null) {
            try {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (bDark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                getWindow().setAttributes(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected Toolbar getToolbar(){
        return null;
    }

    //是否设置状态栏为透明
    protected boolean isTranslucentStatusBar() {
        return true;
    }

    //是否启用EventBus
    protected boolean isEnableEventBus(){
        return false;
    }

}
