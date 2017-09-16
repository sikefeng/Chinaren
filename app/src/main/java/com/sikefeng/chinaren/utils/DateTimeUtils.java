/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateTimeUtils {

    /**
     * 定义Locale，默认为中国区
     */
    private static Locale locale = Locale.CHINA;


    /**
     * 功能描述：获取当前时间的字符串类型，格式类型如："yyyy-MM-dd HH:mm:ss"
     * <br>创建时间： 2017-07-03 12:19:34

     * @param formater 时间格式
     * @return 字符串类型的日期
     */
    public static String getCurDateStr(SimpleDateFormat formater) {
        return formater.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 获取日期格式类，默认为yyyyMMddHHmmss
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat getFormatYMDHMS(){
        return new SimpleDateFormat("yyyyMMddHHmmss", locale);
    }
}
