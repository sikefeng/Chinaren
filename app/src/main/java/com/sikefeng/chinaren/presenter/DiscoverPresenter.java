package com.sikefeng.chinaren.presenter;

import com.sikefeng.chinaren.core.BasePresenter;
import com.sikefeng.chinaren.presenter.vm.DiscoverViewModel;
import com.sikefeng.mvpvmlib.base.IRBaseView;

/**
 * 文件名：ForgetPwdPresenter <br>
 * 创建时间： 2017/7/21 0021 下午16:19 <br>
 * 文件描述：<br>
 * 忘记密码数据协调器，主要用于组织数据
 *
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class DiscoverPresenter extends BasePresenter<IRBaseView, DiscoverViewModel> {

    /**
     * 构造函数设计
     *
     * @param view      view视图
     * @param viewModel 视图相关实体
     */
    public DiscoverPresenter(IRBaseView view, DiscoverViewModel viewModel) {
        super(view, viewModel);
    }

    @Override
    public void onCreate() {

    }



}


