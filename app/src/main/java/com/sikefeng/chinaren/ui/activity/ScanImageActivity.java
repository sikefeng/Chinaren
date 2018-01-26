package com.sikefeng.chinaren.ui.activity;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.view.View;
import android.view.Window;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.databinding.ActivityScanImageBinding;
import com.sikefeng.chinaren.ui.fragment.MyFragment;

import java.util.List;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * 文件名：ScanImageActivity <br>
 * 创建时间： 7/1/18 下午PM4:20 <br>
 * 文件描述：<br>
 * 浏览图片
 *
 * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class ScanImageActivity extends AppCompatActivity {

    private ActivityScanImageBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindowTransitions();
        layoutBinding= DataBindingUtil.setContentView(this, R.layout.activity_scan_image);
        initTitleBar();
        PhotoDraweeView draweeView = layoutBinding.photoDraweeView;
        ViewCompat.setTransitionName(draweeView, MyFragment.SHARED_ELEMENT_NAME);
        draweeView.setPhotoUri(Uri.parse("res:///" + R.mipmap.tou));
        draweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override public void onPhotoTap(View view, float x, float y) {
                onBackPressed();
            }
        });


    }

    private void initTitleBar(){
        layoutBinding.toolbar.setTitle("");
        setSupportActionBar(layoutBinding.toolbar);
        try {
            //给左上角图标的左边加上一个返回的图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){}
        layoutBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void initWindowTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            AutoTransition transition = new AutoTransition();
            getWindow().setSharedElementEnterTransition(transition);
            getWindow().setSharedElementExitTransition(transition);
            ActivityCompat.setEnterSharedElementCallback(this, new SharedElementCallback() {
                @Override public void onSharedElementEnd(List<String> sharedElementNames,
                                                         List<View> sharedElements, List<View> sharedElementSnapshots) {
                    for (final View view : sharedElements) {
                        if (view instanceof PhotoDraweeView) {
                            ((PhotoDraweeView) view).setScale(1f, true);
                        }
                    }
                }
            });
        }
    }
}
