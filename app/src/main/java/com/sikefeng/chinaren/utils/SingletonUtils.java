/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.sikefeng.chinaren.MyApplication;

import java.io.File;


public class SingletonUtils {

    /**
     * 定义ClearableCookieJar
     */
    private static ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getContext()));
    /**
     * 定义缓存文件目录
     */
    private static File cacheDir = MyApplication.getContext().getCacheDir();

    public static ClearableCookieJar getCookieJar() {
        return cookieJar;
    }

    public static File getCacheDir() {
        return cacheDir;
    }
}
