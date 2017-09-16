/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class BounceBackViewPager extends ViewPager {

    /**
     * 持续时间
     */
    private static final int DURATION = 150;

    /**
     * 用来记录初始位置
     */
    private Rect mRect = new Rect();
    /**
     * 处理Viewpager默认情况标志
     */
    private boolean handleDefault = true;
    /**
     * 向前的位置
     */
    private float preX = 0f;
    /**
     * 摩擦系数
     */
    private static final float RATIO = 0.8f;

    /**
     * 声明构造函数
     *
     * @param context Context
     */
    public BounceBackViewPager(Context context) {
        super(context);
    }

    /**
     * 声明构造函数
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public BounceBackViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * mIsDisallowIntercept
     */
    private boolean mIsDisallowIntercept = false;

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        mIsDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if (ev.getPointerCount() > 1 && mIsDisallowIntercept) {
            requestDisallowInterceptTouchEvent(false);
            boolean handled = super.dispatchTouchEvent(ev);
            requestDisallowInterceptTouchEvent(true);
            return handled;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = ev.getX();//记录起点
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (actionMove(ev)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                onTouchActionUp();
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 移动操作
     *
     * @param ev MotionEvent
     * @return boolean
     */
    private boolean actionMove(MotionEvent ev) {
        final float _NOWX = ev.getX();
        //偏移量
        final float _OFFSET = preX - _NOWX;
        preX = _NOWX;
        if (getAdapter().getCount() > 0) {
            if (handleDefault) {//手指滑动的距离大于设定值
                whetherIsCollided(_OFFSET);
            } else {//这种情况是已经出现缓冲区域了，手指慢慢恢复的情况
                scrollBy((int) (_OFFSET * RATIO), 0);
            }
        } else {
            handleDefault = true;
        }
        return !handleDefault;
    }

    /**
     * whetherIsCollided
     *
     * @param offset 位置
     */
    private void whetherIsCollided(float offset) {
        if (mRect.isEmpty()) {
            mRect.set(getLeft(), getTop(), getRight(), getBottom());
        }
        handleDefault = false;
        scrollBy((int) (offset * RATIO), 0);
    }

    /**
     * 监听向上滑动事件
     */
    private void onTouchActionUp() {
        if (!mRect.isEmpty()) {
            recoveryPosition();
        }
    }

    /**
     * recoveryPosition
     */
    private void recoveryPosition() {
        TranslateAnimation ta = new TranslateAnimation(getLeft(), mRect.left, 0, 0);
        ta.setDuration(DURATION);
        ta.setInterpolator(new DecelerateInterpolator());
        startAnimation(ta);
        layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
        handleDefault = true;
    }
}
