/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.sikefeng.chinaren.XXApplication;



public class ResUtils {

    /**
     * 功能描述：根据ID获取颜色
     * <br>创建时间： 2017-07-03 14:25:06

     * @param colorId 颜色ID
     * @return 颜色的INT
     */
    public static int getColor(@ColorRes int colorId){
        return ContextCompat.getColor(XXApplication.getContext(), colorId);
    }

    /**
     * 根据字符串ID获取字符
     * @param strId 字符串ID
     * @return 字符串名称
     */
    public static String getString(@StringRes int strId){
        return XXApplication.getContext().getString(strId);
    }

    /**
     * 根据integerId获取int
     * @param integerId integerId
     * @return int
     */
    public static int getInteger(@IntegerRes int integerId){
        return XXApplication.getContext().getResources().getInteger(integerId);
    }

    /**
     * 根据数组ID获取数组
     * @param arrId 数组ID
     * @return String[]
     */
    public static String[] getArrStr(int arrId){
        return XXApplication.getContext().getResources().getStringArray(arrId);
    }

    /**
     * 获取整型数组
     * @param arrId 数组ID
     * @return int[]
     */
    public static int[] getArrInt(int arrId){
        return XXApplication.getContext().getResources().getIntArray(arrId);
    }

    /**
     * 获取文件资源
     * @param imgId 资源ID
     * @return Drawable
     */
    public static Drawable getDrawable(@DrawableRes int imgId){
        return ContextCompat.getDrawable(XXApplication.getContext(), imgId);
    }

    /**
     * 获取Dimen单位值
     * @param dimenId dimenId
     * @return float数值
     */
    public static float getDimen(@DimenRes int dimenId){
        return XXApplication.getContext().getResources().getDimension(dimenId);
    }
}
