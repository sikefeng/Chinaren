/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;

import android.Manifest;


public class PermissionConstans {

    /***
     * 检查定位需要的权限,在Presenter中直接如下使用
     * checkPermissions(onNext, LocationPermissions);
     */
    public static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    /**
     * 检查选择图片，包括使用摄像头等权限
     */
    public static final String[] CHOOSE_IMG_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 检查外部存储的权限
     */
    public static final String[] STORAGE_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 检查发送短信权限
     */
    public static final String[] SEND_SMS_PERMISSIONS = {
            Manifest.permission.SEND_SMS
    };

    /**
     * 检查打电话权限
     */
    public static final String[] CALL_PHONE_PERMISSIONS = {
            Manifest.permission.CALL_PHONE
    };

    /**
     * 检查获取手机状态的权限
     */
    public static final String[] READ_PHONE_STATE_PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE
    };


}
