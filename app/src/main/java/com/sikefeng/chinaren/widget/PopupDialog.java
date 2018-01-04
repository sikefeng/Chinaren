package com.sikefeng.chinaren.widget;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Richard on 17/3/16.
 */

public class PopupDialog {

    private PopupWindow popWindow;
    private Context context;
    private SparseArray<View> mViews;
    private View layout;
    protected LayoutInflater inflater;
    public PopupDialog() {

    }

    public PopupDialog(Context context, int layoutID) {
        this.context = context;
        this.mViews=new SparseArray<View>();
        this.layout = LayoutInflater.from(context).inflate(layoutID, null);
        popWindow = new PopupWindow(layout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        popWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }
    public void showAtLocation(View parent, int gravity){
           if (popWindow==null)return;
           popWindow.showAtLocation(parent,gravity, 0, 0);
    }
    public void dismiss(){
           if (popWindow!=null&&popWindow.isShowing()){
               popWindow.dismiss();
           }
    }
    public void setAnimation(int animation){
        if (popWindow==null)return;
           popWindow.setAnimationStyle(animation);
    }
    public View getLayoutView(){
           return layout;
    }
    //通过viewId获取控件
    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if (view==null){
            view=layout.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }


}
