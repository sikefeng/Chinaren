/**
 * Copyright (C) 2014-2017 <a href="http://www.xxxxtech.com>">XXXXTech</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.entity.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：ServerListData <br>
 * 创建时间： 28/7/17 下午PM3:55 <br>
 * 文件描述：<br>
 * 获取多个服务实体
 *
 * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class NoteListData extends BaseData{



    /**
     * 多个服务请求实体类
     */
    private List<NoteBean> data = new ArrayList<NoteBean>();

    public List<NoteBean> getData() {
        return this.data;
    }

    public void setData(List<NoteBean> data) {
        this.data = data;
    }
}
