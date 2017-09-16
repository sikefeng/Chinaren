/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.sikefeng.chinaren.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class RatioImageView extends AppCompatImageView {
    /**
     * 横向高度
     */
    private int mHorizontalWeight;
    /**
     * 垂直高度
     */
    private int mVerticalWeight;
    /**
     * 线条
     */
    private int mBaseLine;
    /**
     * mSpecifiedWidth, mSpecifiedHeight
     */
    private int mSpecifiedWidth, mSpecifiedHeight;

    /**
     * 宽度
     */
    public static final int WIDTH = 0;
    /**
     * 高度
     */
    public static final int HEIGHT = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WIDTH, HEIGHT})
    public @interface Base {
    }

    /**
     * 构造函数
     *
     * @param context Context
     */
    public RatioImageView(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * 构造函数
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 构造函数
     * @param context Context
     * @param attrs AttributeSet
     * @param defStyleAttr 样式
     */
    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }*/

    /**
     * 初始化
     * @param context Context
     * @param attrs AttributeSet
     */
    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
            mHorizontalWeight = ta.getInt(R.styleable.RatioImageView_width_ratio, 1);
            mVerticalWeight = ta.getInt(R.styleable.RatioImageView_height_ratio, 1);
            mBaseLine = ta.getInt(R.styleable.RatioImageView_base, WIDTH);
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 0, desiredHeight = 0;
        if (mBaseLine == WIDTH) {
            if (mSpecifiedWidth != 0) {
                desiredWidth = mSpecifiedWidth;
            } else {
                desiredWidth = MeasureSpec.getSize(widthMeasureSpec);
            }
            desiredHeight = (int) ((float) desiredWidth / mHorizontalWeight * mVerticalWeight);
        }

        if (mBaseLine == HEIGHT) {
            if (mSpecifiedHeight != 0) {
                desiredHeight = mSpecifiedHeight;
            } else {
                desiredHeight = MeasureSpec.getSize(heightMeasureSpec);
            }
            desiredWidth = (int) ((float) desiredHeight / mVerticalWeight * mHorizontalWeight);
        }

        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    /**
     * 设定View的宽度,此时View的基准会变为{@link #WIDTH}
     * @param width 宽度
     */
    public void setWidth(int width) {
        mSpecifiedWidth = width;
        setBase(WIDTH);
    }

    /**
     * 设定View的高度,此时View的基准会变为{@link #HEIGHT}
     * @param height 高度
     */
    public void setHeight(int height) {
        mSpecifiedHeight = height;
        setBase(HEIGHT);
    }

    /**
     * 设置按比例布局的基准线
     * @param base 基本线条
     */
    public void setBase(@Base int base) {
        mBaseLine = base;
        requestLayout();
    }

    @Base
    /**获取当前的基准线*/
    public int getBase() {
        return mBaseLine;
    }
}
