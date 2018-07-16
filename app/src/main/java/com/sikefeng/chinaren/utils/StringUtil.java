package com.sikefeng.chinaren.utils;

/**
 * Created by Administrator on 2018\7\16 0016.
 */

public class StringUtil {

    public static boolean isBlank(String txt) {
        if (txt == null || "".equals(txt)) {
            return false;
        }
        return true;
    }

}
