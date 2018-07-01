package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.iflytek.cloud.SpeechError;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.utils.speech.SpeechRecognizerEditText;
import com.sikefeng.chinaren.utils.speech.SpeechRecognizerUtils;


import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class NewNoteActivity extends AppCompatActivity {
    private GifImageView giv1;//由按键控制播放的gif
    private GifDrawable gifDrawable;//资源对象
    private Context mContext;
    private EditText et_content;
    private SpeechRecognizerEditText speechRecognizerEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mContext=this;
        et_content= (EditText) findViewById(R.id.et_content);
        speechRecognizerEditText=SpeechRecognizerEditText.getInstance(mContext);
        initGif();
        gifDrawable.stop();
        giv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        gifDrawable.reset();
                        gifDrawable.start();
                        speechRecognizerEditText.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        gifDrawable.seekTo(0);
                        gifDrawable.stop();
                        speechRecognizerEditText.stop();
                        break;
                }
                return true;
            }
        });
        SpeechRecognizerEditText.getInstance(mContext).setOnSpeechResultListener(new SpeechRecognizerEditText.OnSpeechResultListener() {
            @Override
            public void onListenerResult(String result) {

            }

            @Override
            public void endOfSpeech() {

            }

            @Override
            public void speechError(SpeechError error) {

            }

            @Override
            public void onListenering(String result) {
                et_content.append(result);
                et_content.setSelection(result.length());
            }
        });
    }


    //初始化由按键控制播放的gif
    private void initGif() {
        giv1 = (GifImageView) findViewById(R.id.giv1);
        //这里控制播放的对象实际是gifDrawable
        try {
            gifDrawable = new GifDrawable(getResources(), R.drawable.talk);
            giv1.setImageDrawable(gifDrawable);//这里是实际决定资源的地方，优先级高于xml文件的资源定义
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
