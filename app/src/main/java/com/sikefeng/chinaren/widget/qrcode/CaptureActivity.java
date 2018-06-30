package com.sikefeng.chinaren.widget.qrcode;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.ui.activity.WebActivity;
import com.sikefeng.chinaren.utils.ToastUtils;
import com.sikefeng.chinaren.widget.PopupDialog;
import com.sikefeng.chinaren.widget.dialog.CommomDialog;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class CaptureActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title, tv_album;
    private CheckBox checkBox;
    public static final int REQUEST_IMAGE = 112;
    private final int MY_PERMISSIONS_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
        initView();

    }

    private void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
            //给左上角图标的左边加上一个返回的图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setSupportActionBar(mToolbar);
        //给左上角图标的左边加上一个返回的图标
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_album = (TextView) findViewById(R.id.tv_album);
        TextView tv_qrimage = (TextView) findViewById(R.id.tv_qrimage);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CodeUtils.isLightEnable(true);
//                    buttonView.setText("关灯");
                } else {
                    CodeUtils.isLightEnable(false);
//                    buttonView.setText("开灯");
                }
            }
        });
        toolbar_title.setText("扫一扫");
        tv_album.setOnClickListener(this);
        tv_qrimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomView(view,0);
            }
        });
        tv_qrimage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showBottomView(view,1);
                return true;
            }
        });
    }

    private void showBottomView(View view, int type) {
        PopupDialog popupDialog = new PopupDialog(this, R.layout.popup_qr_layout);
        popupDialog.setAnimation(android.R.style.Animation_InputMethod);
        popupDialog.showAtLocation(view, Gravity.CENTER);
        ImageView qrImage = popupDialog.getView(R.id.qrImage);
        if (type == 1) {
//            try {
//                Bitmap myBitmap = Glide.with(this)
//                        .load("http://img.baizhan.net/uploads/allimg/160112/16_160112154918_1.jpg")
//                        .asBitmap() //必须
//                        .centerCrop()
//                        .into(200, 200)
//                        .get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }

            Bitmap qrBitmap = QRCodeUtils.createQRImage("http://www.baidu.com", 400, 400);
            Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tou);
            Bitmap bitmap = QRCodeUtils.addQRLogo(qrBitmap, logoBitmap);
            qrImage.setImageBitmap(bitmap);
        } else {
            Bitmap qrBitmap = QRCodeUtils.createQrBitmap("http://www.baidu.com", 400, 400);
            qrImage.setImageBitmap(qrBitmap);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_album:
                CodeUtils.isLightEnable(false);
                checkBox.setChecked(false);
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST);
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_IMAGE);
                }
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_IMAGE);
                } else {
                    ToastUtils.showShort("授权失败");
                }
                return;
            }
        }
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//            Intent resultIntent = new Intent(CaptureActivity.this, RegisterActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
//            bundle.putString(CodeUtils.RESULT_STRING, result);
//            resultIntent.putExtras(bundle);
//            resultIntent.putExtra("data",result);
//            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
//            startActivity(resultIntent);
//            CaptureActivity.this.finish();
            Toast.makeText(CaptureActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
            if (result.contains("http") || result.contains("https")) {
                Intent intent = new Intent(CaptureActivity.this, WebActivity.class);
                intent.putExtra("URL", result);
                startActivity(intent);
                CaptureActivity.this.finish();
            }else {
                CommomDialog commomDialog = CommomDialog.getInstance();
                commomDialog.show(CaptureActivity.this, R.layout.dialog_scan_result);
                TextView tvResult=commomDialog.getView(R.id.tv_scan_result);
                tvResult.setText(result);
            }
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
            Toast.makeText(CaptureActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
        }
    };

    //相册二维码返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片

                    CodeUtils.analyzeBitmap(mBitmap, new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                            Intent resultIntent = new Intent(CaptureActivity.this, RegisterActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
//                            bundle.putString(CodeUtils.RESULT_STRING, result);
//                            resultIntent.putExtras(bundle);
//                            resultIntent.putExtra("data",result);
//                            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
//                            startActivity(resultIntent);
                            CaptureActivity.this.finish();
                            Toast.makeText(CaptureActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                            if (result.contains("http") || result.contains("https")) {
                                Intent intent = new Intent(CaptureActivity.this, WebActivity.class);
                                intent.putExtra("URL", result);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(CaptureActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });

                    if (mBitmap != null) {
                        mBitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startWebActivity(String url) {

    }
}
