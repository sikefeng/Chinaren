package com.sikefeng.chinaren.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.sikefeng.chinaren.entity.model.AppBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AppUtil {

    /***
     * 一种常用单例模式
     */
    private static AppUtil instance = null;
    private Context mContext;

    public AppUtil(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public static AppUtil getInstance(Context mContext) {
        // 使用时生成实例，提高了效率！
        if (instance == null)
            instance = new AppUtil(mContext);
        return instance;
    }


    /**
     * 功能描述：TODO: 获取手机已安装APP应用列表
     * 创建时间： 2018-05-23 14:30:05
     *
     * @return
     * @author Sikefeng
     */
    public List<AppBean> getAllApk() {
        List<AppBean> appBeanList = new ArrayList<>();
        AppBean bean = null;
        PackageManager packageManager = mContext.getPackageManager();
        List<PackageInfo> list = packageManager.getInstalledPackages(0);
        for (PackageInfo p : list) {
            bean = new AppBean();
            bean.setAppIcon(p.applicationInfo.loadIcon(packageManager));
            bean.setAppName(packageManager.getApplicationLabel(p.applicationInfo).toString());
            bean.setAppPackageName(p.applicationInfo.packageName);
            bean.setApkPath(p.applicationInfo.sourceDir);
            File file = new File(p.applicationInfo.sourceDir);
            bean.setAppSize((int) file.length());
            int flags = p.applicationInfo.flags;
            //判断是否是属于系统的apk
            if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                bean.setSystem(true);
            } else {
                bean.setSd(true);
            }
            appBeanList.add(bean);
        }
        return appBeanList;
    }


}
