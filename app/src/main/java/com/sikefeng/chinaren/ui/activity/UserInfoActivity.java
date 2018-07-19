package com.sikefeng.chinaren.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.databinding.ActivityUserInfoBinding;
import com.sikefeng.chinaren.utils.SharePreferenceUtils;

import static com.sikefeng.chinaren.utils.Constants.GENDER;
import static com.sikefeng.chinaren.utils.Constants.MOTTO;
import static com.sikefeng.chinaren.utils.Constants.NICKNAME;

public class UserInfoActivity extends AppCompatActivity {
    private ActivityUserInfoBinding currentBinding;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
        mContext = this;
        initUserInfo();

    }

    private void initUserInfo() {
        String nickName = (String) SharePreferenceUtils.get(mContext, NICKNAME, "");
        String motto = (String) SharePreferenceUtils.get(mContext, MOTTO, "");
        String gender = (String) SharePreferenceUtils.get(mContext, GENDER, "0");
        if (gender.equals("0")) {
            currentBinding.tvGender.setText("女");
        } else {
            currentBinding.tvGender.setText("男");
        }
        currentBinding.nickName.setText(nickName);
        currentBinding.tvMotto.setText(motto);
        currentBinding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void updateUserInfo(String type) {
        Intent intent = new Intent(mContext, UserInfoActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }


}
