package com.sikefeng.chinaren.presenter.vm;

import com.sikefeng.chinaren.core.BaseViewModel;
import com.sikefeng.chinaren.entity.model.UserBean;
import com.sikefeng.chinaren.databinding.ActivityForgetpwdBinding;

/**
 * 文件名：ForgetPwdViewModel <br>
 * 创建时间： 2017/7/21 0021 下午16:23 <br>
 * 文件描述：<br>
 * 忘记密码界面的模型
 *
 * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class ForgetPwdViewModel extends BaseViewModel {

    /**
     *  忘记密码界面数据绑定类
     */
    private ActivityForgetpwdBinding currentBinding;
    /**
     *  用户实体类
     */
    private UserBean user = new UserBean();

    public UserBean getUser() {
        return user;
    }

    public void setCurrentBinding(ActivityForgetpwdBinding currentBinding) {
        this.currentBinding = currentBinding;
    }

    /**
     * 功能描述：绑定数据
     * <br>创建时间： 2017-07-23 10:24:56

     * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a>
     * @param verCode 验证码
     */
    public void setUserData(String verCode){
        currentBinding.setUserBean(user);
        user.setVerCode(verCode);
    }


}


