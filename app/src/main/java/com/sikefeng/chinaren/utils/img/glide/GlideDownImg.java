/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils.img.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.sikefeng.chinaren.XXApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GlideDownImg {
    /**
     * 进度值
     */
    private static final int PROGRESS = 100;
    /**
     * 下载线程服务
     */
    private static ExecutorService singleExecutor = null;

    public static void setSingleExecutor(ExecutorService singleExecutor) {
        GlideDownImg.singleExecutor = singleExecutor;
    }

    /**
     * 下载结果
     */
    private DownCallBack callBack;

    /**
     * 设置下载结果回调
     *
     * @param callBack 回调
     */
    public void setCallBack(DownCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 下载
     *
     * @param context 上下文环境
     * @param url     图片地址
     * @param dir     下载的目录
     * @param picName 图片名称
     */
    public void downLoad(final Context context, final String url, final String dir, final String picName) {
        if (singleExecutor == null) {
            setSingleExecutor(Executors.newSingleThreadExecutor());
        }
        singleExecutor.submit(new Runnable() {
            @Override
            public void run() {
                File file = null;
                try {
                    file = Glide.with(XXApplication.getContext())
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (file != null) {
                        savePhotoToSDCard(file.getPath(), dir, picName);
                    } else {
                        onFailed();
                    }
                }
            }
        });
    }

    /**
     * 保存图片到SD卡
     *
     * @param path    下载路径
     * @param dir     保存的目录
     * @param picName 图片名称
     */
    private void savePhotoToSDCard(String path, String dir, String picName) {
        FileOutputStream fOut = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                onFailed();
                return;
            }
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);
            opts.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = PROGRESS;
            bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);
            File picDir = new File(dir);
            if (!picDir.exists()) {
                boolean mkResult = picDir.mkdir();
                if(!mkResult){
                    return;
                }
            }
            if (!picName.contains(".jpg") && !picName.contains(".png")) {
                picName += ".jpg";
            }
            File file2 = new File(picDir, picName);
            fOut = new FileOutputStream(file2);
            fOut.write(baos.toByteArray());
            fOut.flush();
            fOut.close();
            baos.flush();
            baos.close();
            bitmap.recycle();
            bitmap = null;

            if (file2.length() == 0) {
                onFailed();
                return;
            }
            if (null != callBack) {
                callBack.onSuccess(file2);
            }
        } catch (IOException e) {
            e.printStackTrace();
            onFailed();
        }finally {
            if(fOut != null){
                try {
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载失败回调处理
     */
    private void onFailed() {
        if (null != callBack) {
            callBack.onFailed("下载图片失败");
        }
    }

}
