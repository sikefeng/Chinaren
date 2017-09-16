/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */

package com.sikefeng.chinaren.widget.webview;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.utils.ResUtils;



class WebViewFastScroller {

    /**
     * 1000的值
     */
    private static final int VALUE1000 = 1000;
    /**
     * 10的值
     */
    private static final int VALUE10 = 10;
    /**
     * 1500的值
     */
    private static final int VALUE1500 = 1500;


    /**
     * Minimum number of pages to justify showing a fast scroll thumb
     */
    private static final int MIN_PAGES = 2;
    /**
     * Scroll thumb not showing
     */
    private static final int STATE_NONE = 0;
    /**
     * Not implemented yet - fade-in transition <br>
     * private static final int STATE_ENTER = 1; <br>
     * Scroll thumb visible and moving along with the scrollbar
     */
    private static final int STATE_VISIBLE = 2;
    /**
     * Scroll thumb being dragged by user
     */
    private static final int STATE_DRAGGING = 3;
    /**
     * Scroll thumb fading out due to inactivity timeout
     */
    private static final int STATE_EXIT = 4;

    /**
     * 缩略图资源
     */
    private Drawable mThumbDrawable;
    //private Drawable mOverlayDrawable;
    /**
     * 高度
     */
    private int mThumbH;
    /**
     * 宽度
     */
    private int mThumbW;
    /**
     * 页面位置
     */
    private int mThumbY;

    /**
     * 元素位置
     */
    private RectF mOverlayPos;
    /**
     * 元素大小
     */
    private static final int M_OVERLAY_SIZE = 104;

    /**
     * WebView定义
     */
    private WebView mView;
    /**
     * 判断滚动是否完成
     */
    private boolean mScrollCompleted;
    /**
     * 显示的元素
     */
//    private int mVisibleItem;
    /**
     * 定义Paint
     */
    private Paint mPaint;
    // private int mViewOffset;
    /**
     * 页面元素数量
     */
    private int mItemCount = -1;
    /**
     * 是否有长数组元素
     */
    private boolean mLongList;

    // private Object[] mSections;
    // private String mSectionText;
    // private boolean mDrawOverlay;
    /**
     * ScrollFade
     */
    private ScrollFade mScrollFade;

    /**
     * 当前页面状态
     */
    private int mState;

    /**
     * 定义Handler
     */
    private Handler mHandler = new Handler();

    // private SectionIndexer mSectionIndexer;

    /**
     * 是否改变界限
     */
    private boolean mChangedBounds;
    /**
     * 定义最小页
     */
    private int mMinPagesThreshold;

    /**
     * 构造函数声明
     *
     * @param context Context
     * @param view    WebView
     */
    WebViewFastScroller(Context context, WebView view) {
        mView = view;
        setFastscrollState(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
                "show_scroll_tab", true), MIN_PAGES);
        init(context);
    }

    /**
     * Enable or disable the fast-scroll state.
     *
     * @param enabled   The new fast-scroll state.
     * @param threshold 是否hold
     */
    public void setFastscrollState(boolean enabled, int threshold) {
        if (enabled) {
            mMinPagesThreshold = threshold;
        } else {
            // The user has disabled fast scroll, so make the threshold effectively infinite.
            mMinPagesThreshold = Integer.MAX_VALUE;
        }
        mItemCount = -1;
    }

    /**
     * 设置状态
     * @param state 状态
     */
    public void setState(int state) {
        switch (state) {
            case STATE_NONE:
                mHandler.removeCallbacks(mScrollFade);
                mView.invalidate();
                break;
            case STATE_VISIBLE:
                if (mState != STATE_VISIBLE) { // Optimization
                    resetThumbPos();
                }
                break;
                // Fall through
            case STATE_DRAGGING:
                mHandler.removeCallbacks(mScrollFade);
                break;
            case STATE_EXIT:
                int viewWidth = mView.getWidth();
                mView.invalidate(viewWidth - mThumbW, mThumbY + mView.getScrollY(), viewWidth, mThumbY + mThumbH
                        + mView.getScrollY());
                break;
            default:break;
        }
        mState = state;
    }

    /**
     * 获取状态
     * @return 状态值
     */
    public int getState() {
        return mState;
    }

    /**
     * 重置图片位置
     */
    private void resetThumbPos() {
        int viewWidth = mView.getWidth();
        // Bounds are always top right. Y coordinate get's translated during draw
        mThumbDrawable.setBounds(viewWidth - mThumbW, 0, viewWidth, mThumbH);
        mThumbDrawable.setAlpha(ScrollFade.ALPHA_MAX);
    }

    /**
     * 使用图片资源
     * @param drawable 图片
     */
    private void useThumbDrawable(Drawable drawable) {
        mThumbDrawable = drawable;

        mThumbH = drawable.getIntrinsicHeight();
        mThumbW = (int) (mThumbH * 0.75);

        mChangedBounds = true;
    }

    /**
     * 初始化配置WEBVIEW
     * @param context Context
     */
    private void init(Context context) {
        // Get both the scrollbar states drawables
        useThumbDrawable(ResUtils.getDrawable(R.mipmap.fast_scroller));

        //mOverlayDrawable = res.getDrawable(R.mipmap.menu_submenu_background);

        mScrollCompleted = true;

        mOverlayPos = new RectF();
        mScrollFade = new ScrollFade();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(M_OVERLAY_SIZE / 2);
        TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.textColorPrimary});
        ColorStateList textColor = ta.getColorStateList(ta.getIndex(0));
        int textColorNormal = textColor.getDefaultColor();
        mPaint.setColor(textColorNormal);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mState = STATE_NONE;
    }

    /**
     * 停止加载
     */
    void stop() {
        setState(STATE_NONE);
    }

    /**
     * 是否显示
     * @return boolean
     */
    boolean isVisible() {
        return !(mState == STATE_NONE);
    }

    /**
     * 制作画布
     * @param canvas Canvas
     */
    public void draw(Canvas canvas) {

        if (mState == STATE_NONE || mMinPagesThreshold >= Integer.MAX_VALUE) {
            // No need to draw anything
            return;
        }

        mThumbY = Math.round(((mView.getHeight() - mThumbH) * mView.getScrollY())
                / (mView.getContentHeight() * mView.getScale() - mView.getHeight()));

        final int _Y = mThumbY + mView.getScrollY();
        final int _X = mView.getScrollX();
        final int _VIEWWIDTH = mView.getWidth();
        ScrollFade scrollFade = mScrollFade;

        int alpha = -1;
        if (mState == STATE_EXIT) {
            alpha = scrollFade.getAlpha();
            if (alpha < ScrollFade.ALPHA_MAX / 2) {
                mThumbDrawable.setAlpha(alpha * 2);
            }
            int left = _VIEWWIDTH - (mThumbW * alpha) / ScrollFade.ALPHA_MAX;
            mThumbDrawable.setBounds(left, 0, _VIEWWIDTH, mThumbH);
            mChangedBounds = true;
        }

        canvas.translate(_X, _Y);
        mThumbDrawable.draw(canvas);
        canvas.translate(-_X, -_Y);

        // If user is dragging the scroll bar, draw the alphabet overlay
        // if (mState == STATE_DRAGGING && mDrawOverlay) {
        // mOverlayDrawable.draw(canvas);
        // final Paint paint = mPaint;
        // float descent = paint.descent();
        // final RectF rectF = mOverlayPos;
        // canvas.drawText(mSectionText, (int) (rectF.left + rectF.right) / 2, (int) (rectF.bottom + rectF.top) / 2
        // + mOverlaySize / 4 - descent, paint);
        // } else
        if (mState == STATE_EXIT) {
            if (alpha == 0) { // Done with exit
                setState(STATE_NONE);
            } else {
                mView.invalidate(_VIEWWIDTH - mThumbW, _Y, _VIEWWIDTH, _Y + mThumbH);
            }
        }
    }

    /**
     * 监听元素大小改变
     * @param w 宽度
     * @param h 高度
     * @param oldw 原来宽度
     * @param oldh 原来高度
     */
    void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mThumbDrawable != null) {
            mThumbDrawable.setBounds(w - mThumbW, 0, w, mThumbH);
        }
        int msize = (w - M_OVERLAY_SIZE) / 2;
        int mtop =  h / VALUE10;
        RectF pos = mOverlayPos;
        pos.left = msize;
        pos.right = pos.left + M_OVERLAY_SIZE;
        pos.top = mtop; // 10% from top
        pos.bottom = pos.top + M_OVERLAY_SIZE;
        /*if (mOverlayDrawable != null) {
            mOverlayDrawable.setBounds((int) pos.left, (int) pos.top, (int) pos.right, (int) pos.bottom);
        }*/
    }

    /**
     * 监听滚动条
     * @param view 当前视图
     * @param firstVisibleItem 第一个显示的项目
     * @param visibleItemCount 显示的项目数量
     * @param totalItemCount 所有项目数量
     */
    void onScroll(View view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // Are there enough pages to require fast scroll? Recompute only if total count changes
        if (mItemCount != totalItemCount && visibleItemCount > 0) {
            mItemCount = totalItemCount;
            mLongList = mItemCount / visibleItemCount >= mMinPagesThreshold;
        }
        if (!mLongList) {
            if (mState != STATE_NONE) {
                setState(STATE_NONE);
            }
            return;
        }
        if (totalItemCount - visibleItemCount > 0 && mState != STATE_DRAGGING) {
            mThumbY = ((mView.getHeight() - mThumbH) * firstVisibleItem) / (totalItemCount - visibleItemCount);
            if (mChangedBounds) {
                resetThumbPos();
                mChangedBounds = false;
            }
        }
        mScrollCompleted = true;
//        if (firstVisibleItem == mVisibleItem) {
//            // return;
//        }
//        mVisibleItem = firstVisibleItem;
        if (mState != STATE_DRAGGING) {
            setState(STATE_VISIBLE);
            mHandler.postDelayed(mScrollFade, VALUE1500);
        }
    }

    /**
     * 滚动条位置
     * @param position 位置
     */
    private void scrollTo(float position) {
        mScrollCompleted = false;
        float scrollableHeight = mView.getContentHeight() * mView.getScale() - mView.getHeight();
        float scrollY = position * scrollableHeight;

        mView.scrollTo(mView.getScrollX(), Math.round(scrollY));
    }

    /**
     * 取消绘制
     */
    private void cancelFling() {
        // Cancel the list fling
        MotionEvent cancelFling = MotionEvent.obtain(0, 0, MotionEvent.ACTION_CANCEL, 0, 0, 0);
        mView.onTouchEvent(cancelFling);
        cancelFling.recycle();
    }

    /**
     * 监听触发事件
     * @param ev 监听事件
     * @return true有触发，false没有
     */
    boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mState > STATE_NONE && ev.getAction() == MotionEvent.ACTION_DOWN) {
            if ((ev.getX() > (mView.getWidth() - mThumbW)) && (ev.getY() >= mThumbY)
                    && (ev.getY() <= (mThumbY + mThumbH))) {
                setState(STATE_DRAGGING);
                return true;
            }
        }
        return false;
    }

    /**
     * 监听触发事件
     * @param me 监听事件
     * @return true为有触发，false无
     */
    boolean onTouchEvent(MotionEvent me) {
        if (mState == STATE_NONE) {
            return false;
        }
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            if ((me.getX() > (mView.getWidth() - mThumbW)) && (me.getY() >= mThumbY)
                    && (me.getY() <= (mThumbY + mThumbH))) {

                setState(STATE_DRAGGING);
                cancelFling();
                return true;
            }
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
            if (mState == STATE_DRAGGING) {
                setState(STATE_VISIBLE);
                final Handler _HANDLER = mHandler;
                _HANDLER.removeCallbacks(mScrollFade);
                _HANDLER.postDelayed(mScrollFade, VALUE1000);
                return true;
            }
        } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
            if (mState == STATE_DRAGGING) {
                final int _VIEWHEIGHT = mView.getHeight();
                // Jitter
                int newThumbY = (int) me.getY() - mThumbH + VALUE10;
                if (newThumbY < 0) {
                    newThumbY = 0;
                } else if (newThumbY + mThumbH > _VIEWHEIGHT) {
                    newThumbY = _VIEWHEIGHT - mThumbH;
                }
                if (Math.abs(mThumbY - newThumbY) < 2) {
                    return true;
                }
                mThumbY = newThumbY;
                // If the previous scrollTo is still pending
                if (mScrollCompleted) {
                    scrollTo((float) mThumbY / (_VIEWHEIGHT - mThumbH));
                }
                return true;
            }
        }
        return false;
    }

    public class ScrollFade implements Runnable {
        /**
         * 开始时间
         */
        private long mStartTime;
        /**
         * mFadeDuration
         */
        private long mFadeDuration;
        /**
         * 最大透明度
         */
        private static final int ALPHA_MAX = 208;
        /**
         * FADE_DURATION
         */
        private static final long FADE_DURATION = 200;

        /**
         * 开始滚动
         */
        void startFade() {
            mFadeDuration = FADE_DURATION;
            mStartTime = SystemClock.uptimeMillis();
            setState(STATE_EXIT);
        }

        /**
         * 获取透明值
         * @return int
         */
        int getAlpha() {
            if (getState() != STATE_EXIT) {
                return ALPHA_MAX;
            }
            int alpha;
            long now = SystemClock.uptimeMillis();
            if (now > mStartTime + mFadeDuration) {
                alpha = 0;
            } else {
                alpha = (int) (ALPHA_MAX - ((now - mStartTime) * ALPHA_MAX) / mFadeDuration);
            }
            return alpha;
        }

        /**
         * 线程开始
         */
        public void run() {
            if (getState() != STATE_EXIT) {
                startFade();
                return;
            }

            if (getAlpha() > 0) {
                mView.invalidate();
            } else {
                setState(STATE_NONE);
            }
        }
    }
}
