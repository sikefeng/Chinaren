/**
 * Copyright (C) 2014-2017 <a href="http://www.xxxxtech.com>">XXXXTech</a> All Rights Reserved.
 */
package com.sikefeng.chinaren.presenter.vm;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseViewModel;
import com.sikefeng.chinaren.databinding.ActivityNoteListBinding;
import com.sikefeng.chinaren.databinding.ItemListNoteBinding;
import com.sikefeng.chinaren.entity.model.NoteBean;
import com.sikefeng.chinaren.entity.model.NoteListData;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.utils.CommonUtils;

import cn.bingoogolapple.androidcommon.adapter.BGABindingRecyclerViewAdapter;


public class NoteListViewModel extends BaseViewModel {


    /**
     *  定义FragmentNotificationBinding
     */
    private ActivityNoteListBinding currentBinding;

    /**
     * 定义用户
     */
    private UserBean user = new UserBean();

    public UserBean getUser() {
        return user;
    }
    /**
     * 赋值FragmentNotificationBinding
     * @param binding FragmentNotificationBinding
     */
    public NoteListViewModel(ActivityNoteListBinding binding){
        currentBinding = binding;
    }

    /**
     * 列表适配器
     */
    private BGABindingRecyclerViewAdapter<NoteBean, ItemListNoteBinding> adapter = new BGABindingRecyclerViewAdapter<>(R.layout.item_list_note);


    public BGABindingRecyclerViewAdapter<NoteBean, ItemListNoteBinding> getAdapter() {
        return adapter;
    }


    /**
     * 装载数据
     * @param data 数据实体
     * @param isRefresh 是否刷新
     */
    public void setData(NoteListData data, boolean isRefresh){
        setDataState(CommonUtils.isEmpty(data.getData()));
        if(isRefresh){
            adapter.clear();
            adapter.addNewData(data.getData());
        }else {
            adapter.addMoreData(data.getData());
        }

    }
    /**
     * 功能描述：设置暂无数据视图提示
     * <br>创建时间： 2017-08-23 23:21:15

     * @author <a href="mailto:sikefeng.xu@xxxxtech.com">Richard</a>
     * @param emptyView boolean
     */
    public void setEmptyView(boolean emptyView){
        if (emptyView){
            getNoMore().set(false);
            currentBinding.multipleStatusView.showEmpty();
        }else{
            currentBinding.multipleStatusView.showContent();
        }
    }
}



