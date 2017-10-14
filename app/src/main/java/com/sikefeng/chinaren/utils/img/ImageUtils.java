/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils.img;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sikefeng.chinaren.utils.img.glide.DownCallBack;


public final class ImageUtils implements ImgLoadStrategy {

    /**
     * 图片加载策略
     */
    private ImgLoadStrategy imageLoader;

    /**
     * 构造函数
     */
    private ImageUtils() {
    }

    /**
     * 单根方法
     */
    private static class SingletonHolder {
        /**
         * 实现单根方法
         */
        private static final ImageUtils INSTANCE = new ImageUtils();
    }

    /**
     * 单根实例
     *
     * @return ImageUtils
     */
    public static ImageUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 初始化
     *
     * @param imageLoader ImgLoadStrategy
     */
    public void init(ImgLoadStrategy imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull View view) {
        if (null != imageLoader) {
            imageLoader.disPlay(model, view);
        }
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull View view, @NonNull Drawable placeholder) {
        if (null != imageLoader) {
            imageLoader.disPlay(model, view, placeholder);
        }
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull View view, @DrawableRes int placeholder) {
        if (null != imageLoader) {
            imageLoader.disPlay(model, view, placeholder);
        }
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull View view, @NonNull Drawable placeholder, @NonNull Drawable error) {
        if (null != imageLoader) {
            imageLoader.disPlay(model, view, placeholder, error);
        }
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull View view, @DrawableRes int placeholder, @DrawableRes int error) {
        if (null != imageLoader) {
            imageLoader.disPlay(model, view, placeholder, error);
        }
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull View view, @NonNull Drawable placeholder, @DrawableRes int error) {
        if (null != imageLoader) {
            imageLoader.disPlay(model, view, placeholder, error);
        }
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull View view, @DrawableRes int placeholder, @NonNull Drawable error) {
        if (null != imageLoader) {
            imageLoader.disPlay(model, view, placeholder, error);
        }
    }

    @Override
    public void downLoadPic(Context context, String url, String dir, String picName, DownCallBack callBack) {
        if (null != imageLoader) {
            imageLoader.downLoadPic(context, url, dir, picName, callBack);
        }
    }

    @Override
    public void clearMemory(Context context) {
        if (null != imageLoader) {
            imageLoader.clearMemory(context);
        }
    }

    @Override
    public void cancelAllTasks(Context context) {
        if (null != imageLoader) {
            imageLoader.cancelAllTasks(context);
        }
    }

    @Override
    public void resumeAllTasks(Context context) {
        if (null != imageLoader) {
            imageLoader.resumeAllTasks(context);
        }
    }

    @Override
    public void clearDiskCache(Context context) {
        if (null != imageLoader) {
            imageLoader.clearDiskCache(context);
        }
    }

    @Override
    public void cleanAll(Context context) {
        if (null != imageLoader) {
            imageLoader.cleanAll(context);
        }
    }



}
