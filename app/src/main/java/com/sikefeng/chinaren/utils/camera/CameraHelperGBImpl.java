package com.sikefeng.chinaren.utils.camera;

import android.annotation.TargetApi;
import android.hardware.Camera;


@TargetApi(9)
public class CameraHelperGBImpl implements ICameraHelper {
    @Override
    public int getNumberOfCameras() {
        return Camera.getNumberOfCameras();
    }

    @Override
    public Camera openCameraFacing(int facing) {
        return Camera.open(getCameraId(facing));
    }

    @Override
    public boolean hasCamera(int facing) {
        return getCameraId(facing) != -1;
    }

    @Override
    public void getCameraInfo(int cameraId, Camera.CameraInfo cameraInfo) {
        Camera.getCameraInfo(cameraId, cameraInfo);
    }

    /**
     * 获取cameraId
     *
     * @param facing facing
     * @return int
     */
    private int getCameraId(final int facing) {
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int id = 0; id < numberOfCameras; id++) {
            Camera.getCameraInfo(id, info);
            if (info.facing == facing) {
                return id;
            }
        }
        return -1;
    }
}