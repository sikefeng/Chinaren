package com.sikefeng.chinaren.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.widget.headclip.ClipViewLayout;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static com.sikefeng.chinaren.utils.PickerImageUtils.FILE_DIR;

/**
 * Created by Horrarndoo on 2017/9/25.
 * <p>
 * 设置头像Activity
 */

public class HeadSettingActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String RESULT_PATH = "crop_image";
    ClipViewLayout cvlRect;
    String file_path="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_setting);
        cvlRect= (ClipViewLayout) findViewById(R.id.cvl_rect);
        cvlRect.setImageSrc(Uri.parse(this.getIntent().getStringExtra("path")));
        TextView tv_cancel= (TextView) findViewById(R.id.tv_cancel);
        TextView tv_ok= (TextView) findViewById(R.id.tv_ok);
        tv_cancel.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }




    /**
     * 生成Uri
     */
    private String generateUri() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap = cvlRect.clip();
        File file=new File(FILE_DIR,  "abc.jpg");
        Uri mSaveUri = Uri.fromFile(file);
        file_path=file.getAbsolutePath();
        System.out.println("kkkkkkkkkkkkkkk======"+file_path);
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return file_path;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                onBackPressed();
                break;
            case R.id.tv_ok:
                Intent intent = new Intent();
                intent.putExtra(RESULT_PATH, generateUri());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }



}
