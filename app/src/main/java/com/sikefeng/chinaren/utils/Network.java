/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.sikefeng.chinaren.XXApplication;

import java.lang.reflect.Method;


public final class Network {

    /**
     * TAG，用于记录日志
     */
    private static final String TAG = Network.class.getSimpleName();

    /**
     * 构造函数
     */
    private Network() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取连接管理器
     *
     * @param context 上下文环境
     * @return ConnectivityManager
     */
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取电话管理器
     *
     * @param context 上下文环境
     * @return TelephonyManager
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 是否已经连接
     *
     * @return true为已经连接，false没有连接
     */
    public static boolean isConnected() {
        NetworkInfo net = getConnectivityManager(XXApplication.getContext()).getActiveNetworkInfo();
        return net != null && net.isConnected();
    }

    /**
     * 是否已经连接或者正在连接中
     *
     * @return boolean，true为已经连接或在连接中。
     */
    public static boolean isConnectedOrConnecting() {
        ConnectivityManager connectivity = (ConnectivityManager)
                XXApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo nInfo = connectivity.getActiveNetworkInfo();

            if (nInfo != null && nInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取连接类型，0为移动网络，1位WIFI，默认为其它
     * @param context 上下文环境
     * @return NetType，如果连接不上，就是NetType.None
     */
    public static NetType getConnectedType(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        if (net != null) {
            switch (net.getType()) {
                case 0:
                    return NetType.Mobile;
                case 1:
                    return NetType.Wifi;
                default:
                    return NetType.Other;
            }
        } else {
            return NetType.None;
        }
    }

    /**
     * 判断是否WIFI连接
     * @param context 上下文环境
     * @return true为wifi已经连接。
     */
    public static boolean isWifiConnected(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        return net != null && net.getType() == 1 && net.isConnected();
    }

    /**
     * 判断是否移动网络已经连接
     * @param context 上下文环境
     * @return true为已经连接
     */
    public static boolean isMobileConnected(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        return net != null && net.getType() == 0 && net.isConnected();
    }

    /**
     * 判断网络是否可用
     * @param context 上下文环境
     * @return boolean true为可用
     */
    public static boolean isAvailable(Context context) {
        return isWifiAvailable(context) || isMobileAvailable(context) && isMobileEnabled(context);
    }

    /**
     * 判断是否有可用状态的Wifi，以下情况返回false：
     * 1. 设备wifi开关关掉;
     * 2. 已经打开飞行模式；
     * 3. 设备所在区域没有信号覆盖；
     * 4. 设备在漫游区域，且关闭了网络漫游。
     *
     * @param context 上下文环境
     * @return true为可用,false为不可用
     */
    public static boolean isWifiAvailable(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            NetworkInfo[] arr$ = nets;
            int len$ = nets.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                NetworkInfo net = arr$[i$];
                if (net.getType() == 1) {
                    return net.isAvailable();
                }
            }
        }

        return false;
    }

    /**
     * 判断有无可用状态的移动网络，注意关掉设备移动网络直接不影响此函数。
     * 也就是即使关掉移动网络，那么移动网络也可能是可用的(彩信等服务)，即返回true。
     * 以下情况它是不可用的，将返回false：
     * 1. 设备打开飞行模式；
     * 2. 设备所在区域没有信号覆盖；
     * 3. 设备在漫游区域，且关闭了网络漫游。
     * @param context 上下文环境
     * @return true为可用
     */
    public static boolean isMobileAvailable(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            NetworkInfo[] arr$ = nets;
            int len$ = nets.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                NetworkInfo net = arr$[i$];
                if (net.getType() == 0) {
                    return net.isAvailable();
                }
            }
        }

        return false;
    }

    /**
     * 判断是否启用移动网络，启用和可用是不一样的，启用移动网络不一定连接到移动网络。
     * @param context 上下文环境
     * @return true为已经打开，false为没有打开。
     */
    public static boolean isMobileEnabled(Context context) {
        try {
            Method e = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            e.setAccessible(true);
            return ((Boolean) e.invoke(getConnectivityManager(context), new Object[0])).booleanValue();
        } catch (Exception var2) {
            var2.printStackTrace();
            return true;
        }
    }

    /**
     * 打印网络信息
     * @param context 上下文环境
     * @return boolean
     */
    public static boolean printNetworkInfo(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo in = connectivity.getActiveNetworkInfo();
            Log.i(TAG, "-------------##############################-------------");
            Log.i(TAG, "getActiveNetworkInfo: " + in);
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; ++i) {
                    Log.i(TAG, "NetworkInfo[" + i + "]isAvailable : " + info[i].isAvailable());
                    Log.i(TAG, "NetworkInfo[" + i + "]isConnected : " + info[i].isConnected());
                    Log.i(TAG, "NetworkInfo[" + i + "]isConnectedOrConnecting : " + info[i].isConnectedOrConnecting());
                    Log.i(TAG, "NetworkInfo[" + i + "]: " + info[i]);
                }

                Log.i(TAG, "\n");
            } else {
                Log.i(TAG, "getAllNetworkInfo is null");
            }
        }

        return false;
    }

    /**
     * 获取连接类型
     * @param context 上下文环境
     * @return int，如果没有网络返回-1
     */
    public static int getConnectedTypeINT(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        if (net != null) {
            Log.i(TAG, "NetworkInfo: " + net.toString());
            return net.getType();
        } else {
            return -1;
        }
    }

    /**
     * 获取电话网络类型
     * @param context 上下文环境
     * @return 返回网络支持的网络类型
     */
    public static int getTelNetworkTypeINT(Context context) {
        return getTelephonyManager(context).getNetworkType();
    }

    /**
     * 网络类型
     * GPRS    2G(2.5) General Packet Radia Service 114kbps
     * EDGE    2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
     * UMTS    3G WCDMA 联通3G Universal MOBILE Telecommunication System 完整的3G移动通信技术标准
     * CDMA    2G 电信 Code Division Multiple Access 码分多址
     * EVDO_0  3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
     * EVDO_A  3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
     * 1xRTT   2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
     * HSDPA   3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
     * HSUPA   3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
     * HSPA    3G (分HSDPA,HSUPA) High Speed Packet Access
     * IDEN    2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
     * EVDO_B  3G EV-DO Rev.B 14.7Mbps 下行 3.5G
     * LTE     4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
     * EHRPD   3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
     * HSPAP   3G HSPAP 比 HSDPA 快些
     * @param context 上下文环境
     * @return 返回网络类型类NetWorkType
     */
    public static NetWorkType getNetworkType(Context context) {
        int type = getConnectedTypeINT(context);
        switch (type) {
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
                int teleType = getTelephonyManager(context).getNetworkType();
                switch (teleType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetWorkType.Net2G;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetWorkType.Net3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetWorkType.Net4G;
                    default:
                        return NetWorkType.UnKnown;
                }
            case ConnectivityManager.TYPE_WIFI:
                return NetWorkType.Wifi;
            default:
                return NetWorkType.UnKnown;
        }
    }

    public enum NetWorkType {
        UnKnown(-1),
        Wifi(1),
        Net2G(2),
        Net3G(3),
        Net4G(4);

        /**
         * 类型的值
         */
        private int value;

        /**
         * 构造函数，设置类别的值
         * @param value 类型的值
         */
        NetWorkType(int value) {
            this.value = value;
        }
    }

    public enum NetType {
        None(1),
        Mobile(2),
        Wifi(4),
        Other(8);
        /**
         * 类型的值
         */
        private int value;

        /**
         * 构造函数，设置类型的值
         * @param value 类型的值
         */
        NetType(int value) {
            this.value = value;
        }
    }

    /**
     * 打开网络设置界面
     * @param activity Activity
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
