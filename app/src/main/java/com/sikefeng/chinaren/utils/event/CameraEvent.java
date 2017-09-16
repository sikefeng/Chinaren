/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils.event;

public class CameraEvent {

    /**
     * 返回消息
     */
    private String mMsg;

    /**
     * 构造函数
     * @param msg 消息内容
     */
    public CameraEvent(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}
