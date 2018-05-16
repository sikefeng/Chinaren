/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.mvpvmlib.http.converter;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.sikefeng.chinaren.mvpvmlib.utils.GsonSingleton;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public final class GsonConverterFactory extends Converter.Factory {

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     * @return GsonConverterFactory
     */
    public static GsonConverterFactory create() {
        return new GsonConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = GsonSingleton._GSON.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = GsonSingleton._GSON.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(adapter);
    }
}
