
package com.sikefeng.chinaren.api;


import com.sikefeng.chinaren.entity.model.BaseData;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.entity.model.UserData;
import com.sikefeng.chinaren.entity.model.UserListData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface CommonApiService {


    @POST("feedback/user_feedback")
    Observable<BaseData> feedBack(@Query("content") String content);



}



