
package com.sikefeng.chinaren.entity.model;

public class UserData extends BaseData{

    /**
     * 单个用户实体
     */
    private UserBean data = new UserBean();

    /**
     * 获取UserBean
     * @return UserBean
     */
    public UserBean getData() {
        return data;
    }
}
