
package com.sikefeng.chinaren.presenter;

import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.entity.model.GradeBean;
import com.sikefeng.chinaren.entity.model.GradeListData;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.presenter.vm.ContactsViewModel;
import com.sikefeng.chinaren.utils.DataUtils;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;


public class ContactsPresenter extends BasePresenter<IRBaseView, ContactsViewModel> {

    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public ContactsPresenter(IRBaseView view, ContactsViewModel viewModel) {
        super(view, viewModel);
    }
    public void onClickItem(BGABindingViewHolder holder, GradeBean model) {
        getContext().startActivity(BGAPhotoPreviewActivity.newIntent(getContext(),  null, model.getGradeAlbums(), 1));
    }
    @Override
    public void onCreate() {
        getViewModel().getAdapter().setItemEventHandler(this);
        loadData(true);
    }

    @Override
    public void loadData(final boolean isRefresh) {
        GradeListData data = new GradeListData();
        data.setData(DataUtils.getGradClass());
        getViewModel().setData(data, true);
    }

}

