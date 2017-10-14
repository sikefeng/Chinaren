
package com.sikefeng.chinaren.presenter;

import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.presenter.vm.TestViewModel;
import com.sikefeng.mvpvmlib.base.IRBaseView;


public class TestPresenter extends BasePresenter<IRBaseView, TestViewModel> {

    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public TestPresenter(IRBaseView view, TestViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {

    }

}

