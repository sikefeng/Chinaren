package com.sikefeng.chinaren.utils.camera;

import android.content.pm.PackageManager;
import android.hardware.Camera;

import com.sikefeng.chinaren.XXApplication;


public class CameraHelperBaseImpl implements ICameraHelper {

    /**
     * 旋转角度
     */
    private static final int ORIENTATION = 90;

    @Override
    public int getNumberOfCameras() {
        return hasCameraSupport() ? 1 : 0;
    }

    @Override
    public Camera openCameraFacing(int facing) {
        if (facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            return Camera.open();
        }
        return null;
    }

    @Override
    public boolean hasCamera(int facing) {
        if (facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            return hasCameraSupport();
        }
        return false;
    }

    @Override
    public void getCameraInfo(int cameraId, Camera.CameraInfo cameraInfo) {
        cameraInfo.facing = Camera.CameraInfo.CAMERA_FACING_BACK;
        cameraInfo.orientation = ORIENTATION;
    }

    /**
     * 是否支持摄像头
     * @return boolean
     */
    private boolean hasCameraSupport() {
        return XXApplication.getInstance().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}
