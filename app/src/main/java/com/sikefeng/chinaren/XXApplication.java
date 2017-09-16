
package com.sikefeng.chinaren;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;
import com.sikefeng.chinaren.utils.Cockroach;
import com.sikefeng.chinaren.utils.CrashApphandler;
import com.sikefeng.chinaren.utils.FileUtils;
import com.sikefeng.chinaren.utils.img.ImageUtils;
import com.sikefeng.chinaren.utils.img.glide.GlideLoadStrategy;
import com.sikefeng.mvpvmlib.utils.LogUtils;
import com.zhy.changeskin.SkinManager;



public class XXApplication extends Application {
    /**
     * 相机图片，暂定存在这里
     */
    private Bitmap mCameraBitmap;

    /**
     * 自己实例化
     */
    private static XXApplication instance;

    public static XXApplication getInstance() {
        return instance;
    }

    public static void setInstance(XXApplication instance) {
        XXApplication.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.setInstance(XXApplication.this);
        init();


        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
                //由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
                //所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
                //new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                            Log.e("AndroidRuntime","--->CockroachException:"+thread+"<---",throwable);
                            Toast.makeText(XXApplication.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        //AutoLayoutConifg.getInstance().useDeviceSize().init(this);
        ImageUtils.getInstance().init(new GlideLoadStrategy());
        LogUtils.init(BuildConfig.DEBUG);
        SkinManager.getInstance().init(this);
        LogUtils.setIsPrintResponseData(BuildConfig.DEBUG);//是否打印网络返回的源数据
        LogUtils.setIsPrintResponseData(false);

        //初始化对话框
        StyledDialog.init(this);
        registCallback();
        //初始化对话框结束

        //统一URL模式
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        //结束统一URL模式


        FileUtils.initFolder();

        //初始化崩溃捕获
        CrashApphandler.getInstance().init(this);
    }

    /**
     * 在activity生命周期callback中拿到顶层activity引用
     */
    private void registCallback() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActyManager.getInstance().setCurrentActivity(activity);


            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public Bitmap getCameraBitmap() {
        return mCameraBitmap;
    }

    /**
     * 设置图片
     * @param mCameraBitmap Bitmap
     */
    public void setCameraBitmap(Bitmap mCameraBitmap) {
        if (mCameraBitmap != null) {
            recycleCameraBitmap();
        }
        this.mCameraBitmap = mCameraBitmap;
    }

    /**
     * 回收图片
     */
    public void recycleCameraBitmap() {
        if (mCameraBitmap != null) {
            if (!mCameraBitmap.isRecycled()) {
                mCameraBitmap.recycle();
            }
            mCameraBitmap = null;
        }
    }
}
