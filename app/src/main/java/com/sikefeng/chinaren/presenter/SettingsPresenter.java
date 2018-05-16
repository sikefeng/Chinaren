
package com.sikefeng.chinaren.presenter;

import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.mvpvmlib.base.IRBaseView;
import com.sikefeng.chinaren.presenter.vm.SettingsViewModel;


public class SettingsPresenter extends BasePresenter<IRBaseView, SettingsViewModel> {

    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public SettingsPresenter(IRBaseView view, SettingsViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {

    }

}

