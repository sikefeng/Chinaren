/**
 * Copyright (C) 2014-2017 <a href="http://www.xxxxtech.com>">XXXXTech</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.presenter.vm;


import com.sikefeng.chinaren.core.BaseViewModel;
import com.sikefeng.chinaren.databinding.ActivityNewNoteBinding;
import com.sikefeng.chinaren.entity.model.NoteBean;

/**
 * 文件名：LoginViewModel <br>
 * 创建时间： 2017/7/21 0021 下午16:25 <br>
 * 文件描述：<br>
 * 登录界面的模型
 *
 * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class NewNoteViewModel extends BaseViewModel {



    private ActivityNewNoteBinding currentBinding;

    /**
     * 定义用户
     */
    private NoteBean noteBean = new NoteBean();

    public NoteBean getNoteBean() {
        return noteBean;
    }

    public NewNoteViewModel(ActivityNewNoteBinding binding){
        this.currentBinding = binding;
    }


}



