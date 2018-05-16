
package com.sikefeng.chinaren.core;

import android.support.annotation.Nullable;

import com.sikefeng.chinaren.api.APIConfig;
import com.sikefeng.chinaren.api.XXNetInterceptor;
import com.sikefeng.chinaren.mvpvmlib.http.SimpleServiceFactory;
import com.sikefeng.chinaren.mvpvmlib.utils.LogUtils;
import com.sikefeng.chinaren.utils.SingletonUtils;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public final class ServiceFactory<S> extends SimpleServiceFactory<S> {

    /**
     * 1024位
     */
    private static final int CACHE_1024 = 1024;
    /**
     * 自定义缓存倍数大小
     */
    private static final int CACHE_100 = 100;

    /**
     * 超级构造函数
     */
    private ServiceFactory() {
    }

    /**
     * 单根类使用
     */
    private static class SingletonHolder {
        /**
         * 构造函数
         */
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Nullable
    @Override
    public Long getDefaultTimeOut() {
        return null;
    }

    @Override
    public void setClientBuilder(OkHttpClient.Builder clientBuilder) {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);
        clientBuilder.cookieJar(SingletonUtils.getCookieJar());
        File cacheFile = new File(SingletonUtils.getCacheDir(), "HttpCache"); // 指定缓存路径
        Cache cache = new Cache(cacheFile, CACHE_1024 * CACHE_1024 * CACHE_100); // 指定缓存大小100Mb
        clientBuilder.cache(cache);

        clientBuilder.addNetworkInterceptor(new XXNetInterceptor());
    }

    @Override
    public String getDefaultBaseUrl() {
        //默认BaseUrl
        return APIConfig.BASE_URL_DEFAULT;
    }

    /**
     * 网络返回信息日志
     */
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            LogUtils.i("retrofitBack = " + message);
        }
    });

}
