/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.mvpvmlib.http.converter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.sikefeng.chinaren.mvpvmlib.utils.GsonSingleton;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import retrofit2.Converter;


final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    /**
     * 网络请求类型
     */
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    /**
     * 适配器类型
     */
    private final TypeAdapter<T> adapter;

    /**
     * 定义构造函数
     * @param adapter 适配器
     */
    GsonRequestBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), Util.UTF_8);
        JsonWriter jsonWriter = GsonSingleton._GSON.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
