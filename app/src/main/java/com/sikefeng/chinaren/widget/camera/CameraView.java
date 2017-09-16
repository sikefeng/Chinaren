package com.sikefeng.chinaren.widget.camera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.sikefeng.chinaren.ui.activity.CameraActivity;
import com.sikefeng.chinaren.utils.camera.CameraManager;
import com.sikefeng.chinaren.utils.camera.ICameraOperation;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.XXApplication;
import com.sikefeng.chinaren.utils.ScreenUtils;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.utils.camera.IActivityLifiCycle;
import com.sikefeng.chinaren.utils.camera.SensorControler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class CameraView extends SurfaceView implements SurfaceHolder.Callback, ICameraOperation, IActivityLifiCycle {
    public static final String TAG = "CameraView";

    /**
     * 360
     */
    private static final int DP_360 = 360;

    /**
     * 300
     */
    private static final int VALUE_300 = 300;
    /**
     * 100
     */
    private static final int VALUE_100 = 100;
    /**
     * 1000
     */
    private static final int VALUE_1000 = 1000;

    /**
     * 40的值
     */
    private static final int VALUE_40 = 40;

    /**
     * 0后置  1前置
     */
    private CameraManager.CameraDirection mCameraId;
    /**
     * Camera
     */
    private Camera mCamera;
    /**
     * Camera.Parameters
     */
    private Camera.Parameters parameters = null;
    /**
     * CameraManager
     */
    private CameraManager mCameraManager;
    /**
     * Context
     */
    private Context mContext;
    /**
     * SensorControler
     */
    private SensorControler mSensorControler;
    /**
     * SwitchCameraCallBack
     */
    private SwitchCameraCallBack mSwitchCameraCallBack;
    /**
     * mDisplayOrientation
     */
    private int mDisplayOrientation;
    /**
     * mLayoutOrientation
     */
    private int mLayoutOrientation;
    /**
     * CameraOrientationListener
     */
    private CameraOrientationListener mOrientationListener;
    /**
     * 当前缩放
     */
    private int mZoom;
    /**
     * 当前屏幕旋转角度
     */
    private int mOrientation = 0;
    /**
     * mRotation
     */
    private int mRotation;
    /**
     * OnCameraPrepareListener
     */
    private OnCameraPrepareListener onCameraPrepareListener;
    /**
     * Camera.PictureCallback
     */
    private Camera.PictureCallback callback;
    /**
     * CameraActivity
     */
    private CameraActivity mActivity;

    /**
     * 构造函数
     *
     * @param context Context
     */
    public CameraView(Context context) {
        this(context, null);
    }

    /**
     * 构造函数
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public CameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造函数
     *
     * @param context  Context
     * @param attrs    AttributeSet
     * @param defStyle int
     */
    public CameraView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;
        mCameraManager = CameraManager.getInstance(context);
        mCameraId = mCameraManager.getCameraDirection();

        setFocusable(true);
        getHolder().addCallback(this);//为SurfaceView的句柄添加一个回调函数

        mSensorControler = SensorControler.getInstance();
        mOrientationListener = new CameraOrientationListener(mContext);
        mOrientationListener.enable();
    }

    /**
     * 绑定Activity
     *
     * @param activity Activity
     */
    public void bindActivity(CameraActivity activity) {
        this.mActivity = activity;
    }

    public void setOnCameraPrepareListener(OnCameraPrepareListener onCameraPrepareListener) {
        this.onCameraPrepareListener = onCameraPrepareListener;
    }

    public void setPictureCallback(Camera.PictureCallback callback) {
        this.callback = callback;
    }

    public void setSwitchCameraCallBack(SwitchCameraCallBack mSwitchCameraCallBack) {
        this.mSwitchCameraCallBack = mSwitchCameraCallBack;
    }

    /**
     * 调整SurfaceView的宽高
     *
     * @param adapterSize 适配器尺寸
     */
    private void adjustView(Camera.Size adapterSize) {
        int width = ScreenUtils.getScreenWidth(XXApplication.getContext());
        int height = width * adapterSize.width / adapterSize.height;

        //让surfaceView的中心和FrameLayout的中心对齐
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.topMargin = -(height - width) / 2;
        params.width = width;
        params.height = height;
        setLayoutParams(params);
    }

    /**
     * 初始化相机
     */
    private void initCamera() {
        parameters = mCamera.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);

        List<String> focusModes = parameters.getSupportedFocusModes();

        //设置对焦模式
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        try {
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //修正surfaceView的宽高
        mCameraManager.setFitPreSize(mCamera);

        Camera.Size adapterSize = mCamera.getParameters().getPreviewSize();

        //设置图片的宽高比预览的一样
        mCameraManager.setFitPicSize(mCamera, (float) adapterSize.width / adapterSize.height);
        Log.i(TAG, "adpterSize Preview-->width:" + adapterSize.width + "  height:" + adapterSize.height);


        adapterSize = mCamera.getParameters().getPictureSize();
        Log.i(TAG, "adpterSize Picture-->width:" + adapterSize.width + "  height:" + adapterSize.height);

        //调整控件的布局  防止预览被拉伸
        adjustView(adapterSize);

        determineDisplayOrientation();

        mCamera.startPreview();
        turnLight(mCameraManager.getLightStatus());  //设置闪光灯
        mCameraManager.setActivityCamera(mCamera);
    }

    /**
     * 开始预览
     */
    public void startPreview() {
        if (mCamera != null) {
            try {
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止预览
     */
    public void stopPreview() {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放相机
     */
    public void releaseCamera() {
        mCameraManager.releaseCamera(mCamera);
        mCamera = null;
    }


    /**
     * 切换摄像头
     */
    @Override
    public void switchCamera() {
        mCameraId = mCameraId.next();
        releaseCamera();

        setUpCamera(mCameraId, mCameraId == CameraManager.CameraDirection.CAMERA_BACK);
    }

    @Override
    public void switchFlashMode() {
        turnLight(mCameraManager.getLightStatus().next());
    }

    public boolean isBackCamera() {
        return mCameraId == CameraManager.CameraDirection.CAMERA_BACK;
    }

    @Override
    public boolean takePicture() {
        try {
            mSensorControler.lockFocus();
            mCamera.takePicture(null, null, callback);
            mOrientationListener.rememberOrientation();


        } catch (Throwable t) {
            t.printStackTrace();
            Log.e(TAG, "photo fail after Photo Clicked");

            ToastUtils.showShort(R.string.topic_camera_takephoto_failure);

            try {
                mCamera.startPreview();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            return false;
        }

//        try {
//            mCamera.startPreview();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }

        return true;
    }

    @Override
    public int getMaxZoom() {
        if (mCamera == null){ return -1;}
        Camera.Parameters parameters = mCamera.getParameters();
        if (!parameters.isZoomSupported()){ return -1;}
        return parameters.getMaxZoom() > VALUE_40 ? VALUE_40 : parameters.getMaxZoom();
    }

    @Override
    public void setZoom(int zoom) {
        if (mCamera == null){ return;}
        Camera.Parameters parameters;
        //注意此处为录像模式下的setZoom方式。在Camera.unlock之后，调用getParameters方法会引起android框架底层的异常
        //stackoverflow上看到的解释是由于多线程同时访问Camera导致的冲突，所以在此使用录像前保存的mParameters。
        parameters = mCamera.getParameters();

        if (!parameters.isZoomSupported()) {
            return;
        }
        parameters.setZoom(zoom);
        mCamera.setParameters(parameters);
        mZoom = zoom;
    }

    @Override
    public int getZoom() {
        return mZoom;
    }


    /**
     * 闪光灯开关   开->关->自动
     *
     * @param ligthStatus 灯光状态
     */
    private void turnLight(CameraManager.FlashLigthStatus ligthStatus) {
        if (CameraManager.getmFlashLightNotSupport().contains(ligthStatus)) {
            turnLight(ligthStatus.next());
            return;
        }

        if (mCamera == null || mCamera.getParameters() == null
                || mCamera.getParameters().getSupportedFlashModes() == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> supportedModes = mCamera.getParameters().getSupportedFlashModes();

        switch (ligthStatus) {
            case LIGHT_AUTO:
                if (supportedModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                }
                break;
            case LIGTH_OFF:
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                break;
            case LIGHT_ON:
                if (supportedModes.contains(Camera.Parameters.FLASH_MODE_ON)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                } else if (supportedModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                } else if (supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                }
                break;
            default:break;
        }
        mCamera.setParameters(parameters);
        mCameraManager.setLightStatus(ligthStatus);
    }

    public int getPicRotation() {
        return (mDisplayOrientation
                + mOrientationListener.getRememberedNormalOrientation()
                + mLayoutOrientation
        ) % DP_360;
    }

    /**
     * 设置当前的Camera 并进行参数设置
     *
     * @param mCameraId         相机ID
     * @param isSwitchFromFront 是否切换前台
     */
    private void setUpCamera(CameraManager.CameraDirection mCameraId, boolean isSwitchFromFront) {
        int facing = mCameraId.ordinal();
        try {
            mCamera = mCameraManager.openCameraFacing(facing);
            //重置对焦计数
            mSensorControler.restFoucs();
        } catch (Exception e) {
            ToastUtils.showLong(R.string.tips_camera_forbidden);
            e.printStackTrace();
        }
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(getHolder());
                initCamera();

                mCameraManager.setCameraDirection(mCameraId);

                if (mCameraId == CameraManager.CameraDirection.CAMERA_FRONT) {
                    mSensorControler.lockFocus();
                } else {
                    mSensorControler.unlockFocus();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showLong("切换失败，请重试！");
        }

        if (mSwitchCameraCallBack != null) {
            mSwitchCameraCallBack.switchCamera(isSwitchFromFront);
        }
    }

    /**
     * 手动聚焦
     *
     * @param point    触屏坐标
     * @param callback 回调
     * @return boolean
     */
    protected boolean onFocus(Point point, Camera.AutoFocusCallback callback) {
        if (mCamera == null) {
            return false;
        }

        Camera.Parameters parameters = null;
        try {
            parameters = mCamera.getParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //不支持设置自定义聚焦，则使用自动聚焦，返回

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            if (parameters.getMaxNumFocusAreas() <= 0) {
                return focus(callback);
            }

            Log.i(TAG, "onCameraFocus:" + point.x + "," + point.y);

            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            int left = point.x - VALUE_300;
            int top = point.y - VALUE_300;
            int right = point.x + VALUE_300;
            int bottom = point.y + VALUE_300;
            left = left < -VALUE_1000 ? -VALUE_1000 : left;
            top = top < -VALUE_1000 ? -VALUE_1000 : top;
            right = right > VALUE_1000 ? VALUE_1000 : right;
            bottom = bottom > VALUE_1000 ? VALUE_1000 : bottom;
            areas.add(new Camera.Area(new Rect(left, top, right, bottom), VALUE_100));
            parameters.setFocusAreas(areas);
            try {
                //本人使用的小米手机在设置聚焦区域的时候经常会出异常，看日志发现是框架层的字符串转int的时候出错了，
                //目测是小米修改了框架层代码导致，在此try掉，对实际聚焦效果没影响
                mCamera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


        return focus(callback);
    }

    /**
     * 焦点
     * @param callback 回调
     * @return boolean
     */
    private boolean focus(Camera.AutoFocusCallback callback) {
        try {
            mCamera.autoFocus(callback);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated");

        mCameraManager.releaseStartTakePhotoCamera();

        if (null == mCamera) {
            //打开默认的摄像头
            setUpCamera(mCameraId, false);
            if (onCameraPrepareListener != null) {
                onCameraPrepareListener.onPrepare(mCameraId);
            }
            if (mCamera != null) {
                startOrientationChangeListener();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            releaseCamera();

            //释放资源
            if (holder != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    holder.getSurface().release();
                }
            }
        } catch (Exception e) {
            //相机已经关了
            e.printStackTrace();
        }
    }

    /**
     * 90的值
     */
    private static final int VALUE_90 = 90;
    /**
     * 180的值
     */
    private static final int VALUE_180 = 180;
    /**
     * 270的值
     */
    private static final int VALUE_270 = 270;
    /**
     * 360的值
     */
    private static final int VALUE_360 = 360;
    /**
     * 45的值
     */
    private static final int VALUE_45 = 45;
    /**
     * 315的值
     */
    private static final int VALUE_315 = 315;
    /**
     * 135的值
     */
    private static final int VALUE_135 = 135;
    /**
     * 225的值
     */
    private static final int VALUE_225 = 225;
    /**
     * Determine the current display orientation and rotate the camera preview
     * accordingly
     */
    private void determineDisplayOrientation() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId.ordinal(), cameraInfo);

        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: {
                degrees = 0;
                break;
            }
            case Surface.ROTATION_90: {
                degrees = VALUE_90;
                break;
            }
            case Surface.ROTATION_180: {
                degrees = VALUE_180;
                break;
            }
            case Surface.ROTATION_270: {
                degrees = VALUE_270;
                break;
            }
            default:break;
        }

        int displayOrientation;

        // Camera direction
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // Orientation is angle of rotation when facing the camera for
            // the camera image to match the natural orientation of the device
            displayOrientation = (cameraInfo.orientation + degrees) % VALUE_360;
            displayOrientation = (VALUE_360 - displayOrientation) % VALUE_360;
        } else {
            displayOrientation = (cameraInfo.orientation - degrees + VALUE_360) % VALUE_360;
        }

        mDisplayOrientation = (cameraInfo.orientation - degrees + VALUE_360) % VALUE_360;
        mLayoutOrientation = degrees;

        mCamera.setDisplayOrientation(displayOrientation);

        Log.i(TAG, "displayOrientation:" + displayOrientation);
    }

    /**
     * 启动屏幕朝向改变监听函数 用于在屏幕横竖屏切换时改变保存的图片的方向
     */
    private void startOrientationChangeListener() {
        OrientationEventListener mOrEventListener = new OrientationEventListener(getContext()) {
            @Override
            public void onOrientationChanged(int rotation) {

                if (((rotation >= 0) && (rotation <= VALUE_45)) || (rotation > VALUE_315)) {
                    rotation = 0;
                } else if ((rotation > VALUE_45) && (rotation <= VALUE_135)) {
                    rotation = VALUE_90;
                } else if ((rotation > VALUE_135) && (rotation <= VALUE_225)) {
                    rotation = VALUE_180;
                } else if ((rotation > VALUE_225) && (rotation <= VALUE_315)) {
                    rotation = VALUE_270;
                } else {
                    rotation = 0;
                }
                if (rotation == mOrientation) {
                    return;
                }
                mOrientation = rotation;

            }
        };
        mOrEventListener.enable();
    }


    /**
     * When orientation changes, onOrientationChanged(int) of the listener will be called
     */
    private class CameraOrientationListener extends OrientationEventListener {
        /**
         * mCurrentNormalizedOrientation
         */
        private int mCurrentNormalizedOrientation;
        /**
         * mRememberedNormalOrientation
         */
        private int mRememberedNormalOrientation;

        /**
         * CameraOrientationListener
         * @param context Context
         */
        CameraOrientationListener(Context context) {
            super(context, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation != ORIENTATION_UNKNOWN) {
                mCurrentNormalizedOrientation = normalize(orientation);
            }
        }

        /**
         * normalize
         * @param degrees int
         * @return int
         */
        private int normalize(int degrees) {
            if (degrees > VALUE_315 || degrees <= VALUE_45) {
                return 0;
            }

            if (degrees > VALUE_45 && degrees <= VALUE_135) {
                return VALUE_90;
            }

            if (degrees > VALUE_135 && degrees <= VALUE_225) {
                return VALUE_180;
            }

            if (degrees > VALUE_225 && degrees <= VALUE_315) {
                return VALUE_270;
            }

            throw new RuntimeException("The physics as we know them are no more. Watch out for anomalies.");
        }

        /**
         * rememberOrientation
         */
        public void rememberOrientation() {
            mRememberedNormalOrientation = mCurrentNormalizedOrientation;
        }

        public int getRememberedNormalOrientation() {
            return mRememberedNormalOrientation;
        }
    }

    @Override
    public void onStart() {
        mOrientationListener.enable();
    }

    @Override
    public void onStop() {
        mOrientationListener.disable();
    }

    /**
     * OnCameraPrepareListener
     */
    public interface OnCameraPrepareListener {
        /**
         * onPrepare
         * @param cameraDirection CameraDirection
         */
        void onPrepare(CameraManager.CameraDirection cameraDirection);
    }

    /**
     * SwitchCameraCallBack
     */
    public interface SwitchCameraCallBack {
        /**
         * switchCamera
         * @param isSwitchFromFront boolean
         */
        void switchCamera(boolean isSwitchFromFront);
    }
}
