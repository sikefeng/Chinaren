package com.sikefeng.chinaren.utils;

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Richard on 26/9/17.
 */

public class NetImageUtils {

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
