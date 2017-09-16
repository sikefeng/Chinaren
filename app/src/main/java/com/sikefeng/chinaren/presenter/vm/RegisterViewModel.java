package com.sikefeng.chinaren.presenter.vm;

import com.sikefeng.chinaren.core.BaseViewModel;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.databinding.ActivityRegisterBinding;

/**
 * 文件名：RegisterViewModel <br>
 * 创建时间： 2017/7/21 0021 下午15:47 <br>
 * 文件描述：<br>
 * 注册界面的模型
 *
 * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class RegisterViewModel extends BaseViewModel {
    /**
     *  注册界面数据绑定类
     */
    private ActivityRegisterBinding currentBinding;
    /**
     *  用户实体类
     */
    private UserBean user = new UserBean();

    public UserBean getUser() {
        return user;
    }

    public void setCurrentBinding(ActivityRegisterBinding currentBinding) {
        this.currentBinding = currentBinding;
    }

    /**
     * 功能描述：绑定数据
     * <br>创建时间： 2017-07-21 15:57:27

     * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a>
     * @param verCode  验证码
     */
    public void setUserData(String verCode){
        currentBinding.setUserBean(user);
        user.setVerCode(verCode);
    }
}
