package com.sikefeng.chinaren.utils;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.sikefeng.chinaren.R;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;

import static com.hss01248.dialog.StyledDialog.context;

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


    public static void displayImage(ImageView mImageView, String url){
        Picasso.with(context)
                .load(url)
                .resize(100, 100)
                .centerCrop()
                .into(mImageView);

    }
}
