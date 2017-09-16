/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.widget.webview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;



public class FastScrollWebView extends WebView {

    /**
     * Helper object that renders and controls the fast scroll thumb.
     */
    private WebViewFastScroller mFastScroller;

    /**
     * 构造函数声明
     * @param context Context
     */
    public FastScrollWebView(Context context) {
        super(context);
        mFastScroller = new WebViewFastScroller(context, this);
    }

    /**
     * 构造函数声明
     * @param context Context
     * @param attrs AttributeSet
     */
    public FastScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mFastScroller = new WebViewFastScroller(context, this);
    }

    /**
     * 构造函数声明
     * @param context Context
     * @param attrs AttributeSet
     * @param defStyle 样式
     */
    public FastScrollWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mFastScroller = new WebViewFastScroller(context, this);
    }

    /**
     * If fast scroll is visible, then don't draw the vertical scrollbar.
     * 
     * @hide
     */
    // @Override
    // protected boolean isVerticalScrollBarHidden() {
    // return mFastScroller != null && mFastScroller.isVisible();
    // }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mFastScroller.onScroll(this, t, getHeight(), getContentHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mFastScroller != null) {
            mFastScroller.onSizeChanged(w, h, oldw, oldh);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mFastScroller.onTouchEvent(ev)) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mFastScroller.draw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mFastScroller.onInterceptTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
