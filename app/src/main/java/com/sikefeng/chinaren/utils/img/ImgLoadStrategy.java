package com.sikefeng.chinaren.utils.img;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sikefeng.chinaren.utils.img.glide.DownCallBack;



public interface ImgLoadStrategy {

    /**
     * 显示在默认的当前视图
     *
     * @param model 图片对象
     * @param view  界面视图
     */
    void disPlay(@Nullable Object model, @NonNull View view);

    /**
     * 显示在默认的当前视图
     *
     * @param model       图片对象
     * @param view        界面视图
     * @param placeholder 显示在列表的某个行
     */
    void disPlay(@Nullable Object model, @NonNull View view, @NonNull Drawable placeholder);

    /**
     * 显示在默认的当前视图
     *
     * @param model       图片对象
     * @param view        界面视图
     * @param placeholder 显示在列表的某个行
     */
    void disPlay(@Nullable Object model, @NonNull View view, @DrawableRes int placeholder);

    /**
     * 显示在默认的当前视图
     *
     * @param model       图片对象
     * @param view        界面视图
     * @param placeholder 显示在列表的某个行
     *                    @param error 加载出错图片
     */
    void disPlay(@Nullable Object model, @NonNull View view, @NonNull Drawable placeholder, @NonNull Drawable error);
    /**
     * 显示在默认的当前视图
     *
     * @param model       图片对象
     * @param view        界面视图
     * @param placeholder 显示在列表的某个行
     *                    @param error 加载出错图片
     */
    void disPlay(@Nullable Object model, @NonNull View view, @DrawableRes int placeholder, @DrawableRes int error);
    /**
     * 显示在默认的当前视图
     *
     * @param model       图片对象
     * @param view        界面视图
     * @param placeholder 显示在列表的某个行
     *                    @param error 加载出错图片
     */
    void disPlay(@Nullable Object model, @NonNull View view, @NonNull Drawable placeholder, @DrawableRes int error);
    /**
     * 显示在默认的当前视图
     *
     * @param model       图片对象
     * @param view        界面视图
     * @param placeholder 显示在列表的某个行
     *                    @param error 加载出错图片
     */
    void disPlay(@Nullable Object model, @NonNull View view, @DrawableRes int placeholder, @NonNull Drawable error);

    /**
     * 下载图片
     * @param context 上下文环境
     * @param url 图片地址
     * @param dir 保存的路径
     * @param picName 图片名称
     * @param callBack 下载结果回调
     */
    void downLoadPic(Context context, String url, String dir, String picName, DownCallBack callBack);

    /**
     * 清空缓存
     * @param context 所在的环境
     */
    void clearMemory(Context context);

    /**
     * 取消所有任务
     * @param context 所在的环境
     */
    void cancelAllTasks(Context context);

    /**
     * 继续所有任务
     * @param context 所在环境
     */
    void resumeAllTasks(Context context);

    /**
     * 清空磁盘缓存
     * @param context 所在环境
     */
    void clearDiskCache(Context context);

    /**
     * 清空所有，包括缓存和任务
     * @param context 所在环境
     */
    void cleanAll(Context context);

}
