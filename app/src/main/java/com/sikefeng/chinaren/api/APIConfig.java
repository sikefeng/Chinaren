
package com.sikefeng.chinaren.api;


import com.sikefeng.chinaren.MyApplication;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;

public class APIConfig {

    public static final String WS_URL = "http://sit.sikefeng.com/xxws";
//    public static final String BASE_URL_DEFAULT = "http://120.78.79.198:8080/AIWeb/api/";  //SIT服务器
//    public static final String BASE_URL_DEFAULT = "http://192.168.0.104:8981/AIWeb/api/";  //本地服务器
    public static  String BASE_URL_DEFAULT = (String) SharePreferenceUtils.get(MyApplication.getContext(),"URL","http://120.78.79.198:8080")+"/AIWeb/api/";
    public static final String BASE_URL_WEBVIEW = "http://192.168.0.104:8981/AIWeb/";  //本地服务器


}
