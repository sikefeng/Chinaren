/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;


import com.sikefeng.chinaren.MyApplication;

import java.util.List;


public class CommonUtils {

    /**
     * px转dp
     * @param pxValue px的值
     * @return int dp的值
     */
    public static int px2dp(float pxValue) {
        float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转px
     * @param dipValue dp的值
     * @return px的值
     */
    public static int dp2px(float dipValue) {
        float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转sp
     * @param pxValue 输入px的值
     * @return 输出sp的值
     */
    public static int px2sp(float pxValue) {
        float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp转px
     * @param spValue 输入sp的值
     * @return 输出px的值
     */
    public static int sp2px(float spValue) {
        float fontScale = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 判断一个对象缩放为空，包括判断null
     * @param datas 可以是List，也可以是String
     * @return boolean，true为空。
     */
    public static boolean isEmpty(Object datas){
        boolean isEmpty = (null == datas);
        if (datas instanceof List) {
            isEmpty = (((List) datas).size() == 0);
        }
        return isEmpty;
    }

}
