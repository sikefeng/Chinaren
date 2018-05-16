/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.mvpvmlib.base;

import android.app.Activity;
import android.content.Intent;


public interface IRBaseView {
    /**
     * 获取Activity
     * @return Activity
     */
    Activity getActivity();

    /**
     * 开始Activity，即跳转到Activity
     * @param intent Intent
     */
    void startActivity(Intent intent);

    /**
     * 完成Acitivty生命周期
     */
    void finish();
}
