package com.sikefeng.chinaren.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.ui.activity.HeadSettingActivity;
import com.sikefeng.chinaren.widget.PopupDialog;

import java.io.File;
import java.text.SimpleDateFormat;

import static android.app.Activity.RESULT_OK;
import static com.sikefeng.chinaren.ui.activity.HeadSettingActivity.RESULT_PATH;

/**
 * Created by Administrator on 2017/5/30.
 */

public class PickerImageUtils {

    private Activity context;
    private PopupDialog popupDialog;
    public static final int PHOTOZOOM = 9001;//从相册选择图片
    public static final int PHOTOTAKE = 9002; //拍照
    public static final int CROP_RESULT_CODE = 9003; //图片裁剪
    private final int PERMISSION_FILE = 9004;//Android6.0文件权限申请
    private final int PERMISSION_CAMERA = 9005;//Android6.0拍照权限申请

    public static String FILE_DIR="/sdcard/aaa/";
    private String result_path="";
    private boolean isCropImage=false;

    public PickerImageUtils(Activity context) {
        this.context = context;
    }
    public PickerImageUtils(Activity context, boolean isCropImage) {
        this.context = context;
        this.isCropImage = isCropImage;

        new Thread(new Runnable() {
            @Override
            public void run() {
                File file=new File(FILE_DIR);
                if (file.exists()){
                    deleteAllFiles(file);
                }
            }
        }).start();
    }
    //删除某目录下所有文件夹及文件
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
    /**
     * 图片选择的监听回调
     */
    private OnPictureSelectedListener mOnPictureSelectedListener;



    public void selectPhoto(View view) {
        if (Build.VERSION.SDK_INT > 22) {
            // 没有权限。
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                }
                // 申请授权。
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_FILE);
                return;
            }
        }
        File file = new File(FILE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        popupDialog = new PopupDialog(context, R.layout.popup_select_picture);
        popupDialog.setAnimation(android.R.style.Animation_InputMethod);
        popupDialog.showAtLocation(view, Gravity.CENTER);
        View layout = popupDialog.getLayoutView();
        TextView tv_photograph = popupDialog.getView(R.id.tv_takephoto);
        tv_photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDialog.dismiss();
                startCapture();
            }
        });
        TextView tv_albums = popupDialog.getView(R.id.tv_albums);
        tv_albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDialog.dismiss();
                //从相册获取图片
                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                context.startActivityForResult(intent, PHOTOZOOM);
//                  startAlbum();
            }
        });
        TextView tv_cancel = popupDialog.getView(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDialog.dismiss();
            }
        });

    }
    //通过拍照获取图片
    private void startCapture() {
        //通过拍照获取图片
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //如果没有授权，则请求授权
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
                return;
            }
        }
        String time = new SimpleDateFormat("yyMMddhhmmss").format(System.currentTimeMillis()) + ".JPG";
        result_path= FILE_DIR + time;
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(result_path));
        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        context.startActivityForResult(openCameraIntent, PHOTOTAKE);
    }
    //从相册获取图片
    private void startAlbum() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            context.startActivityForResult(intent, PHOTOZOOM);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                context.startActivityForResult(intent, PHOTOZOOM);
            } catch (Exception e2) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    public void setOnActivityResult(int requestCode, int resultCode, Intent data){
        Uri uri = null;
        switch (requestCode) {
            case PHOTOTAKE:  //拍照返回结果
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (isCropImage){
//                    ClipImageActivity.startActivity(context, result_path, CROP_RESULT_CODE);
                    Intent sendIntent=new Intent(context, HeadSettingActivity.class);
                    sendIntent.putExtra("path",result_path);
                    context.startActivityForResult(sendIntent,CROP_RESULT_CODE);
                    return;
                }
                if (mOnPictureSelectedListener!=null){
                    mOnPictureSelectedListener.onPictureSelected(result_path);
                }
                break;
            case PHOTOZOOM:  //相册选择图片返回结果
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (data == null) {
                    return;
                }
                uri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = context.managedQuery(uri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result_path = cursor.getString(column_index);
                if (isCropImage){
                    Intent sendIntent=new Intent(context, HeadSettingActivity.class);
                    sendIntent.putExtra("path",result_path);
                    context.startActivityForResult(sendIntent,CROP_RESULT_CODE);
                    return;
                }
                if (mOnPictureSelectedListener!=null){
                    mOnPictureSelectedListener.onPictureSelected(result_path);
                }
                break;
            case CROP_RESULT_CODE: //图片裁剪结果
                if (resultCode != RESULT_OK) {
                    return;
                }
                result_path= data.getStringExtra(RESULT_PATH);
                if (mOnPictureSelectedListener!=null){
                    mOnPictureSelectedListener.onPictureSelected(result_path);
                }
                break;
        }
    }

    //设置请求权限
    public void setOnRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if (requestCode == PERMISSION_FILE) { //文件请求权限
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("------------------允许文件访问");
            }
        } else if (requestCode == PERMISSION_CAMERA) { //拍照请求权限
            //grantResults授权结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //成功开启摄像头
                String time = new SimpleDateFormat("yyMMddhhmmss").format(System.currentTimeMillis()) + ".JPG";
                result_path= FILE_DIR + time;
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = Uri.fromFile(new File(result_path));
                openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                context.startActivityForResult(openCameraIntent, PHOTOTAKE);
            }
        }
    }
    /**
     * 设置图片选择的回调监听
     *
     * @param onPictureSelectedListener
     */
    public void setOnPictureSelectedListener(OnPictureSelectedListener onPictureSelectedListener) {
        this.mOnPictureSelectedListener = onPictureSelectedListener;
    }
    /**
     * 图片选择的回调接口
     */
    public interface OnPictureSelectedListener {
        /**
         * 图片选择的监听回调
         *
         * @param result_path
         */
        void onPictureSelected(String result_path);
    }
}
