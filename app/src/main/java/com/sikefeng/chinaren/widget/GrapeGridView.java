package com.sikefeng.chinaren.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;



public class GrapeGridView extends GridView {

	public GrapeGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GrapeGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GrapeGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	// ͨ������dispatchTouchEvent��������ֹ����
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			return true;// ��ֹGridview���л���
		}
		return super.dispatchTouchEvent(ev);
	}
}