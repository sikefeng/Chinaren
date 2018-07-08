package com.sikefeng.chinaren.utils.speech;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.sikefeng.chinaren.entity.model.AppBean;
import com.sikefeng.chinaren.utils.AppUtil;

import java.util.List;

import static android.media.AudioManager.ADJUST_RAISE;
import static android.media.AudioManager.FLAG_SHOW_UI;

public class MyAccessibilityService extends AccessibilityService {

    private boolean isLongPressKey;//是否长按
    //记录用户首次点击返回键的时间
    private long startTime = 0;
    private long endTime = 0;
    private int count = 0;
    public static List<AppBean> appList;

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (!isLongPressKey) {
                    count = 0;
                    startTime = System.currentTimeMillis();
                    isLongPressKey = true;
                    handler.sendEmptyMessage(1);
                } else {
                    endTime = System.currentTimeMillis() - startTime;
                    if (endTime > 2000) {

                    } else {
                        adjustStreamVolume();
                        handler.removeMessages(1);
                    }
                    isLongPressKey = false;
                }
                return true;
            default:
                break;
        }
        return super.onKeyEvent(event);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (msg.what == 1) {
//                if (count < 2) {
//                    count = count + 1;
//                    handler.sendEmptyMessageDelayed(1, 1000);
//                } else {
//                    handler.removeMessages(1);
//                    Intent intent = new Intent();
//                    intent.setClass(getBaseContext(), DialogActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//            }
        }
    };


    @Override
    public void onInterrupt() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        appList = AppUtil.getInstance(this).getAllApk();//获取已安装APP应用列表
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Auto-generated method stub

    }

    /**
     * 功能描述：TODO: 调整音量
     * 创建时间： 2018-05-23 14:20:33
     *
     * @author Sikefeng
     */
    public void adjustStreamVolume() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        am.adjustStreamVolume(STREAM_SYSTEM, ADJUST_RAISE, FLAG_SHOW_UI);//系统音量
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC, ADJUST_RAISE, FLAG_SHOW_UI);//媒体音量
    }


}
