
package com.sikefeng.chinaren.api;


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


public interface UserApiService {

    String BASE_URL = APIConfig.BASE_URL_DEFAULT;

    /**
     * 获取用户列表信息
     *
     * @return Observable<UserListData>
     */
    @GET("users")
    Observable<UserListData> getUsers();

    /**
     * 获取用户信息
     *
     * @return Observable<UserData>
     */
    @GET("getUser")
    Observable<UserData> getUser();

    /**
     * 用户注册
     *
     * @param loginName 登录名称
     * @param password  密码
     * @return Observable<UserData>
     */
    @FormUrlEncoded
    @POST("register")
    Observable<UserData> register(@Field("loginName") String loginName, @Field("password") String password);

    /**
     * 用户登录
     *
     * @param userBean 用户实体类
     * @return Observable<UserData>
     */
    @POST("common/login")
    Observable<UserData> login(@Body UserBean userBean);

    /**
     * 用户注册
     *
     * @param userBean 用户实体类
     * @return Observable<UserData>
     */
    @POST("common/register")
    Observable<UserData> registerUser(@Body UserBean userBean);

    /**
     * 获取验证码
     *
     * @param tel 手机号码
     * @return Observable<UserData>
     */
    @GET("common/get_check_code")
    Observable<UserData> getCode(@Query("tel") String tel);

    /**
     * 通过验证码修改用户密码
     *
     * @param userBean 用户实体类
     * @return Observable<UserData>
     */
    @POST("common/forget_pwd")
    Observable<UserData> forgetPwd(@Body UserBean userBean);

    /**
     * 功能描述：通过旧密码修改密码
     * <br>创建时间： 2017-07-24 15:28:18
     *
     * @param oldPassword     旧密码
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @return UserData
     */
    @FormUrlEncoded
    @POST("common/change_pwd")
    Observable<UserData> updatePwd(@Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword, @Field("confirmPassword") String confirmPassword);

    /**
     * 功能描述：用户退出登录
     * <br>创建时间： 2017-07-24 15:31:08
     *
     * @param token 用户token
     * @return UserData
     */
    @GET("common/logout")
    Observable<UserData> exitLogin(@Query("token") String token);


}



