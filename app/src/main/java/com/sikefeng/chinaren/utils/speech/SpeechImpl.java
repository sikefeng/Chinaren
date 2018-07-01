package com.sikefeng.chinaren.utils.speech;

/**
 * Created by Administrator on 2018/3/15.
 */

public interface SpeechImpl {

    void startSpeak(String content);
    void pauseSpeak();
    void resumeSpeak();
    void stopSpeak();
    boolean isPlaying();
    void setSpeaker(String speaker);

}
