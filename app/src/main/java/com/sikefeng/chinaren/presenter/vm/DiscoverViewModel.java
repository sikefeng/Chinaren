package com.sikefeng.chinaren.presenter.vm;

import com.sikefeng.chinaren.core.BaseViewModel;
import com.sikefeng.chinaren.databinding.FragmentDiscoverBinding;
import com.sikefeng.chinaren.entity.model.UserBean;

/**
 * 文件名：MyFragmentViewModel <br>
 * 创建时间： 24/7/17 下午PM4:56 <br>
 * 文件描述：<br>
 * 我界面的模型
 *
 * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class DiscoverViewModel extends BaseViewModel {

    /**
     *  我界面数据绑定类
     */
    private FragmentDiscoverBinding currentBinding;
    /**
     *  用户实体类
     */
    private UserBean user = new UserBean();

    public UserBean getUser() {
        return user;
    }

    public void setCurrentBinding(FragmentDiscoverBinding currentBinding) {
        this.currentBinding = currentBinding;
    }





}


