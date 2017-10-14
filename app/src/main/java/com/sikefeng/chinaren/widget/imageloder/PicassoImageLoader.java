package com.sikefeng.chinaren.widget.imageloder;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.impl.AlbumImageLoader;

import java.io.File;

/**
 * 文件名：PicassoImageLoader <br>
 * 创建时间： 2017/8/6 下午12:04 <br>
 * 文件描述：<br>
 * PicassoImageLoader
 *
 * @author <a href="mailto:dawson@xxxxtech.com">Dawson</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class PicassoImageLoader implements AlbumImageLoader {

    @Override
    public void loadImage(ImageView imageView, String imagePath, int width, int height) {
        Picasso.with(imageView.getContext())
                .load(new File(imagePath))
                .centerCrop()
                .resize(width, height)
                .into(imageView);
    }

}
