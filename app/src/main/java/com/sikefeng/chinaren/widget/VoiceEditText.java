package com.sikefeng.chinaren.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

import com.iflytek.cloud.SpeechError;
import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.utils.speech.SpeechRecognizerEditText;

import static android.content.Context.VIBRATOR_SERVICE;


public class VoiceEditText extends android.support.v7.widget.AppCompatEditText implements
        OnFocusChangeListener, TextWatcher ,SpeechRecognizerEditText.OnSpeechResultListener ,PopupDialog.OnDismissListener {


//    if (isChecked) {
//        // 显示密码
//        password_edit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//    }
//    else {
//        // 隐藏密码
//        password_edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//    }

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    private Context mContext;

    /**
     * 功能描述：ClearEditText构造方法
     * <br>创建时间： 2017-07-27 18:10:05
     *
     * @param context 上下文
     * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a>
     */
    public VoiceEditText(Context context) {
        this(context, null);
        mContext=context;
    }

    /**
     * 功能描述：这里构造方法也很重要，不加这个很多属性不能再XML里面定义
     * <br>创建时间： 2017-07-27 18:11:47
     *
     * @param context 上下文
     * @param attrs   属性
     * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a>
     */
    public VoiceEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        mContext=context;
    }

    /**
     * 功能描述：ClearEditText构造方法
     * <br>创建时间： 2017-07-27 18:13:16
     *
     * @param context  上下文
     * @param attrs    属性
     * @param defStyle 样式
     * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a>
     */
    public VoiceEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        init();
    }

    /**
     * 功能描述：设置清除图片
     * <br>创建时间： 2017-07-27 18:12:16
     *
     * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a>
     */
    private void init() {
//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//	throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.drawable.voice_selector);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
//默认设置隐藏图标
        setClearIconVisible(false);
//设置焦点改变的监听
        setOnFocusChangeListener(this);
//设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
//        speechRecognizerEditText = SpeechRecognizerEditText.getInstance(mContext);
        speechRecognizerEditText = new SpeechRecognizerEditText(mContext);
        speechRecognizerEditText.setOnSpeechResultListener(this);

    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.clearFocus();
                    Vibrator vibrator = (Vibrator) mContext.getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(200L);
                    showVoiceListener();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * three_num
     */
    private final int threeNum = 3;

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible 是否显示清除图标
     */
    protected void setClearIconVisible(boolean visible) {
        visible=true;
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[threeNum]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * fiveNum
     */
    private final int fiveNum = 5;

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(fiveNum));
    }


    /**
     * 晃动动画
     *
     * 1秒钟晃动多少下
     *
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //保证光标始终在最后面
        if (selStart == selEnd) {//防止不能多选
            setSelection(getText().length());
        }

    }
    private SpeechRecognizerEditText speechRecognizerEditText;
    private PopupDialog popupDialog=null;
    private EditText etResulting;

    public void showVoiceListener(){
        if (popupDialog==null){
            popupDialog= new PopupDialog(mContext, R.layout.popup_voicer);
        }
        if (popupDialog.isShowing()){
            popupDialog.dismiss();
            return;
        }
        popupDialog.setAnimation(android.R.style.Animation_InputMethod);
        popupDialog.showAtLocation(this, Gravity.BOTTOM);
        etResulting=popupDialog.getView(R.id.tv_result);
        etResulting.setText("");
        speechRecognizerEditText.start();
        ImageView imageView1 = popupDialog.getView(R.id.ivLeft);
        ImageView imageView2 = popupDialog.getView(R.id.ivRight);

        AnimationDrawable animationDrawable1 = (AnimationDrawable) imageView1.getDrawable();
        AnimationDrawable animationDrawable2 = (AnimationDrawable) imageView2.getDrawable();
        animationDrawable1.start();
        animationDrawable2.start();
        popupDialog.setOnDismissListener(this);
    }


    @Override
    public void startSpeech() {
//          showVoiceListener();
    }

    @Override
    public void endOfSpeech() {
         popupDialog.dismiss();
    }

    @Override
    public void speechError(SpeechError error) {
         popupDialog.dismiss();
    }

    @Override
    public void onListenering(String result) {
            etResulting.append(result);
    }

    @Override
    public void onListenerResult(String result) {
            this.setText(this.getText()+result+"。");
    }

    @Override
    public void dissmiss() {
          if (speechRecognizerEditText.isListening()){
              speechRecognizerEditText.stop();
          }
    }
}
