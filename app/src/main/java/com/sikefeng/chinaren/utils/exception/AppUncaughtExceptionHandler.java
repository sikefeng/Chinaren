package com.sikefeng.chinaren.utils.exception;

import android.os.Build;

import com.sikefeng.chinaren.utils.DavikActivityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.sikefeng.chinaren.api.APIConfig.BASE_URL_DEFAULT;

public class AppUncaughtExceptionHandler implements UncaughtExceptionHandler {

    public final static String LINE_END = "\r\n";

    private static AppUncaughtExceptionHandler mInstance;
    private static UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private AppUncaughtExceptionHandler() {
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public static AppUncaughtExceptionHandler getInstance() {
        if (null == mInstance) {
            synchronized (AppUncaughtExceptionHandler.class) {
                if (null == mInstance) {
                    mInstance = new AppUncaughtExceptionHandler();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String name = thread.getName();
        String errorLog = obtainErrorLog(ex);
//        System.out.println(name + "=kkkkkkkkkkkkkkkkkkkkkk=" + errorLog);
        sendError(errorLog);
        mDefaultUncaughtExceptionHandler.uncaughtException(thread, ex);
    }

    public static String obtainErrorLog(Throwable ex) {
        StackTraceElement[] arr = ex.getStackTrace();
        final StringBuffer sbLog = new StringBuffer(ex.toString());
        final String lineSeperator = "-------------------------------\n\n";
        sbLog.append(LINE_END + LINE_END);
        sbLog.append("--------- Stack trace ---------\n\n");
        for (int i = 0; i < arr.length; i++) {
            sbLog.append("    ");
            sbLog.append(arr[i].toString());
            sbLog.append(LINE_END);
        }

        Throwable cause = ex.getCause();
        if (cause != null) {
            sbLog.append(lineSeperator);
            sbLog.append("--------- Cause ---------\n\n");
            sbLog.append(cause.toString());
            sbLog.append(LINE_END + LINE_END);
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                sbLog.append("    ");
                sbLog.append(arr[i].toString());
                sbLog.append(LINE_END);
            }
        }
        sbLog.append(lineSeperator);
        sbLog.append("--------- Device ---------\n\n");
        sbLog.append("Brand: ");
        sbLog.append(Build.BRAND);
        sbLog.append(LINE_END);
        sbLog.append("Device: ");
        sbLog.append(Build.DEVICE);
        sbLog.append(LINE_END);
        sbLog.append("Model: ");
        sbLog.append(Build.MODEL);
        sbLog.append(LINE_END);
        sbLog.append("Id: ");
        sbLog.append(Build.ID);
        sbLog.append(LINE_END);
        sbLog.append("Product: ");
        sbLog.append(Build.PRODUCT);
        sbLog.append(LINE_END);
        sbLog.append(lineSeperator);
        sbLog.append("--------- Firmware ---------\n\n");
        sbLog.append("SDK: ");
        sbLog.append(Build.VERSION.SDK);
        sbLog.append(LINE_END);
        sbLog.append("Release: ");
        sbLog.append(Build.VERSION.RELEASE);
        sbLog.append(LINE_END);
        sbLog.append("Incremental: ");
        sbLog.append(Build.VERSION.INCREMENTAL);
        sbLog.append(LINE_END);
        sbLog.append(lineSeperator);
        sbLog.append("--------- Activity Stack Info ---------\n\n");
        for (int i = 0; i < DavikActivityUtils.getScreenManager().getAllActivity().size(); i++) {
            sbLog.append(i + "-" + DavikActivityUtils.getScreenManager().getAllActivity().get(i).getClass().getSimpleName());
            sbLog.append(LINE_END);
        }
        sbLog.append("--------- App Info ---------\n\n");
        sbLog.append("Version code:");
        sbLog.append(VersionUtil.getVersionCode());
        sbLog.append(LINE_END);
        sbLog.append("Version name:");
        sbLog.append(VersionUtil.getVersionName());
        return sbLog.toString();
    }

    private void sendError(String content) {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(BASE_URL_DEFAULT + "crash/crash_save?content="+content);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    BufferedReader bufr = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line = null;
                    while ((line = bufr.readLine()) != null) {
                        response.append(line);
                    }
//					Message message=new Message();
//					message.what=SHOW_RESPONSE;
//					//将服务器返回的数据存放到Message中
//					message.obj=response.toString();
//					handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


}
