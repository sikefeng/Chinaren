
package com.sikefeng.chinaren.api;

import com.alibaba.android.arouter.utils.TextUtils;
import com.sikefeng.chinaren.MyApplication;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;
import com.sikefeng.mvpvmlib.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class XXNetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String token = (String) SharePreferenceUtils.get(MyApplication.getContext(), Constants.TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", token);
        }
        LogUtils.e(request.url().url().toString());//打印要访问的地址 可注释
        request = builder.build();
        return chain.proceed(request);
    }
}
