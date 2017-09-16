/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils.img.glide;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.utils.img.ImgLoadStrategy;


public class GlideLoadStrategy implements ImgLoadStrategy {

    /**
     * 缩略图尺寸
     */
    private float thumbnail = 0.1f;
    /**
     * 下载图片的类
     */
    private GlideDownImg downImg;

    @Override
    public void disPlay(@Nullable Object model, @NonNull final View view) {
        disPlay(model, view, R.mipmap.img_placeholder, R.mipmap.img_placeholder);
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull final View view, @NonNull Drawable placeholder) {
        disPlay(model, view, placeholder, R.mipmap.img_placeholder);
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull final View view, @DrawableRes int placeholder) {
        disPlay(model, view, placeholder, R.mipmap.img_placeholder);
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull final View view, @NonNull Drawable placeholder, @NonNull Drawable error) {
        try {
            Context context = view.getContext();
            if (view instanceof ImageView) {
                Glide.with(context)
                        .load(model)
                        .placeholder(placeholder)
                        .error(error)
                        .thumbnail(thumbnail)
                        .into((ImageView) view);
            } else {
                setBgDrawable(view, placeholder);
                Glide.with(context).load(model).asBitmap().error(error).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap loadedImage, GlideAnimation<? super Bitmap> arg1) {
                        setBgBitmap(view, loadedImage);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        setBgDrawable(view, errorDrawable);
                    }

                });
            }
        }catch (Exception e){}
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull final View view, @DrawableRes int placeholder, @DrawableRes int error) {
        try {
            Context context = view.getContext();
            if (view instanceof ImageView) {
                Glide.with(context)
                        .load(model)
                        .placeholder(placeholder)
                        .error(error)
                        .thumbnail(thumbnail)
                        .into((ImageView) view);
            } else {
                setBgDrawable(view, placeholder);
                Glide.with(context).load(model).asBitmap().error(error).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap loadedImage, GlideAnimation<? super Bitmap> arg1) {
                        setBgBitmap(view, loadedImage);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        setBgDrawable(view, errorDrawable);
                    }

                });
            }
        }catch (Exception e){}
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull final View view, @NonNull Drawable placeholder, @DrawableRes int error) {
        try {
            Context context = view.getContext();
            if (view instanceof ImageView) {
                Glide.with(context)
                        .load(model)
                        .placeholder(placeholder)
                        .error(error)
                        .thumbnail(thumbnail)
                        .into((ImageView) view);
            } else {
                setBgDrawable(view, placeholder);
                Glide.with(context).load(model).asBitmap().error(error).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap loadedImage, GlideAnimation<? super Bitmap> arg1) {
                        setBgBitmap(view, loadedImage);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        setBgDrawable(view, errorDrawable);
                    }

                });
            }
        }catch (Exception e){}
    }

    @Override
    public void disPlay(@Nullable Object model, @NonNull final View view, @DrawableRes int placeholder, @NonNull Drawable error) {
        try {
            Context context = view.getContext();
            if (view instanceof ImageView) {
                Glide.with(context)
                        .load(model)
                        .placeholder(placeholder)
                        .error(error)
                        .thumbnail(thumbnail)
                        .into((ImageView) view);
            } else {
                setBgDrawable(view, placeholder);
                Glide.with(context).load(model).asBitmap().error(error).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap loadedImage, GlideAnimation<? super Bitmap> arg1) {
                        setBgBitmap(view, loadedImage);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        setBgDrawable(view, errorDrawable);
                    }

                });
            }
        }catch (Exception e){}
    }

    @Override
    public void downLoadPic(Context context, String url, String dir, String picName, DownCallBack callBack) {
        if(null == downImg){
            downImg = new GlideDownImg();
            downImg.setCallBack(callBack);
        }
        downImg.downLoad(context, url, dir, picName);
    }


    @Override
    public void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    @Override
    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    @Override
    public void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }

    /**
     * 设置图片背景，当API版本为4的时候
     * @param view 当前view
     * @param bitmap 图片
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public void setBgBitmap(View view, Bitmap bitmap){
        BitmapDrawable bd = new BitmapDrawable(view.getContext().getResources(), bitmap);
        setBgDrawable(view, bd);
    }

    /**
     * 设置背景图片
     * @param view 当前view
     * @param drawableId 图片ID
     */
    public void setBgDrawable(View view, int drawableId){
        Drawable drawable = view.getContext().getResources().getDrawable(drawableId);
        setBgDrawable(view, drawable);
    }

    /**
     * 设置背景图片
     * @param view  当前view
     * @param drawable 图片
     */
    public void setBgDrawable(View view, Drawable drawable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        }else {
            view.setBackgroundDrawable(drawable);
        }
    }

}
