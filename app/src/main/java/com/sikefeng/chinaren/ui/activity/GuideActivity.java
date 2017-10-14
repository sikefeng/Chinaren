package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;
import com.sikefeng.mvpvmlib.utils.LogUtils;

/**
 * 文件名：GuideActivity <br>
 * 创建时间： 2017/7/21 0021 下午17:21 <br>
 * 文件描述：<br>
 * app启动页
 *
 * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class GuideActivity extends AppCompatActivity {
    /**
     * 上下文
     */
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = GuideActivity.this;
        boolean isLogin = (boolean) SharePreferenceUtils.get(context, Constants.ISLOGIN, false);
        String token = (String) SharePreferenceUtils.get(context, Constants.TOKEN, "");
        LogUtils.i(isLogin+"-------------"+token);
        if (isLogin && !TextUtils.isEmpty(token)) {
            ARouter.getInstance().build(Constants.MAIN_URL).navigation();
        } else {
            ARouter.getInstance().build(Constants.LOGIN_URL).navigation();
        }
        finish();
    }
}

