package com.sikefeng.chinaren.utils.speech;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.sikefeng.chinaren.MyApplication;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.widget.dialog.AlarmDialog;
import com.sikefeng.chinaren.widget.dialog.CommomDialog;
import com.sikefeng.chinaren.widget.qrcode.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * 语音识别器
 */
public class SpeechRecognizerEditText {

    private static String TAG = SpeechRecognizerEditText.class.getSimpleName();
    private final String sp_name = "com.iflytek.setting";
    // 语音听写对象
    private SpeechRecognizer mIat;
    private OnSpeechResultListener mListener;
    int ret = 0; // 函数调用返回值
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private StringBuffer stringBuffer;
    private int waitTime = 0;

    /***
     * 一种常用单例模式
     */
    private static SpeechRecognizerEditText instance = null;
    private Context mContext;

    public SpeechRecognizerEditText(Context mContext) {

        this.mContext = mContext.getApplicationContext();
        initConfig();
    }

    public static SpeechRecognizerEditText getInstance(Context mContext) {
        // 使用时生成实例，提高了效率！
        if (instance == null)
            instance = new SpeechRecognizerEditText(mContext);
        return instance;
    }



    public void initConfig() {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);

        mSharedPreferences = mContext.getSharedPreferences(sp_name, Activity.MODE_PRIVATE);
    }

    /**
     * 检查是否正在输入语音
     *
     * @return
     */
    public boolean isListening() {
        if (mIat != null) {
            if (mIat.isListening()) {
                return true;
            }
        }
        return false;
    }


    //开始录入语音
    public void start() {

        stringBuffer = new StringBuffer();
        // 移动数据分析，收集开始听写事件
        mIatResults.clear();
        // 设置参数
        setParam();
        // 不显示听写对话框
        ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            log("听写失败,错误码：" + ret);
        } else {
            log(mContext.getString(R.string.text_begin));
        }
    }


    public void stop() {
        mIat.stopListening();
        log("停止听写");
    }

    public void cancal() {
        mIat.cancel();
        log("取消听写");
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            log("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            if (mListener != null) {
                mListener.speechError(error);
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            log("结束说话");
            if (mListener != null) {
                mListener.endOfSpeech();
            }
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            if (!isLast) {
                String text = JsonParser.parseIatResult(results.getResultString());
                stringBuffer.append(text);
                if (mListener != null) {
                    mListener.onListenering(text);
                }
            }
            if (isLast) {
                // TODO 最后的结果
                if (mListener != null) {
                    String content=stringBuffer.toString();
                    mListener.onListenerResult(content);
                }
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            log("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };



    /**
     * 设置语音录入等待时间
     *
     * @param waitTime
     */
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
//        this.mTranslateEnable = mSharedPreferences.getBoolean(mContext.getString(R.string.pref_key_translate), false);
//        if (mTranslateEnable) {
//            Log.i(TAG, "translate enable");
//            mIat.setParameter(SpeechConstant.ASR_SCH, "1");
//            mIat.setParameter(SpeechConstant.ADD_CAP, "translate");
//            mIat.setParameter(SpeechConstant.TRS_SRC, "its");
//        }
        //设置说话的语言
        String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);
//            if (mTranslateEnable) {
//                mIat.setParameter(SpeechConstant.ORI_LANG, "en");
//                mIat.setParameter(SpeechConstant.TRANS_LANG, "cn");
//            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
//            if (mTranslateEnable) {
//                mIat.setParameter(SpeechConstant.ORI_LANG, "cn");
//                mIat.setParameter(SpeechConstant.TRANS_LANG, "en");
//            }
        }
        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理（0-10000毫秒）
        if (waitTime > 0) {
            mIat.setParameter(SpeechConstant.VAD_BOS, String.valueOf(waitTime));
        } else {
            mIat.setParameter(SpeechConstant.VAD_BOS, "2500");
        }
        log(String.valueOf(waitTime));
//        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "10000"));
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音（0-10000毫秒）
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "2500"));
//        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
        waitTime = 0;
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                log("初始化失败，错误码：" + code);
            }
        }
    };

    private void log(String s) {
//        Log.e(TAG, s);
        System.out.println("========msg=" + s);
    }


    public void setOnSpeechResultListener(OnSpeechResultListener myListener) {
        mListener = myListener;
    }

    //自定义语音录入回调接口
    public interface OnSpeechResultListener {
        void onListenerResult(String result);

        void endOfSpeech();

        void speechError(SpeechError error);

        void onListenering(String result);
    }


}
