package com.sikefeng.chinaren.utils.camera;



public interface ICameraOperation {
    /**
     * 切换前置和后置相机
     */
    void switchCamera();

    /**
     * 切换闪光灯模式
     */
    void switchFlashMode();

    /**
     * 拍照
     * @return boolean
     */
    boolean takePicture();

    /**
     * 相机最大缩放级别
     *
     * @return int
     */
    int getMaxZoom();

    /**
     * 设置当前缩放级别
     *
     * @param zoom 缩放大小
     */
    void setZoom(int zoom);

    /**
     * 获取当前缩放级别
     *
     * @return int
     */
    int getZoom();

    /**
     * 释放相机
     */
    void releaseCamera();
}
