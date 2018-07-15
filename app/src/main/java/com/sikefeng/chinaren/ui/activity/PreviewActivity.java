package com.sikefeng.chinaren.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sikefeng.chinaren.R;

public class PreviewActivity extends AppCompatActivity {


    private SimpleDraweeView simpleDraweeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        simpleDraweeView= (SimpleDraweeView) findViewById(R.id.draweeView);
        String path=this.getIntent().getStringExtra("path");
        simpleDraweeView.setImageURI(Uri.parse(path));
    }
}
