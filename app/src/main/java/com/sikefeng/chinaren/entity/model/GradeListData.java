package com.sikefeng.chinaren.entity.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 24/9/17.
 */

public class GradeListData extends BaseData{

    public List<GradeBean> getData() {
        return this.data;
    }

    /**
     * 多个用户实体
     */
    private List<GradeBean> data = new ArrayList<>();

    public void setData(List<GradeBean> data) {
        this.data = data;
    }
}
