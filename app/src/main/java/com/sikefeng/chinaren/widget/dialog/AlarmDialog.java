package com.sikefeng.chinaren.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sikefeng.chinaren.R;


public class AlarmDialog extends AlertDialog implements View.OnClickListener {
    private OnDialogCallbackListener mListener = null;
    private TextView confirm, cancel;
    private TextView title, tv_time,tv_date,tv_week;

    public AlarmDialog(Context context, int theme, OnDialogCallbackListener listener) {
        super(context, theme);
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alarm);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_week = (TextView) findViewById(R.id.tv_week);
        title = (TextView) findViewById(R.id.title);
        confirm = (TextView) findViewById(R.id.confirm);
        cancel = (TextView) findViewById(R.id.cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        super.setCanceledOnTouchOutside(false);
        super.setCancelable(false); //不可以按返回键取消Dialog
    }

    public void setWeekText(String str) {
        if (this.tv_week != null) {
            this.tv_week.setText(str);
        }
    }

    public void setDateText(String str) {
        if (this.tv_date != null) {
            this.tv_date.setText(str);
        }
    }

    public void setTimeText(String str) {
        if (this.tv_time != null) {
            this.tv_time.setText(str);
        }
    }

    public void setTitleText(String str) {
        if (this.title != null) {
            this.title.setText(str);
        }
    }

    public interface OnDialogCallbackListener {
        void confirm();// 确认

        void cancel();// 取消
    }

    public OnDialogCallbackListener getListener() {
        return mListener;
    }

    public void setListener(OnDialogCallbackListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                mListener.confirm();
                break;
            case R.id.cancel:
                mListener.cancel();
                break;
        }
        //移除对话框
        dismiss();
    }

}
