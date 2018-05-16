/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.mvpvmlib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GsonSingleton {

    /**
     * 定义GSON
     */
    public static final Gson _GSON = new GsonBuilder().setLenient().create();

}
