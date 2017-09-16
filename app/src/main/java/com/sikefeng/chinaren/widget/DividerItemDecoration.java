/**
 * Copyright (C) 2014-2017 <a href="http://www.sikefeng.com>">sikefeng</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sikefeng.chinaren.R;


public final class DividerItemDecoration extends RecyclerView.ItemDecoration {

	/**
	 * 定义HORIZONTAL_LIST
	 */
	public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
	/**
	 * 定义VERTICAL_LIST
	 */
	public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

	/**
	 * 声明Drawable
	 */
	private Drawable mDivider;
	/**
	 * 声明旋转
	 */
	private int mOrientation;

	/**
	 * 线条默认宽度
	 */
	private static final int LINE_DEFAULT_WIDTH = 8;
	/**
	 * 线条宽度
	 */
	private int lineWidth = LINE_DEFAULT_WIDTH;


	/**
	 * 1px垂直高度  带颜色
	 * @param context Context
	 * @return DividerItemDecoration
	 */
	public static DividerItemDecoration get1pxDividerV(Context context){
		return new DividerItemDecoration(context, VERTICAL_LIST, R.drawable.divider, 1);
	}

	/**
	 * 默认垂直高度  透明
	 * @param context Context
	 * @return DividerItemDecoration
	 */
	public static DividerItemDecoration getDefaultDividerTransV(Context context){
		return new DividerItemDecoration(context, VERTICAL_LIST, R.drawable.divider_trans, dip2px(context, LINE_DEFAULT_WIDTH));
	}

	/**
	 * 垂直高度  透明
	 * @param context Context
	 * @param orientation 方向
	 * @param drawableId 资源ID
	 * @param lineWidth 线条宽度
	 */
	private DividerItemDecoration(Context context, int orientation, int drawableId, int lineWidth) {
		mDivider = context.getResources().getDrawable(drawableId);
		setOrientation(orientation);
		this.lineWidth = lineWidth;
	}

	/**
	 * 设置方向，例如垂直还是横向
	 * @param orientation 方向
	 */
	private void setOrientation(int orientation) {
		if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
			throw new IllegalArgumentException("invalid orientation");
		}
		mOrientation = orientation;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent) {
		if (mOrientation == VERTICAL_LIST) {
			drawVertical(c, parent);
		} else {
			drawHorizontal(c, parent);
		}
	}

	/**
	 * 绘制垂直元素
	 * @param c Canvas
	 * @param parent RecyclerView
	 */
	public void drawVertical(Canvas c, RecyclerView parent) {
		final int _LEFT = parent.getPaddingLeft();
		final int _RIGHT = parent.getWidth() - parent.getPaddingRight();

		final int _CHILDCOUNT = parent.getChildCount();
		for (int i = 0; i < _CHILDCOUNT; i++) {
			final View _CHILD = parent.getChildAt(i);
			//RecyclerView v = new RecyclerView(parent.getContext());
			final RecyclerView.LayoutParams _PARAMS = (RecyclerView.LayoutParams) _CHILD
					.getLayoutParams();
			final int _TOP = _CHILD.getBottom() + _PARAMS.bottomMargin;
			final int _BOTTOM = _TOP + lineWidth;
			mDivider.setBounds(_LEFT, _TOP, _RIGHT, _BOTTOM);
			mDivider.draw(c);
		}
	}

	/**
	 * 绘制横向对象
	 * @param c Canvas
	 * @param parent RecyclerView
	 */
	public void drawHorizontal(Canvas c, RecyclerView parent) {
		final int _TOP = parent.getPaddingTop();
		final int _BOTTOM = parent.getHeight() - parent.getPaddingBottom();

		final int _CHILDCOUNT = parent.getChildCount();
		for (int i = 0; i < _CHILDCOUNT; i++) {
			final View _CHILD = parent.getChildAt(i);
			final RecyclerView.LayoutParams _PARAMS = (RecyclerView.LayoutParams) _CHILD
					.getLayoutParams();
			final int _LEFT = _CHILD.getRight() + _PARAMS.rightMargin;
			final int _RIGHT = _LEFT + lineWidth;
			mDivider.setBounds(_LEFT, _TOP, _RIGHT, _BOTTOM);
			mDivider.draw(c);
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
		if (mOrientation == VERTICAL_LIST) {
			outRect.set(0, 0, 0, lineWidth);
		} else {
			outRect.set(0, 0, lineWidth, 0);
		}
	}

	/**
	 * DIp转PX
	 * @param context Context
	 * @param dipValue float
	 * @return int
	 */
	private static int dip2px(Context context, float dipValue) {
		final float _SCALE = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * _SCALE + 0.5f);
	}
}
