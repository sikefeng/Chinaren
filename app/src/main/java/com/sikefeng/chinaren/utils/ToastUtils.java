/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.sikefeng.chinaren.MyApplication;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterUtil;

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
            toast = Toast.makeText(MyApplication.getContext(), resId, Toast.LENGTH_SHORT);
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
            toast = Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT);
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
            toast = Toast.makeText(MyApplication.getContext(), resId, Toast.LENGTH_LONG);
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
            toast = Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_LONG);
        }
        toast.setText(message);
        toast.show();
    }

    public static void showBottom(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            Toast toast;
            if (text.length() < 10) {
                toast = Toast.makeText(BGAAdapterUtil.getApp(), text, Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(BGAAdapterUtil.getApp(), text, Toast.LENGTH_LONG);
            }
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, BGAAdapterUtil.dp2px(2));
            toast.show();
        }
    }
}
