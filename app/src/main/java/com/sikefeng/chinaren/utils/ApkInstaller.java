package com.sikefeng.chinaren.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;




public class ApkInstaller {
    /**
     * 定义Activity
     */
    private Activity mActivity;

    /**
     * 构造函数
     * @param activity Activity
     */
    public ApkInstaller(Activity activity) {
        mActivity = activity;
    }



    /**
     * 如果服务组件没有安装打开语音服务组件下载页面，进行下载后安装。
     * @param context Context
     * @param url String
     * @param assetsApk assetsApk
     * @return boolean
     */
    private boolean processInstall(Context context, String url, String assetsApk) {
        //直接下载方式
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
        return true;
    }
}
