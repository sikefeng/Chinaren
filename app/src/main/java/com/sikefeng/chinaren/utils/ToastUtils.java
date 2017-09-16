/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.sikefeng.chinaren.XXApplication;

public final class ToastUtils {

    /**
     * 声明Toast
     */
    private static Toast toast = null;

    /**
     * 构造函数
     */
    private ToastUtils() {
    }

    /**
     * 2秒时间显示消息
     * @param resId 文本资源ID
     */
    public static void showShort(@StringRes int resId) {
        if(null == toast){
            toast = Toast.makeText(XXApplication.getContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }

    /**
     * 2秒时间显示消息
     * @param message 消息文本
     */
    public static void showShort(String message) {
        if(null == toast){
            toast = Toast.makeText(XXApplication.getContext(), message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }

    /**
     * 3.5秒时间显示消息
     * @param resId 消息文本资源ID
     */
    public static void showLong(@StringRes int resId) {
        if(null == toast){
            toast = Toast.makeText(XXApplication.getContext(), resId, Toast.LENGTH_LONG);
        }
        toast.setText(resId);
        toast.show();
    }

    /**
     * 3.5秒时间显示消息
     * @param message 文本消息内容
     */
    public static void showLong(String message) {
        if(null == toast){
            toast = Toast.makeText(XXApplication.getContext(), message, Toast.LENGTH_LONG);
        }
        toast.setText(message);
        toast.show();
    }
}
