package com.sikefeng.chinaren.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;


public class CommomDialog {
    
    private static CommomDialog commomDialog;
    public static CommomDialog getInstance(){
         if (commomDialog==null){
             commomDialog =new CommomDialog();
         }
         return commomDialog;
    }

    private View layout;
    private AlertDialog dialog;
    private SparseArray<View> mViews;
      public void show(Context mcontext,int layoutID){
          this.mViews=new SparseArray<View>();
          AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
          this.layout = LayoutInflater.from(mcontext).inflate(layoutID, null);
          builder.setView(layout);
          dialog= builder.show();
      }

    /**
     * 关闭Dialog
     */
    public void dismiss(){
          if (dialog!=null){
              if (dialog.isShowing())
              dialog.dismiss();
          }
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
