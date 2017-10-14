/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sikefeng.chinaren.widget.zxing.activity;


import android.Manifest;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.widget.zxing.camera.CameraManager;
import com.sikefeng.chinaren.widget.zxing.decode.DecodeThread;
import com.sikefeng.chinaren.widget.zxing.utils.BeepManager;
import com.sikefeng.chinaren.widget.zxing.utils.CaptureActivityHandler;
import com.sikefeng.chinaren.widget.zxing.utils.InactivityTimer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public final class CaptureActivity extends AppCompatActivity implements
        SurfaceHolder.Callback, EasyPermissions.PermissionCallbacks ,View.OnClickListener{

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView mFlash;

    private Rect mCropRect = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_qr_scan);

        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        // Install the callback and wait for surfaceCreated() to init the
        // camera.
        scanPreview.getHolder().addCallback(this);

        cameraTask();
    }

    @Override
    protected void onResume() {
        if (scanPreview != null) {
            handler = null;
            if (isHasSurface) {
                // The activity was paused but not stopped, so the surface still
                // exists. Therefore
                // surfaceCreated() won't be called, so init the camera here.
                initCamera(scanPreview.getHolder());
            }
        }
        if (inactivityTimer != null) {
            inactivityTimer.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        if (inactivityTimer != null) {
            inactivityTimer.onPause();
        }
        if (beepManager != null) {
            beepManager.close();
        }
        if (cameraManager != null) {
            cameraManager.closeDriver();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (inactivityTimer != null) {
            inactivityTimer.shutdown();
        }
        if (scanPreview != null) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onDestroy();
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private boolean isHasSurface = false;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        isHasSurface = true;
        initCamera(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // Doing
    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(final Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                handleText(rawResult.getText());
            }
        }, 800);
    }

    private void handleText(String text) {
        ToastUtils.showShort(text);
    }


    private void initCamera(SurfaceHolder surfaceHolder) {
        if (cameraManager == null)
            return;

        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager,
                        DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException | RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        Toast.makeText(this, R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
    }



    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_flash:
                light();
                break;
            default:
                break;
        }
    }

    private boolean mIsLight;

    private void light() {
        try {
            if (mIsLight) {
                // 关闪光灯
                cameraManager.offLight();
                mFlash.setBackgroundResource(R.mipmap.light_normal);
                mIsLight = false;
            } else {
                // 开闪光灯
                cameraManager.openLight();
                mFlash.setBackgroundResource(R.mipmap.light_selected);
                mIsLight = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCamera() {
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        mFlash = (ImageView) findViewById(R.id.capture_flash);
        mFlash.setOnClickListener(this);

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);


        cameraManager = new CameraManager(getApplication());
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms != null && perms.size() == 2) {
            initCamera();
        } else {
            displayFrameworkBugMessageAndExit();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        displayFrameworkBugMessageAndExit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private static final int CAMERA_PERM = 1;

    @AfterPermissionGranted(CAMERA_PERM)
    private void cameraTask() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.VIBRATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            initCamera();
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this,
                    getResources().getString(R.string.str_request_camera_message),
                    CAMERA_PERM, perms);
        }
    }
}

