package com.sikefeng.chinaren.utils.speech;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.sikefeng.chinaren.utils.Constants;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * 语音合成器
 */
public class SpeechSynthesizerUtils implements SpeechImpl {

    private final String sp_name = "com.iflytek.setting";
    private static String TAG = SpeechSynthesizerUtils.class.getSimpleName();
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;//在线播放
    // private String mEngineType =SpeechConstant.TYPE_LOCAL;  //离线播放
    // 默认发音人
    private String voicerName = "xiaoyan";
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    //保存语音播放配置
    private SharedPreferences mSharedPreferences;
    // 语音合成对象
    private SpeechSynthesizer speechSynthesizer;

    private SynthesizerListener mSynthesizerListener;

    /***
     * 一种常用单例模式
     */
    private static SpeechSynthesizerUtils instance = null;
    private Context mContext;

    public SpeechSynthesizerUtils(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        initConfig();
    }

    public static SpeechSynthesizerUtils getInstance(Context mContext) {
        // 使用时生成实例，提高了效率！
        if (instance == null)
            instance = new SpeechSynthesizerUtils(mContext);
        return instance;
    }

    public void initConfig() {
        voicerName= (String) SharePreferenceUtils.get(mContext, Constants.ROBOT_VOICERNANE,"xiaoyan");
        mSharedPreferences = mContext.getSharedPreferences(sp_name, MODE_PRIVATE);
        // 设置参数
        setParam();
    }

    @Override
    public void startSpeak(String content) {
//        if (isPlaying()){
//            stopSpeak();
//        }
        //语音合成时取消语音录入
//        if (SpeechRecognizerUtils.getInstance(mContext).isListening()) {
//            SpeechRecognizerUtils.getInstance(mContext).cancal();
//        }
        int code = speechSynthesizer.startSpeaking(content, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
//			String path = Environment.getExternalStorageDirectory()+"/tts.ico";
//			int code = mTts.synthesizeToUri(text, path, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            Log.e(TAG, "语音合成失败,错误码: " + code);
        }
    }

    @Override
    public void pauseSpeak() {
        speechSynthesizer.pauseSpeaking();
    }

    @Override
    public void resumeSpeak() {
        speechSynthesizer.resumeSpeaking();
    }

    @Override
    public void stopSpeak() {
        if (null != speechSynthesizer) {
            speechSynthesizer.stopSpeaking();
            // 退出时释放连接
            speechSynthesizer.destroy();
        }
    }

    @Override
    public boolean isPlaying() {
        return speechSynthesizer.isSpeaking();
    }

    @Override
    public void setSpeaker(String speaker) {
        voicerName = speaker;
        // 设置参数
        setParam();
    }

    /**
     * 设置在线语音合成和离线语音合成
     *
     * @param engineType
     */
    public void setEngineType(String engineType) {
        mEngineType = engineType;
        // 设置参数
        setParam();
    }


    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 初始化合成对象
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
        // 清空参数
        speechSynthesizer.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, voicerName);
            //设置合成语速
            speechSynthesizer.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
            //设置合成音调
            speechSynthesizer.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
            //设置合成音量
            speechSynthesizer.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
        } else {
            // 清空参数
            speechSynthesizer.setParameter(SpeechConstant.PARAMS, null);
            // 设置使用本地引擎
            speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置发音人资源路径
            speechSynthesizer.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath());
            // 设置发音人
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");

            // 设置语速
            speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");

            // 设置音调
            speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");

            // 设置音量
            speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");

            // 设置播放器音频流类型
            speechSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, "3");
//            speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
//            // 设置发音人资源路径
//            speechSynthesizer.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath());
//            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
//            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        speechSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
        // 设置播放合成音频打断音乐播放，默认为true
        speechSynthesizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        speechSynthesizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        speechSynthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                log("初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            log("开始播放");
        }

        @Override
        public void onSpeakPaused() {
            log("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            log("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            // 合成进度
            mPercentForBuffering = percent;

        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                if (mCallBackListener != null) {
                    mCallBackListener.speakCompleted(error);
                }
                log("播放完成");
            } else if (error != null) {
                log(error.getPlainDescription(true));
            }
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
    private OnSpeakCallBackListener mCallBackListener;  //声明接口对象

    public void setOnSpeakListener(OnSpeakCallBackListener callBackListener) {
        this.mCallBackListener = callBackListener;
    }

    /*
     *创建回调接口
     */
    public interface OnSpeakCallBackListener {
        void speakCompleted(SpeechError error);
    }

    // 获取离线发音人资源路径
    private String getResourcePath() {
        StringBuffer tempBuffer = new StringBuffer();
        // 合成通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/common.jet"));
        tempBuffer.append(";");
        // 发音人资源
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/xiaoyan.jet"));
        return tempBuffer.toString();
    }


    private void log(String s) {
        System.out.println("========msg=" + s);
    }


}
