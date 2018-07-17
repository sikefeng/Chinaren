
package com.sikefeng.chinaren.api;


import com.sikefeng.chinaren.entity.model.BaseData;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface CommonApiService {


    @POST("feedback/user_feedback")
    Observable<BaseData> feedBack(@Query("userId") String userId,@Query("content") String content,@Query("type") String type);



}



