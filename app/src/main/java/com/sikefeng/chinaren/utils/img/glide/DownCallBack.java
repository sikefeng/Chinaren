/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.utils.img.glide;

import java.io.File;

public interface DownCallBack {
    /**
     * 成功
      * @param file 下载的图片文件
     */
    void onSuccess(File file);

    /**
     * 失败
     * @param err 失败的信息
     */
    void onFailed(String err);
}
