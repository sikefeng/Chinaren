package com.sikefeng.chinaren.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.sikefeng.chinaren.entity.model.PhoneBean;

import java.util.ArrayList;
import java.util.List;



public class PhoneUtils {

    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    //上下文对象
    private Context context;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public PhoneUtils(Context context) {
        this.context = context;
    }

    //获取所有联系人
    public List<PhoneBean> getPhone() {
        List<PhoneBean> phoneDtos = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
        while (cursor.moveToNext()) {
            PhoneBean phoneDto = new PhoneBean(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)));
            phoneDtos.add(phoneDto);
        }
        return phoneDtos;
    }

    //直接呼叫
    public void callPhone(Activity mContext, String phone) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CALL_PHONE}, 1001);
                return;
            } else {
                Intent intent3 = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phone);
                intent3.setData(data);
                context.startActivity(intent3);
            }
        } else {
            Intent intent3 = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phone);
            intent3.setData(data);
            context.startActivity(intent3);
        }
    }
    //间接呼叫
//    public void callPhone(Context context,String phone){
//        Intent intent3 = new Intent(Intent.ACTION_CALL);
//        Uri data = Uri.parse("tel:"+phone);
//        intent3.setData(data);
//        context.startActivity(intent3);
//    }

}
