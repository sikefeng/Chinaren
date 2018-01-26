package com.sikefeng.chinaren.ui.adapter;

import android.databinding.BindingAdapter;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 文件名：ImageBindingAdapter <br>
 * 创建时间： 7/1/18 下午PM2:38 <br>
 * 文件描述：<br>
 * TODO: 这里对类的描述
 *
 * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class ImageBindingAdapter {


    @BindingAdapter({"actualImageUri"})
    public static void loadNetworkImage(SimpleDraweeView simpleDraweeView, String url) {
        simpleDraweeView.setImageURI(Uri.parse(url));
    }


}
