package com.sikefeng.chinaren.utils;

import android.annotation.SuppressLint;
import android.content.Context;

public final class AppUtils {
    /**
     * 定义上下文环境
     */
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    /**
     * 定义构造函数
     */
    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        AppUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("u should init first");
    }
}
