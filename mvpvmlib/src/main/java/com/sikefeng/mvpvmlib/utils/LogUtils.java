/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.mvpvmlib.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


public class LogUtils {

    /**
     * 定义isPrintResponseData
     */
    private static boolean isPrintResponseData = true;

    public static boolean isPrintResponseData() {
        return isPrintResponseData;
    }

    public static void setIsPrintResponseData(boolean isPrintResponseData) {
        LogUtils.isPrintResponseData = isPrintResponseData;
    }

    /**
     * 更多查看：https://github.com/orhanobut/logger
     * @param isDebug 是否debug
     */
    public static void init(final boolean isDebug){
        /*FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));*/
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }
        });
    }

    /**
     * 错误信息
     * @param msg 消息
     * @param obj 对象
     */
    public static void e(String msg, Object... obj) {
        Logger.e(msg, obj);
    }

    /**
     * 警告信息
     * @param msg 消息
     * @param obj 对象
     */
    public static void w(String msg, Object... obj) {
        Logger.w(msg, obj);
    }

    /**
     * 调试信息
     * @param obj 对象
     */
    public static void d(Object obj) {
        Logger.d(obj);
    }
    /**
     * 调试消息
     * @param msg 消息
     * @param obj 对象
     */
    public static void d(String msg, Object... obj) {
        Logger.d(msg, obj);
    }

    /**
     * 提示信息
     * @param msg 消息
     * @param obj 对象
     */
    public static void i(String msg, Object... obj) {
        Logger.i(msg, obj);
    }

    /**
     * 转JSON格式
     * @param json json内容
     */
    public static void json(String json) {
        Logger.json(json);
    }

    /**
     * 打印错误消息
     * @param e Throwable
     */
    public static void e(Throwable e) {
        Logger.e(e.getMessage());
    }
}
