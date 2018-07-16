package com.sikefeng.chinaren.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sikefeng.chinaren.R;

public class PreviewActivity extends AppCompatActivity {


    private SimpleDraweeView simpleDraweeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_preview);
        simpleDraweeView= (SimpleDraweeView) findViewById(R.id.draweeView);
        String path=this.getIntent().getStringExtra("path");
        simpleDraweeView.setImageURI(Uri.parse(path));
    }
}
