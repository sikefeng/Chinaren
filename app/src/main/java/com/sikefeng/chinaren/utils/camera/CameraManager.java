package com.sikefeng.chinaren.utils.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.ui.activity.CameraActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;



public final class CameraManager implements ICameraHelper {
    public static final String TAG = "CameraManager";

    /**
     * 定义CameraManager
     */
    private static CameraManager mInstance;
    /**
     * 定义ICameraHelper
     */
    private final ICameraHelper mCameraHelper;
    /**
     * 定义Camera
     */
    private Camera mStartTakePhotoCamera;
    /**
     * 定义Camera
     */
    private Camera mActivityCamera;

    /**
     * 定义FlashLigthStatus
     */
    private FlashLigthStatus mLightStatus;
    /**
     * 定义CameraDirection
     */
    private CameraDirection mFlashDirection;
    /**
     * 定义Context
     */
    private Context mContext;
    /**
     * 定义TextView
     */
    private TextView tvFlashLight, tvCameraDireation;  //绑定的闪光灯和前后置镜头切换控件

    /**
     * 定义List
     */
    private static List<FlashLigthStatus> mFlashLightNotSupport = new ArrayList<FlashLigthStatus>();

    /**
     * setmFlashLightNotSupport
     *
     * @param mFlashLightNotSupport FlashLigthStatus集合
     */
    public static void setmFlashLightNotSupport(List<FlashLigthStatus> mFlashLightNotSupport) {
        CameraManager.mFlashLightNotSupport = mFlashLightNotSupport;
    }

    /**
     * getmFlashLightNotSupport
     *
     * @return List
     */
    public static List<FlashLigthStatus> getmFlashLightNotSupport() {
        return mFlashLightNotSupport;
    }

    public static final String SP_LIGHT_STATUE = "SP_LIGHT_STATUE";
    public static final String SP_CAMERA_DIRECTION = "SP_CAMERA_DIRECTION";

    public static final int[] RES_DRAWABLE_FLASHLIGHT = {R.drawable.selector_btn_flashlight_auto, R.drawable.selector_btn_flashlight_on, R.drawable.selector_btn_flashlight_off};
    public static final int[] RES_DRAWABLE_CAMERA_DIRECTION = {R.drawable.selector_btn_camera_back, R.drawable.selector_btn_camera_front};

    public static final int[] RES_STRING_FLASHLIGHT = {R.string.topic_camera_flashlight_auto, R.string.topic_camera_flashlight_on, R.string.topic_camera_flashlight_off};
    public static final int[] RES_STRING_CAMERA_DIRECTION = {R.string.topic_camera_back, R.string.topic_camera_front};

    public static final int LEN_PIC = 64;   //图片的边长   px

    public static final int TYPE_PREVIEW = 0;
    public static final int TYPE_PICTURE = 1;

    /**
     * 最大允许的照片尺寸的长度   宽或者高
     */
    public static final int ALLOW_PIC_LEN = 2000;

    /**
     * 文件深度
     */
    private static final int DEGREES = 90;

    /**
     * 版本14
     */
    private static final int VERSION_14 = 14;

    /**
     * 版本8
     */
    private static final int VERSION_8 = 8;

    /**
     * 屏蔽默认构造方法
     *
     * @param context Context
     */
    private CameraManager(Context context) {

        mContext = context;

        if (SDK_INT >= GINGERBREAD) {
            mCameraHelper = new CameraHelperGBImpl();
        } else {
            mCameraHelper = new CameraHelperBaseImpl();
        }

        mLightStatus = FlashLigthStatus.valueOf(SPConfigUtil.loadInt(SP_LIGHT_STATUE, FlashLigthStatus.LIGHT_AUTO.ordinal())); //默认 自动
        mFlashDirection = CameraDirection.valueOf(SPConfigUtil.loadInt(SP_CAMERA_DIRECTION, CameraDirection.CAMERA_BACK.ordinal())); //默认后置摄像头
    }

    /**
     * 获取CameraManager
     *
     * @param context Context
     * @return CameraManager
     */
    public static synchronized CameraManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CameraManager.class) {
                mInstance = new CameraManager(context);
            }
        }
        return mInstance;
    }

    public void setStartTakePhotoCamera(Camera mStartTakePhotoCamera) {
        this.mStartTakePhotoCamera = mStartTakePhotoCamera;
    }

    public void setActivityCamera(Camera mActivityCamera) {
        this.mActivityCamera = mActivityCamera;
    }

    public FlashLigthStatus getLightStatus() {
        return mLightStatus;
    }

    /**
     * setLightStatus
     *
     * @param mLightStatus FlashLigthStatus
     */
    public void setLightStatus(FlashLigthStatus mLightStatus) {
        this.mLightStatus = mLightStatus;

        // 保留用户设置
        if (tvFlashLight != null) {
            tvFlashLight.setText(RES_STRING_FLASHLIGHT[mLightStatus.ordinal()]);

            Drawable drawable = mContext.getResources().getDrawable(RES_DRAWABLE_FLASHLIGHT[mLightStatus.ordinal()]);
            drawable.setBounds(0, 0, LEN_PIC, LEN_PIC);
            tvFlashLight.setCompoundDrawables(drawable, null, null, null);

            SPConfigUtil.save(SP_LIGHT_STATUE, mLightStatus.ordinal() + "");
        }
    }

    public CameraDirection getCameraDirection() {
        return mFlashDirection;
    }

    /**
     * setCameraDirection
     *
     * @param mFlashDirection CameraDirection
     */
    public void setCameraDirection(CameraDirection mFlashDirection) {
        this.mFlashDirection = mFlashDirection;

        // 保留用户设置
        if (tvCameraDireation != null) {
            tvCameraDireation.setText(RES_STRING_CAMERA_DIRECTION[mFlashDirection.ordinal()]);

            Drawable drawable = mContext.getResources().getDrawable(RES_DRAWABLE_CAMERA_DIRECTION[mFlashDirection.ordinal()]);
            drawable.setBounds(0, 0, LEN_PIC, LEN_PIC);
            tvCameraDireation.setCompoundDrawables(drawable, null, null, null);
            //不再
            // 记录相机方向  会导致部分相机 前置摄像头
            SPConfigUtil.save(SP_CAMERA_DIRECTION, mFlashDirection.ordinal() + "");

            tvFlashLight.setVisibility(mFlashDirection == CameraDirection.CAMERA_BACK ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * setCameraDirButtonClickable
     *
     * @param clickable boolean
     */
    public void setCameraDirButtonClickable(boolean clickable) {
        if (tvCameraDireation != null) {
            tvCameraDireation.setClickable(clickable);
        }
    }

    @Override
    public int getNumberOfCameras() {
        return mCameraHelper.getNumberOfCameras();
    }

    @Override
    public Camera openCameraFacing(int facing) throws Exception {
        Camera camera = mCameraHelper.openCameraFacing(facing);
        mFlashLightNotSupport.clear();
        if (camera != null) {
            List<String> supportFlashModes = camera.getParameters().getSupportedFlashModes();
            if (facing == 0) {
                //某些supportFlashModes  null  不支持
                if (supportFlashModes != null) {
                    if (!supportFlashModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                        mFlashLightNotSupport.add(FlashLigthStatus.LIGHT_AUTO);
                    }
                    if (!supportFlashModes.contains(Camera.Parameters.FLASH_MODE_ON)) {
                        mFlashLightNotSupport.add(FlashLigthStatus.LIGHT_ON);
                    }
                }
            }
        }
        return camera;
    }

    @Override
    public boolean hasCamera(int facing) {
        return mCameraHelper.hasCamera(facing);
    }

    @Override
    public void getCameraInfo(int cameraId, Camera.CameraInfo cameraInfo) {
        mCameraHelper.getCameraInfo(cameraId, cameraInfo);
    }

    /**
     * hasFrontCamera
     *
     * @return boolean
     */
    public boolean hasFrontCamera() {
        return hasCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    /**
     * hasBackCamera
     *
     * @return boolean
     */
    public boolean hasBackCamera() {
        return hasCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    /**
     * canSwitch
     *
     * @return boolean
     */
    public boolean canSwitch() {
        return hasFrontCamera() && hasBackCamera();
    }

    /**
     * 绑定闪光灯、摄像头设置控件
     *
     * @param tvFlashLight      TextView
     * @param tvCameraDireation TextView
     */
    public void bindOptionMenuView(TextView tvFlashLight, TextView tvCameraDireation) {
        this.tvFlashLight = tvFlashLight;
        this.tvCameraDireation = tvCameraDireation;

        //刷新视图
        setLightStatus(getLightStatus());
        setCameraDirection(getCameraDirection());

        //设置监听
        if (tvFlashLight != null) {
            tvFlashLight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLightStatus(getLightStatus().next());
                }
            });
        }
    }

    /**
     * unbinding
     */
    public void unbinding() {
        tvCameraDireation = null;
        tvFlashLight = null;
    }


    /**
     * 设置相机拍照的尺寸
     *
     * @param camera Camera
     */
    public void setUpPicSize(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        try {
            Camera.Size adapterSize = findBestResolution(camera, 1.0d, TYPE_PICTURE);
            parameters.setPictureSize(adapterSize.width, adapterSize.height);
            camera.setParameters(parameters);

            Log.i(TAG, "setUpPicSize:" + adapterSize.width + "*" + adapterSize.height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置相机预览的尺寸
     *
     * @param camera Camera
     */
    public void setUpPreviewSize(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        try {
            Camera.Size adapterSize = findBestResolution(camera, 1.0d, TYPE_PREVIEW);
            parameters.setPreviewSize(adapterSize.width, adapterSize.height);
            camera.setParameters(parameters);

            Log.i(TAG, "setUpPreviewSize:" + adapterSize.width + "*" + adapterSize.height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置相机最小预览尺寸
     *
     * @param camera Camera
     */
    public void setUpPreviewSizeMin(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        try {
            Camera.Size adapterSize = findMinResolution(camera, TYPE_PREVIEW);

            parameters.setPreviewSize(adapterSize.width, adapterSize.height);
            camera.setParameters(parameters);

            Log.i(TAG, "setUpPreviewSizeMin:" + adapterSize.width + "*" + adapterSize.height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置适合的大小
     *
     * @param camera Camera
     * @param bl     float
     */
    public void setFitPicSize(Camera camera, float bl) {
        Camera.Parameters parameters = camera.getParameters();

        try {
            Camera.Size adapterSize = findFitPicResolution(camera, bl);
            parameters.setPictureSize(adapterSize.width, adapterSize.height);
            camera.setParameters(parameters);

            Log.i(TAG, "setFitPicSize:" + adapterSize.width + "*" + adapterSize.height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置合适的预览尺寸
     *
     * @param camera Camera
     */
    public void setFitPreSize(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        try {
            Camera.Size adapterSize = findFitPreResolution(camera);
            parameters.setPictureSize(adapterSize.width, adapterSize.height);
            camera.setParameters(parameters);

            Log.i(TAG, "setFitPreSize:" + adapterSize.width + "*" + adapterSize.height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回合适的照片尺寸参数
     *
     * @param camera Camera
     * @param bl     float
     * @return Camera.Size
     * @throws Exception Exception
     */
    private Camera.Size findFitPicResolution(Camera camera, float bl) throws Exception {
        Camera.Parameters cameraParameters = camera.getParameters();
        List<Camera.Size> supportedPicResolutions = cameraParameters.getSupportedPictureSizes();

        Camera.Size resultSize = null;
        for (Camera.Size size : supportedPicResolutions) {
            if ((float) size.width / size.height == bl && size.width <= ALLOW_PIC_LEN && size.height <= ALLOW_PIC_LEN) {
                if (resultSize == null) {
                    resultSize = size;
                } else if (size.width > resultSize.width) {
                    resultSize = size;
                }
            }
        }
        if (resultSize == null) {
            return supportedPicResolutions.get(0);
        }
        return resultSize;
    }

    /**
     * 返回合适的预览尺寸参数
     *
     * @param camera Camera
     * @return Camera.Size
     * @throws Exception Exception
     */
    private Camera.Size findFitPreResolution(Camera camera) throws Exception {
        Camera.Parameters cameraParameters = camera.getParameters();
        List<Camera.Size> supportedPicResolutions = cameraParameters.getSupportedPreviewSizes();

        Camera.Size resultSize = null;
        for (Camera.Size size : supportedPicResolutions) {
            if (size.width <= ALLOW_PIC_LEN) {
                if (resultSize == null) {
                    resultSize = size;
                } else if (size.width > resultSize.width) {
                    resultSize = size;
                }
            }
        }
        if (resultSize == null) {
            return supportedPicResolutions.get(0);
        }
        return resultSize;
    }

    /**
     * 返回最小的预览尺寸
     *
     * @param cameraInst Camera
     * @param type       int
     * @return Camera.Size
     * @throws Exception Exception
     */
    private Camera.Size findMinResolution(Camera cameraInst, int type) throws Exception {
        Camera.Parameters cameraParameters = cameraInst.getParameters();
        List<Camera.Size> supportedPicResolutions = type == TYPE_PREVIEW ? cameraParameters.getSupportedPreviewSizes() : cameraParameters.getSupportedPictureSizes(); // 至少会返回一个值

        if (supportedPicResolutions == null) {
            return null;
        }

        Camera.Size resultSize = supportedPicResolutions.get(0);
        for (Camera.Size size : supportedPicResolutions) {
            if (size.width < resultSize.width) {
                resultSize = size;
            }
        }
        return resultSize;
    }

    /**
     * 找到合适的尺寸
     *
     * @param cameraInst    Camera
     * @param maxDistortion 最大允许的宽高比
     * @param type          尺寸类型 0：preview  1：picture
     * @return Camera.Size
     * @throws Exception Exception
     */
    public Camera.Size findBestResolution(Camera cameraInst, double maxDistortion, int type) throws Exception {
        Camera.Parameters cameraParameters = cameraInst.getParameters();
        List<Camera.Size> supportedPicResolutions = type == TYPE_PREVIEW ? cameraParameters.getSupportedPreviewSizes() : cameraParameters.getSupportedPictureSizes(); // 至少会返回一个值

        StringBuilder picResolutionSb = new StringBuilder();
        for (Camera.Size supportedPicResolution : supportedPicResolutions) {
            picResolutionSb.append(supportedPicResolution.width).append('x')
                    .append(supportedPicResolution.height).append(" ");
        }
        Log.d(TAG, "Supported picture resolutions: " + picResolutionSb);

        Camera.Size defaultPictureResolution = cameraParameters.getPictureSize();
        Log.d(TAG, "default picture resolution " + defaultPictureResolution.width + "x"
                + defaultPictureResolution.height);

        // 排序
        List<Camera.Size> sortedSupportedPicResolutions = new ArrayList<Camera.Size>(
                supportedPicResolutions);
        Collections.sort(sortedSupportedPicResolutions, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size a, Camera.Size b) {
                int aRatio = a.width / a.height;
                int bRatio = b.width / a.height;

                if (Math.abs(aRatio - 1) <= Math.abs(bRatio - 1)) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        //返回最合适的
        return sortedSupportedPicResolutions.get(0);
    }

    /**
     * 打开相机界面
     *
     * @param context Context
     */
    public void openCameraActivity(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }


    /**
     * 控制图像的正确显示方向
     *
     * @param camera Camera
     */
    public void setDispaly(Camera camera) {
        if (Build.VERSION.SDK_INT >= VERSION_14) {
            camera.setDisplayOrientation(DEGREES);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            setDisplayOrientation(camera, DEGREES);
        }
    }

    /**
     * 实现的图像的正确显示
     *
     * @param camera Camera
     * @param i      int
     */
    private void setDisplayOrientation(Camera camera, int i) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation",
                    new Class[]{int.class});
            if (downPolymorphic != null) {
                try {
                    downPolymorphic.invoke(camera, new Object[]{i});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchMethodException e) {
            Log.e("Came_e", "图像出错");
        }
    }

    /**
     * releaseActivityCamera
     */
    public void releaseActivityCamera() {
        if (mActivityCamera != null) {
            try {
                mActivityCamera.stopPreview();
                mActivityCamera.setPreviewCallback(null);
                mActivityCamera.setPreviewCallbackWithBuffer(null);
                mActivityCamera.release();
                mActivityCamera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * releaseStartTakePhotoCamera
     */
    public void releaseStartTakePhotoCamera() {
        if (mStartTakePhotoCamera != null) {
            try {
                mStartTakePhotoCamera.stopPreview();
                mStartTakePhotoCamera.setPreviewCallback(null);
                mStartTakePhotoCamera.setPreviewCallbackWithBuffer(null);
                mStartTakePhotoCamera.release();
                mStartTakePhotoCamera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放相机
     *
     * @param camera Camera
     */
    public void releaseCamera(Camera camera) {
        if (camera != null) {
            try {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.setPreviewCallbackWithBuffer(null);
                camera.release();
                camera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 闪光灯状态
     */
    public enum FlashLigthStatus {
        LIGHT_AUTO, LIGHT_ON, LIGTH_OFF;

        /**
         * 不断循环的枚举
         *
         * @return FlashLigthStatus
         */
        public FlashLigthStatus next() {
            int index = ordinal();
            int len = FlashLigthStatus.values().length;
            FlashLigthStatus status = FlashLigthStatus.values()[(index + 1) % len];
            if (!mFlashLightNotSupport.contains(status.name())) {
                return status;
            } else {
                return next();
            }
        }

        /**
         * valueof
         *
         * @param index int
         * @return FlashLigthStatus
         */
        public static FlashLigthStatus valueOf(int index) {
            return FlashLigthStatus.values()[index];
        }
    }

    /**
     * 前置还是后置摄像头
     */
    public enum CameraDirection {
        CAMERA_BACK, CAMERA_FRONT;

        /**
         * 不断循环的枚举
         *
         * @return CameraDirection
         */
        public CameraDirection next() {
            int index = ordinal();
            int len = CameraDirection.values().length;
            return CameraDirection.values()[(index + 1) % len];
        }

        /**
         * valueOf
         *
         * @param index int
         * @return CameraDirection
         */
        public static CameraDirection valueOf(int index) {
            return CameraDirection.values()[index];
        }
    }
}
