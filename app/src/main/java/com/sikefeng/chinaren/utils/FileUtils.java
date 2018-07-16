/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.sikefeng.chinaren.MyApplication;
import com.sikefeng.chinaren.R;

import java.io.File;


public class FileUtils {

    public static final String FOLDER = "XxCamera";

    /**
     * 检查设备是否存在SDCard的工具方法
     *
     * @return true有存储的SD卡，false没有
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    /**
     * 获取当前项目要保存图片的路径
     *
     * @return 返回当前项目的图片路径
     */
    public static String getDownLoadImgPath() {
        if (!hasSdcard()) {
            return null;
        } else {
            String cacheImgPath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + MyApplication.getContext().getPackageName()
                    + File.separator + MyApplication.getContext().getString(R.string.app_name) + "_Pic";
            File file = new File(cacheImgPath);
            if (!file.exists()) {
                boolean mkresult = file.mkdirs();// 创建文件夹
                if(!mkresult){
                    return null;
                }
            }
            return cacheImgPath;
        }
    }


    /***
     * 根据路径 删除文件(夹)
     * @param file 文件
     * @return true为删除成功
     */
    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                return file.delete();
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    return file.delete();
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                return file.delete();
            }
            return true;
        }
    }

    /**
     * 刷新图库等文件系统  用来保存图片后调用
     *
     * @param path 图片路径
     */
    public static void notifyFileSystemChanged(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        try {
            File file = new File(path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //添加此判断，判断SDK版本是不是4.4或者高于4.4
                String[] paths = new String[]{path};
                MediaScannerConnection.scanFile(MyApplication.getContext(), paths, null, null);
            } else {
                Intent intent;
                if (file.isDirectory()) {
                    intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
                    intent.setClassName("com.android.providers.media", "com.android.providers.media.MediaScannerReceiver");
                    intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
                    //Log.v(LOG_TAG, "directory changed, send broadcast:" + intent.toString());
                } else {
                    intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(file));
                    //Log.v(LOG_TAG, "file changed, send broadcast:" + intent.toString());
                }
                MyApplication.getContext().sendBroadcast(intent);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 初始化目录
     */
    public static void initFolder() {
        File imgFile = new File(getCameraImageFolder());
        if (!imgFile.exists() || imgFile.isFile()) {
            imgFile.mkdirs();
        }
    }

    public static String getAppFoler() {
        return Environment.getExternalStorageDirectory()
                + "/" + FOLDER;
    }

    /**
     * 获取拍照照片保存的父目录
     *
     * @return String
     */
    public static String getCameraImageFolder() {
        return getAppFoler() + "/" + "cameraImg";
    }

    /**
     * 根据时间获取生成的相机照片路径
     *
     * @return String
     */
    public static String getCameraImgPath() {
        return getCameraImageFolder() + "/" + System.currentTimeMillis() + ".jpg";
    }

    /**
     * 检测SD卡是否存在
     * @return boolean
     */
    public static boolean checkSDcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore
                    .Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}

