package com.sikefeng.chinaren.utils.camera;

import android.hardware.Camera;


public interface ICameraHelper {

    /**
     * 获取相机
     * @return int
     */
    int getNumberOfCameras();

    /**
     * openCameraFacing
     * @param facing int
     * @return Camera
     * @throws Exception Exception
     */
    Camera openCameraFacing(int facing) throws Exception;

    /**
     * hasCamera
     * @param facing int
     * @return boolean
     */
    boolean hasCamera(int facing);

    /**
     * getCameraInfo
     * @param cameraId int
     * @param cameraInfo Camera.CameraInfo
     */
    void getCameraInfo(int cameraId, Camera.CameraInfo cameraInfo);
}
