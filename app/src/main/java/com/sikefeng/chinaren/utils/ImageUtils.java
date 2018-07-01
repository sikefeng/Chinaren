package com.sikefeng.chinaren.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sikefeng.chinaren.ui.activity.ScanImageActivity;

import java.util.List;

import static com.sikefeng.chinaren.ui.fragment.MyFragment.SHARED_ELEMENT_NAME;

/**
 * Created by Richard on 7/1/18.
 */

public class ImageUtils {

    public static void previewImage(Activity activity,ImageView imageView,String url) {
        ViewCompat.setTransitionName(imageView, SHARED_ELEMENT_NAME);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity, imageView, SHARED_ELEMENT_NAME);

                // FIX BUG https://github.com/facebook/fresco/issues/1445
                ActivityCompat.setExitSharedElementCallback(activity,
                        new android.support.v4.app.SharedElementCallback() {
                            @Override
                            public void onSharedElementEnd(List<String> sharedElementNames,
                                                           List<View> sharedElements, List<View> sharedElementSnapshots) {
                                super.onSharedElementEnd(sharedElementNames, sharedElements,
                                        sharedElementSnapshots);
                                for (final View view : sharedElements) {
                                    if (view instanceof SimpleDraweeView) {
                                        view.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        });
                Intent intent=new Intent(activity, ScanImageActivity.class);
                intent.putExtra("path",url);
                ActivityCompat.startActivity(activity,intent , optionsCompat.toBundle());
            }
        });
    }


}
