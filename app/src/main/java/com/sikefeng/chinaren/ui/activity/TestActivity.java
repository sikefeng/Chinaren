package com.sikefeng.chinaren.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseActivity;
import com.sikefeng.chinaren.databinding.ActivityTestBinding;
import com.sikefeng.chinaren.entity.event.PermissionEvent;
import com.sikefeng.chinaren.presenter.TestPresenter;
import com.sikefeng.chinaren.presenter.vm.TestViewModel;
import com.sikefeng.chinaren.utils.PermissionUtils;
import com.sikefeng.mvpvmlib.base.RBasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Richard on 24/9/17.
 */

public class TestActivity extends BaseActivity<ActivityTestBinding> implements BGANinePhotoLayout.Delegate{
    /**
     * 用户登录数据协调器
     */
    private TestPresenter presenter;

    /**
     * 显示权限设置
     */
    private boolean showAuth = true;
    private BGANinePhotoLayout mCurrentClickNpl;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }
    /**
     * 接收授权事件
     *
     * @param event PermissionEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPermissionEvent(PermissionEvent event) {
        showAuth = event.isToSystemAuthorization();
    }

    @Override
    protected boolean isEnableEventBus() {
        return true;
    }

    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new TestPresenter(this, new TestViewModel());
        }
        return presenter;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        //获取当前页面.setSwipeBackEnable(false);//设置是否可滑动
        SwipeBackHelper.getCurrentPage(this);

        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());


        getBinding().nplItemMomentPhotos.setDelegate(TestActivity.this);
        ArrayList<String> photos=new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered19.png"));
        getBinding().nplItemMomentPhotos.setData(photos);
    }



    @Override
    public void onResume() {
        super.onResume();
        if (showAuth) {
            showAuth = false;
            PermissionUtils.getInstace().authorization(this, showAuth, true, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA); //6.0+授权访问
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /*** 点击空白位置 隐藏软键盘*/
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }
    /**
     * 图片预览，兼容6.0动态权限
     */
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PREVIEW)
    private void photoPreviewWrapper() {
        if (mCurrentClickNpl == null) {
            return;
        }

        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            if (mCurrentClickNpl.getItemCount() == 1) {
                // 预览单张图片
//                startActivity(BGAPhotoPreviewActivity.newIntent(this, mDownLoadableCb.isChecked() ? downloadDir : null, mCurrentClickNpl.getCurrentClickItem()));
                startActivity(BGAPhotoPreviewActivity.newIntent(this, null, mCurrentClickNpl.getCurrentClickItem()));
            } else if (mCurrentClickNpl.getItemCount() > 1) {
                // 预览多张图片
//                startActivity(BGAPhotoPreviewActivity.newIntent(this, mDownLoadableCb.isChecked() ? downloadDir : null, mCurrentClickNpl.getData(), mCurrentClickNpl.getCurrentClickItemPosition()));
                startActivity(BGAPhotoPreviewActivity.newIntent(this,  null, mCurrentClickNpl.getData(), mCurrentClickNpl.getCurrentClickItemPosition()));
            }
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
        }
    }

}

