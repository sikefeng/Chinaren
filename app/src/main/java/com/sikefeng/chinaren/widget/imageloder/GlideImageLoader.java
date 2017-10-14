package com.sikefeng.chinaren.widget.imageloder;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.album.impl.AlbumImageLoader;

/**
 * 文件名：GlideImageLoader <br>
 * 创建时间： 2017/8/6 下午12:04 <br>
 * 文件描述：<br>
 * GlideImageLoader
 *
 * @author <a href="mailto:dawson@xxxxtech.com">Dawson</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class GlideImageLoader implements AlbumImageLoader {

    @Override
    public void loadImage(ImageView imageView, String imagePath, int width, int height) {
        Glide.with(imageView.getContext())
                .load(imagePath)
                .into(imageView);
    }

}
