/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.mvpvmlib.http;

import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public abstract class SimpleServiceFactory<S> extends BaseServiceFactory<S> {

    /**
     * 默认超时时间 10秒
     */
    private static final long DEFAULT_TIMEOUT = 10;
    /**
     * 默认超时时间 10秒
     */
//    private long defaultTime = DEFAULT_TIMEOUT;

    @Override
    protected OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //设置超时时间
//        defaultTime = (getDefaultTimeOut() == null ? DEFAULT_TIMEOUT : getDefaultTimeOut());
        clientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.retryOnConnectionFailure(true);

        setClientBuilder(clientBuilder);

        return clientBuilder.build();
    }

    /**
     * 获取默认时间
     * @return Long
     */
    @Nullable
    public abstract Long getDefaultTimeOut();

    /**
     * 设置客户端请求环境
     * @param clientBuilder OkHttpClient.Builder
     */
    public abstract void setClientBuilder(OkHttpClient.Builder clientBuilder);
}
