
package com.sikefeng.chinaren.core;


import com.sikefeng.chinaren.api.UserApiService;


public class ServiceHelper {

    public static UserApiService getUsersAS(){
        return (UserApiService) ServiceFactory.getInstance().getService(UserApiService.class);
    }


}
