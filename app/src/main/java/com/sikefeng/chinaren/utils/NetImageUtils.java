package com.sikefeng.chinaren.utils;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sikefeng.chinaren.R;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;

/**
 * Created by Richard on 26/9/17.
 */

public class NetImageUtils {

    public static void previewImage(Activity activity, int position, ArrayList<String> imageList) {
        Album.gallery(activity)
                .toolBarColor(ContextCompat.getColor(activity, R.color.theme_color)) // Toolbar color.
                .statusBarColor(ContextCompat.getColor(activity, R.color.theme_color)) // StatusBar color.
                .navigationBarColor(ActivityCompat.getColor(activity, R.color.theme_color)) // NavigationBar color.
                .checkedList(imageList) // Image list.
                .currentPosition(position) // Preview first to show the first few.
//                .checkFunction(true) // Does the user have an anti-selection when previewing.
                .start(Constants.VALUE_104);

    }

    /**
     * 功能描述：TODO: 这里对方法的描述
     * <br>创建时间： 2018-01-07 14:51:47

     * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a>
     * @param mImageView
     * @param url
     */
    public static void displayImage(SimpleDraweeView mImageView, String url){
          mImageView.setImageURI(Uri.parse(url));
//        Picasso.with(context)
//                .load(url)
//                .resize(100, 100)
//                .centerCrop()
//                .into(mImageView);

    }
}
