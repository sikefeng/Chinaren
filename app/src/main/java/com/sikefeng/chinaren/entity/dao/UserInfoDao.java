package com.sikefeng.chinaren.entity.dao;

import android.content.Context;

import com.sikefeng.chinaren.MyApplication;

/**
 * Created by Richard on 24/9/17.
 */

public class UserInfoDao {


    private static UserInfoDao instance;

    private Context context;

    private UserInfoDao() {
        this.context = MyApplication.getContext();
    }

    public static UserInfoDao getInstance() {
        if (instance != null) {
            instance = new UserInfoDao();
        }
        return instance;
    }


    public  void saveUserInfo(){

    }

    public  void updateUserInfo(){

    }

    public  void deleteUserInfo(){

    }

    public  void findUserInfo(){

    }


}
