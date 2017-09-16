package com.sikefeng.chinaren.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.XXApplication;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.utils.camera.CameraManager;
import com.sikefeng.chinaren.utils.event.CameraEvent;
import com.sikefeng.chinaren.widget.camera.SquareCameraContainer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;




public class CameraActivity extends Activity {
    public static final String TAG = "CameraActivity";
    /**
     * 定义相机管理器
     */
    private CameraManager mCameraManager;

    /**
     * 定义TextView
     */
    private TextView mTvFlashLight, mTvCameraDireation;
    /**
     * 定义相机容器
     */
    private SquareCameraContainer mCameraContainer;
    /**
     * 定义图片按钮
     */
//    private ImageButton mIbRecentPic;
    /**
     * finish计数   当动画和异步任务都结束的时候  再调用finish方法
     */
    private int mFinishCount = 2;
    /**
     * 缩略图管理器
     */
//    AlbumHelper helper;

    /**
     * 设置500毫秒
     */
    private final int mSeconed = 500;

    /**
     * 定义Handler
     */
    private Handler handler = new Handler();

    /**
     * 订阅时间
     * @param event 自定义事件类
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CameraEvent event) {
        ToastUtils.showLong(event.getMsg());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mCameraManager = CameraManager.getInstance(this);

//        helper = AlbumHelper.getHelper();
//        helper.init(getApplicationContext());

        initView();
        initData();
        initListener();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化视图
     */
    void initView() {
        mTvFlashLight = (TextView) findViewById(R.id.tv_flashlight);
        mTvCameraDireation = (TextView) findViewById(R.id.tv_camera_direction);
        mCameraContainer = (SquareCameraContainer) findViewById(R.id.cameraContainer);
//        m_ibRecentPic = (ImageButton) findViewById(R.id.ib_recentpic);
    }

    /**
     * 初始化数据
     */
    void initData() {
        mCameraManager.bindOptionMenuView(mTvFlashLight, mTvCameraDireation);
//        mCameraContainer.setImagePath(getIntent().getStringExtra(PATH_OUTIMG));
        mCameraContainer.bindActivity(this);

        //todo  获取系统相册中的一张相片
//        List<ImageItem> list = helper.getImagesList();
//        if (list != null && list.size() != 0) {
//            m_ibRecentPic.setImageBitmap(BitmapUtils.createCaptureBitmap(list.get(0).getImagePath()));
//            m_ibRecentPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            m_ibRecentPic.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //跳转到系统相册
//                    Intent intent = new Intent(Intent.ACTION_DEFAULT,
//                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivity(intent);
//                }
//            });
//        } else {
//            //设置默认图片
//            m_ibRecentPic.setImageResource(R.drawable.selector_camera_icon_album);
//        }
    }

    /**
     * 初始化监听事件
     */
    void initListener() {
        if (mCameraManager.canSwitch()) {
            mTvCameraDireation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTvCameraDireation.setClickable(false);
                    mCameraContainer.switchCamera();

                    //500ms后才能再次点击
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTvCameraDireation.setClickable(true);
                        }
                    }, mSeconed);
                }
            });
        }

        mTvFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraContainer.switchFlashMode();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCameraContainer != null) {
            mCameraContainer.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCameraContainer != null) {
            mCameraContainer.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraManager.unbinding();
        mCameraManager.releaseActivityCamera();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //在创建前  释放相机
        mCameraManager.unbinding();
        mCameraManager.releaseActivityCamera();
    }

    /**
     * 对一些参数重置
     */
    public void rest() {
        mFinishCount = 2;
    }

    /**
     * 退出按钮点击
     * @param view View
     */
    public void onExitClicked(View view) {
        onBackPressed();
    }

    /**
     * 照相按钮点击
     * @param view View
     */
    public void onTakePhotoClicked(View view) {
        mCameraContainer.takePicture();
    }

    /**
     * 提交finish任务  进行计数  都在main Thread
     */

    /**
     * 照完照片 提交
     */
    public void postTakePhoto() {
//        mFinishCount--;
//        if (mFinishCount < 0) mFinishCount = 2;
//        if (mFinishCount == 0) {
//            setResult(RESULT_OK);
//            finish();
//        }


//        mCameraManager.releaseActivityCamera();

        Toast.makeText(this, "take photo", Toast.LENGTH_SHORT).show();

        Bitmap bitmap = XXApplication.getInstance().getCameraBitmap();

//        if (bitmap != null) {
//            m_ibRecentPic.setImageBitmap(bitmap);
//        }


    }
}






