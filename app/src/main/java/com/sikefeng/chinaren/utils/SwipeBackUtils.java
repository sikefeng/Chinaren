/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;

import android.app.Activity;

import com.jude.swipbackhelper.SwipeBackHelper;

public class SwipeBackUtils {

    /**
     * 关闭向右滑动返回事件
     * @param activity Activity
     */
    public static void disableSwipeActivity(Activity activity) {
        SwipeBackHelper.getCurrentPage(activity)
                .setSwipeBackEnable(false);
    }

    /**
     * 打开向右滑动返回事件
     * @param activity Activity
     * @param percent Float 互动触发事件的比例
     */
    public static void enableSwipeActivity(Activity activity, Float percent) {
        SwipeBackHelper.getCurrentPage(activity)
                .setSwipeBackEnable(true)
                .setSwipeEdgePercent(percent == null ? 0.1f : percent);
    }

}
