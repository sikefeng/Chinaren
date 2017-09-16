
package com.sikefeng.chinaren.entity.model;

import java.util.ArrayList;
import java.util.List;


public class UserListData extends BaseData{

    public List<UserBean> getData() {
        return this.data;
    }

    /**
     * 多个用户实体
     */
    private List<UserBean> data = new ArrayList<>();

}
