/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.mvpvmlib.http.converter;

import com.google.gson.TypeAdapter;
import com.sikefeng.chinaren.mvpvmlib.utils.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    /**
     * 适配器
     */
    private final TypeAdapter<T> adapter;

    /**
     * 构造函数
     * @param adapter 适配器
     */
    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String body = value.string();

        if(LogUtils.isPrintResponseData()) {
            LogUtils.w(body);
        }

        try {
            return adapter.fromJson(body);
        } finally {
            value.close();
        }
    }
}
