package com.sikefeng.chinaren.widget.camera;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.sikefeng.chinaren.R;

import static com.sikefeng.chinaren.utils.Constants.VALUE_1000;
import static com.sikefeng.chinaren.utils.Constants.VALUE_3500;

public class FocusImageView extends AppCompatImageView {
    public static final String TAG = "FocusImageView";
    /**
     * NO_ID
     */
    private static final int NO_ID = -1;
    /**
     * mFocusImg
     */
    private int mFocusImg = NO_ID;
    /**
     * mFocusSucceedImg
     */
    private int mFocusSucceedImg = NO_ID;
    /**
     * mFocusFailedImg
     */
    private int mFocusFailedImg = NO_ID;
    /**
     * Animation
     */
    private Animation mAnimation;
    /**
     * Handler
     */
    private Handler mHandler;

    /**
     * FocusImageView
     * @param context Context
     */
    public FocusImageView(Context context) {
        super(context);
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.focusview_show);
        setVisibility(View.GONE);
        mHandler = new Handler();
    }

    /**
     * FocusImageView
     * @param context Context
     * @param attrs AttributeSet
     */
    public FocusImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.focusview_show);
        mHandler = new Handler();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FocusImageView);
        mFocusImg = a.getResourceId(R.styleable.FocusImageView_focus_focusing_id, NO_ID);
        mFocusSucceedImg = a.getResourceId(R.styleable.FocusImageView_focus_success_id, NO_ID);
        mFocusFailedImg = a.getResourceId(R.styleable.FocusImageView_focus_fail_id, NO_ID);
        a.recycle();

        //聚焦图片不能为空
        if (mFocusImg == NO_ID || mFocusSucceedImg == NO_ID || mFocusFailedImg == NO_ID) {
            throw new RuntimeException("mFocusImg,mFocusSucceedImg,mFocusFailedImg is null");
        }
    }

    /**
     * 显示聚焦图案
     *
     * @param point Point
     */
    public void startFocus(Point point) {
        if (mFocusImg == NO_ID || mFocusSucceedImg == NO_ID || mFocusFailedImg == NO_ID) {
            throw new RuntimeException("focus image is null");
        }
        //根据触摸的坐标设置聚焦图案的位置
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.topMargin = point.y - getMeasuredHeight() / 2;
        params.leftMargin = point.x - getMeasuredWidth() / 2;
        setLayoutParams(params);
        //设置控件可见，并开始动画
        setVisibility(View.VISIBLE);
        setImageResource(mFocusImg);
        startAnimation(mAnimation);
        //3秒后隐藏View。在此处设置是由于可能聚焦事件可能不触发。
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        }, VALUE_3500);
    }

    /**
     * 聚焦成功回调
     */
    public void onFocusSuccess() {
        setImageResource(mFocusSucceedImg);
        //移除在startFocus中设置的callback，1秒后隐藏该控件
        mHandler.removeCallbacks(null, null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        }, VALUE_1000);

    }

    /**
     * 聚焦失败回调
     */
    public void onFocusFailed() {
        setImageResource(mFocusFailedImg);
        //移除在startFocus中设置的callback，1秒后隐藏该控件
        mHandler.removeCallbacks(null, null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        }, VALUE_1000);
    }

    /**
     * 设置开始聚焦时的图片
     *
     * @param focus int
     */
    public void setFocusImg(int focus) {
        this.mFocusImg = focus;
    }

    /**
     * 设置聚焦成功显示的图片
     *
     * @param focusSucceed int
     */
    public void setFocusSucceedImg(int focusSucceed) {
        this.mFocusSucceedImg = focusSucceed;
    }
}
