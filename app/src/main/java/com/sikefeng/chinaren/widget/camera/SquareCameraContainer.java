package com.sikefeng.chinaren.widget.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.sikefeng.chinaren.XXApplication;
import com.sikefeng.chinaren.ui.activity.CameraActivity;
import com.sikefeng.chinaren.utils.BitmapUtils;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.FileUtils;
import com.sikefeng.chinaren.utils.ScreenUtils;
import com.sikefeng.chinaren.utils.camera.CameraManager;
import com.sikefeng.chinaren.utils.camera.IActivityLifiCycle;
import com.sikefeng.chinaren.utils.camera.ICameraOperation;
import com.sikefeng.chinaren.utils.camera.SPConfigUtil;
import com.sikefeng.chinaren.utils.camera.SensorControler;
import com.sikefeng.chinaren.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class SquareCameraContainer extends FrameLayout implements ICameraOperation, IActivityLifiCycle {
    public static final String TAG = "SquareCameraContainer";
    /**
     * Context
     */
    private Context mContext;

    /**
     * 相机绑定的SurfaceView
     */
    private CameraView mCameraView;

    /**
     * 触摸屏幕时显示的聚焦图案
     */
    private FocusImageView mFocusImageView;
    /**
     * 缩放控件
     */
    private SeekBar mZoomSeekBar;
    /**
     * CameraActivity
     */
    private CameraActivity mActivity;
    /**
     * SoundPool
     */
    private SoundPool mSoundPool;
    /**
     * mFocusSoundPrepared
     */
    private boolean mFocusSoundPrepared;
    /**
     * mFocusSoundId
     */
    private int mFocusSoundId;
    /**
     * mImagePath
     */
    private String mImagePath;
    /**
     * mSensorControler
     */
    private SensorControler mSensorControler;
    /**
     * 一段时间后遮罩层一定要隐藏
     */
    public static final int RESETMASK_DELY = 1000;

    /**
     * SquareCameraContainer
     *
     * @param context Context
     */
    public SquareCameraContainer(Context context) {
        super(context);
        mContext = context;
        init();
    }

    /**
     * SquareCameraContainer
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public SquareCameraContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * 初始化
     */
    void init() {
        inflate(mContext, R.layout.custom_camera_container, this);

        mCameraView = (CameraView) findViewById(R.id.cameraView);
        mFocusImageView = (FocusImageView) findViewById(R.id.focusImageView);
        mZoomSeekBar = (SeekBar) findViewById(R.id.zoomSeekBar);

        mSensorControler = SensorControler.getInstance();

        mSensorControler.setCameraFocusListener(new SensorControler.CameraFocusListener() {
            @Override
            public void onFocus() {
                int screenWidth = ScreenUtils.getScreenWidth(XXApplication.getContext());
                Point point = new Point(screenWidth / 2, screenWidth / 2);

                onCameraFocus(point);
            }
        });
        mCameraView.setOnCameraPrepareListener(new CameraView.OnCameraPrepareListener() {
            @Override
            public void onPrepare(CameraManager.CameraDirection cameraDirection) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, RESETMASK_DELY);
                //在这里相机已经准备好 可以获取maxZoom
                mZoomSeekBar.setMax(mCameraView.getMaxZoom());

                if (cameraDirection == CameraManager.CameraDirection.CAMERA_BACK) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int screenWidth = ScreenUtils.getScreenWidth(XXApplication.getContext());
                            Point point = new Point(screenWidth / 2, screenWidth / 2);
                            onCameraFocus(point);
                        }
                    }, Constants.VALUE_800);
                }
            }
        });
        mCameraView.setSwitchCameraCallBack(new CameraView.SwitchCameraCallBack() {
            @Override
            public void switchCamera(boolean isSwitchFromFront) {
                if (isSwitchFromFront) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int screenWidth = ScreenUtils.getScreenWidth(XXApplication.getContext());
                            Point point = new Point(screenWidth / 2, screenWidth / 2);
                            onCameraFocus(point);
                        }
                    }, Constants.VALUE_300);
                }
            }
        });
        mCameraView.setPictureCallback(pictureCallback);
        mZoomSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

//        //音效初始化
//        mSoundPool = getSoundPool();
    }

    /**
     * getSoundPool
     *
     * @return SoundPool
     */
    private SoundPool getSoundPool() {
        if (mSoundPool == null) {
            mSoundPool = new SoundPool(Constants.VALUE_5, AudioManager.STREAM_MUSIC, 0);
            mFocusSoundId = mSoundPool.load(mContext, R.raw.camera_focus, 1);
            mFocusSoundPrepared = false;
            mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    mFocusSoundPrepared = true;
                }
            });
        }
        return mSoundPool;
    }

    public void setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    /**
     * bindActivity
     *
     * @param activity CameraActivity
     */
    public void bindActivity(CameraActivity activity) {
        this.mActivity = activity;
        if (mCameraView != null) {
            mCameraView.bindActivity(activity);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int len = ScreenUtils.getScreenWidth(XXApplication.getContext());

        //保证View是正方形
        setMeasuredDimension(len, len);
    }

    /**
     * 记录是拖拉照片模式还是放大缩小照片模式
     */

    private static final int MODE_INIT = 0;
    /**
     * 放大缩小照片模式
     */
    private static final int MODE_ZOOM = 1;
    /**
     * 初始状态
     */
    private int mode = MODE_INIT;// 初始状态
    /**
     * startDis
     */
    private float startDis;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
/** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 手指压下屏幕
            case MotionEvent.ACTION_DOWN:
                mode = MODE_INIT;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //如果mZoomSeekBar为null 表示该设备不支持缩放 直接跳过设置mode Move指令也无法执行
                if (mZoomSeekBar == null) {return true;}
                //移除token对象为mZoomSeekBar的延时任务
                mHandler.removeCallbacksAndMessages(mZoomSeekBar);
//                mZoomSeekBar.setVisibility(View.VISIBLE);
                mZoomSeekBar.setVisibility(View.GONE);

                mode = MODE_ZOOM;
                /** 计算两个手指间的距离 */
                startDis = spacing(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == MODE_ZOOM) {
                    //只有同时触屏两个点的时候才执行
                    if (event.getPointerCount() < 2){ return true;}
                    float endDis = spacing(event);// 结束距离
                    //每变化10f zoom变1
                    int scale = (int) ((endDis - startDis) / 10f);
                    if (scale >= 1 || scale <= -1) {
                        int zoom = mCameraView.getZoom() + scale;
                        //zoom不能超出范围
                        if (zoom > mCameraView.getMaxZoom()){ zoom = mCameraView.getMaxZoom();}
                        if (zoom < 0){ zoom = 0;}
                        mCameraView.setZoom(zoom);
                        mZoomSeekBar.setProgress(zoom);
                        //将最后一次的距离设为当前距离
                        startDis = endDis;
                    }
                }
                break;
            // 手指离开屏幕
            case MotionEvent.ACTION_UP:
                if (mode != MODE_ZOOM) {
                    //设置聚焦
                    Point point = new Point((int) event.getX(), (int) event.getY());
                    onCameraFocus(point);
                } else {
                    //ZOOM模式下 在结束两秒后隐藏seekbar 设置token为mZoomSeekBar用以在连续点击时移除前一个定时任务
                    mHandler.postAtTime(new Runnable() {

                        @Override
                        public void run() {
                            mZoomSeekBar.setVisibility(View.GONE);
                        }
                    }, mZoomSeekBar, SystemClock.uptimeMillis() + Constants.VALUE_2000);
                }
                break;
            default:break;
        }
        return true;
    }

    /**
     * 两点的距离
     *
     * @param event MotionEvent
     * @return float
     */
    private float spacing(MotionEvent event) {
        if (event == null) {
            return 0;
        }
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 相机对焦  默认不需要延时
     *
     * @param point Point
     */
    private void onCameraFocus(final Point point) {
        onCameraFocus(point, false);
    }

    /**
     * 相机对焦
     *
     * @param point     Point
     * @param needDelay 是否需要延时
     */
    public void onCameraFocus(final Point point, boolean needDelay) {
        long delayDuration = needDelay ? Constants.VALUE_300 : 0;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mSensorControler.isFocusLocked()) {
                    if (mCameraView.onFocus(point, autoFocusCallback)) {
                        mSensorControler.lockFocus();
                        mFocusImageView.startFocus(point);

                        //播放对焦音效
                        if (mFocusSoundPrepared) {
                            mSoundPool.play(mFocusSoundId, 1.0f, 0.5f, 1, 0, 1.0f);
                        }
                    }
                }
            }
        }, delayDuration);
    }


    @Override
    public void switchCamera() {
        mCameraView.switchCamera();
    }

    @Override
    public void switchFlashMode() {
        mCameraView.switchFlashMode();
    }

    @Override
    public boolean takePicture() {
        setMaskOn();
        boolean flag = mCameraView.takePicture();
        if (!flag) {
            mSensorControler.unlockFocus();
        }
        setMaskOff();
        return flag;
    }

    @Override
    public int getMaxZoom() {
        return mCameraView.getMaxZoom();
    }

    @Override
    public void setZoom(int zoom) {
        mCameraView.setZoom(zoom);
    }

    @Override
    public int getZoom() {
        return mCameraView.getZoom();
    }

    @Override
    public void releaseCamera() {
        if (mCameraView != null) {
            mCameraView.releaseCamera();
        }
    }

    /**
     * Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    /**
     * Camera.AutoFocusCallback
     */
    private final Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //聚焦之后根据结果修改图片
            if (success) {
                mFocusImageView.onFocusSuccess();
            } else {
                //聚焦失败显示的图片，由于未找到合适的资源，这里仍显示同一张图片
                mFocusImageView.onFocusFailed();
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //一秒之后才能再次对焦
                    mSensorControler.unlockFocus();
                }
            }, Constants.VALUE_1000);
        }
    };
    /**
     * pictureCallback
     */
    private final Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            mActivity.rest();

            Log.i(TAG, "pictureCallback");

            new SavePicTask(data, mCameraView.isBackCamera()).start();
        }
    };

    /**
     * onSeekBarChangeListener
     */
    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            mCameraView.setZoom(progress);
            mHandler.removeCallbacksAndMessages(mZoomSeekBar);
            //ZOOM模式下 在结束两秒后隐藏seekbar 设置token为mZoomSeekBar用以在连续点击时移除前一个定时任务
            mHandler.postAtTime(new Runnable() {

                @Override
                public void run() {
                    mZoomSeekBar.setVisibility(View.GONE);
                }
            }, mZoomSeekBar, SystemClock.uptimeMillis() + Constants.VALUE_2000);
        }


        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }


        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onStart() {
        mSensorControler.onStart();

        if (mCameraView != null) {
            mCameraView.onStart();
        }

        mSoundPool = getSoundPool();
    }

    @Override
    public void onStop() {
        mSensorControler.onStop();

        if (mCameraView != null) {
            mCameraView.onStop();
        }

        mSoundPool.release();
        mSoundPool = null;
    }

    /**
     * setMaskOn
     */
    public void setMaskOn() {

    }

    /**
     * setMaskOff
     */
    public void setMaskOff() {

    }

    /**
     * 旋转bitmap
     * 对于前置摄像头和后置摄像头采用不同的旋转角度  前置摄像头还需要做镜像水平翻转
     *
     * @param bitmap       Bitmap
     * @param isBackCamera boolean
     * @return Bitmap
     */
    public Bitmap rotateBitmap(Bitmap bitmap, boolean isBackCamera) {
        System.gc();
        int degrees = isBackCamera ? 0 : 0;
        degrees = mCameraView.getPicRotation();
        if (null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        if (!isBackCamera) {
            matrix.postScale(-1, 1, bitmap.getWidth() / 2, bitmap.getHeight() / 2);   //镜像水平翻转
        }
//            Bitmap bmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,!isBackCamera);
        //不需要透明度 使用RGB_565
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bitmap, matrix, paint);


        if (null != bitmap) {
            bitmap.recycle();
        }

        return bmp;
    }


    /**
     * 获取以中心点为中心的正方形区域
     *
     * @param data byte[]
     * @return Rect
     */
    private Rect getCropRect(byte[] data) {
        //获得图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        int width = options.outWidth;
        int height = options.outHeight;
        int centerX = width / 2;
        int centerY = height / 2;

        int PHOTO_LEN = Math.min(width, height);
        return new Rect(centerX - PHOTO_LEN / 2, centerY - PHOTO_LEN / 2, centerX + PHOTO_LEN / 2, centerY + PHOTO_LEN / 2);
    }

    /**
     * 给出合适的sampleSize的建议
     *
     * @param data   byte[]
     * @param target int
     * @return int
     */
    private int suggestSampleSize(byte[] data, int target) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        int w = options.outWidth;
        int h = options.outHeight;
        int candidateW = w / target;
        int candidateH = h / target;
        int candidate = Math.max(candidateW, candidateH);
        if (candidate == 0) {
            return 1;
        }
        if (candidate > 1) {
            if ((w > target) && (w / candidate) < target) {
                candidate -= 1;
            }
        }
        if (candidate > 1) {
            if ((h > target) && (h / candidate) < target) {
                candidate -= 1;
            }
        }
        //if (VERBOSE)
        Log.i(TAG, "for w/h " + w + "/" + h + " returning " + candidate + "(" + (w / candidate) + " / " + (h / candidate));
        return candidate;
    }

    /**
     * fileScan
     *
     * @param filePath String
     */
    public void fileScan(String filePath) {
        Uri data = Uri.parse("file://" + filePath);
        mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    /**
     * 定义lasttiME
     */
    private long lastTime;

    /**
     * 保存任务
     */
    private class SavePicTask extends Thread {
        /**
         * data
         */
        private byte[] data;
        /**
         * isBackCamera
         */
        private boolean isBackCamera;
        /**
         * sampleSizeSuggested
         */
        private boolean sampleSizeSuggested;
        /**
         * 寻找合适的bitmap发生io异常  允许一次重试
         */
        private boolean ioExceptionRetried;

        /**
         * SavePicTask
         *
         * @param data         byte[]
         * @param isBackCamera boolean
         */
        SavePicTask(byte[] data, boolean isBackCamera) {
            sampleSizeSuggested = false;
            ioExceptionRetried = false;
            this.data = data;
            this.isBackCamera = isBackCamera;
        }

        @Override
        public void run() {
            super.run();

            long current = System.currentTimeMillis();

            Message msg = handler.obtainMessage();
            msg.obj = saveToSDCard(data);
            handler.sendMessage(msg);

            Log.i(TAG, "save photo:" + (System.currentTimeMillis() - current) + "ms");
        }

        /**
         * 将拍下来的照片存放在SD卡中
         *
         * @param data byte[]
         * @return imagePath 图片路径
         */
        public boolean saveToSDCard(byte[] data) {
            lastTime = System.currentTimeMillis();

            //ADD 生成保存图片的路径
            mImagePath = FileUtils.getCameraImgPath();
            Log.i(TAG, "ImagePath:" + mImagePath);

            //保存到SD卡
            if (TextUtils.isEmpty(mImagePath)) {
                Log.e(TAG, "要保存的图片路径为空");
                return false;
            }

            if (!FileUtils.checkSDcard()) {
                Toast.makeText(mContext, R.string.tips_sdcard_notexist, Toast.LENGTH_LONG).show();

                return false;
            }

            Log.i(TAG, "saveToSDCard beforefindFitBitmap time:" + (System.currentTimeMillis() - lastTime));
            //从本地读取合适的sampleSize,默认为1
            int sampleSize = SPConfigUtil.loadInt("sampleSize", 1);
            Bitmap bitmap = findFitBitmap(data, getCropRect(data), sampleSize);

            if (bitmap == null) {
                return false;
            }

            Log.i(TAG, "saveToSDCard beforeSave time:" + (System.currentTimeMillis() - lastTime));
            BitmapUtils.saveBitmap(bitmap, mImagePath);
            XXApplication.getInstance().setCameraBitmap(bitmap);
//            bitmap.recycle();

            Log.i(TAG, "saveToSDCard afterSave time:" + (System.currentTimeMillis() - lastTime));
            return true;
        }

        /**
         * 寻找合适的bitmap  剪切rect  并且做旋转  镜像处理
         *
         * @param data       byte[]
         * @param rect       Rect
         * @param sampleSize int
         * @return Bitmap
         */
        private Bitmap findFitBitmap(byte[] data, Rect rect, int sampleSize) {
            InputStream is = null;
            System.gc();
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inSampleSize = sampleSize;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inPurgeable = true;
                options.inInputShareable = true;

                is = new ByteArrayInputStream(data);

                Bitmap bitmap = BitmapUtils.decode(is, rect, options);

//                BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);
//                bitmap = decoder.decodeRegion(rect, options);


                //未抛出异常，保存合适的sampleSize
                SPConfigUtil.save("sampleSize", sampleSize + "");

                Log.i(TAG, "sampleSize:" + sampleSize);
                Log.i(TAG, "saveToSDCard afterLoad Bitmap time:" + (System.currentTimeMillis() - lastTime));

//                if(mCameraView.needRotateBitmap()) {
                bitmap = rotateBitmap(bitmap, isBackCamera);
                Log.i(TAG, "saveToSDCard afterRotate Bitmap time:" + (System.currentTimeMillis() - lastTime));
//                }
                return bitmap;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                System.gc();

                /* 是否对sampleSize做出过建议，没有就做一次建议，按照建议的尺寸做出缩放，做过就直接缩小图片**/
                if (sampleSizeSuggested) {
                    return findFitBitmap(data, rect, sampleSize * 2);
                } else {
                    return findFitBitmap(data, rect, suggestSampleSize(data, ScreenUtils.getScreenWidth(XXApplication.getContext())));
                }
            } catch (Exception e) {
                e.printStackTrace();
                //try again
                if (!ioExceptionRetried) {
                    ioExceptionRetried = true;
                    return findFitBitmap(data, rect, sampleSize);
                }
                return null;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Handler
     */
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            boolean result = (Boolean) msg.obj;

            Log.i(TAG, "TASK onPostExecute:" + (System.currentTimeMillis() - lastTime));

            if (result) {
                fileScan(mImagePath);
//                releaseCamera();    //不要在这个地方释放相机资源   这里是浪费时间的最大元凶  约1500ms左右
                mActivity.postTakePhoto();
                Log.i(TAG, "TASK:" + (System.currentTimeMillis() - lastTime));
            } else {
                Log.e(TAG, "photo save failed!");
                Toast.makeText(mContext, R.string.topic_camera_takephoto_failure, Toast.LENGTH_SHORT).show();

                mActivity.rest();

                mCameraView.startPreview();
            }
        }
    };
}
