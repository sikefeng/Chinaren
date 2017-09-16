package com.sikefeng.chinaren.utils;

import android.content.Context;

import com.sikefeng.chinaren.XXApplication;
import com.sikefeng.chinaren.entity.model.UserBean;

import static com.hss01248.dialog.StyledDialog.context;



public class LoginUtil {


    /**
     * 功能描述：保存登录信息
     * <br>创建时间： 2017-07-25 10:43:47

     * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a>
     * @param context 上下文
     * @param userBean 用户实体类
     */
    public static void saveUserData(final Context context, final UserBean userBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /** 保存登陆者的信息 */
                SharePreferenceUtils.put(context, Constants.TOKEN, userBean.getToken());
                SharePreferenceUtils.put(context, Constants.UID, userBean.getUserId());
                SharePreferenceUtils.put(context, Constants.LOGINNAME, userBean.getLoginName());
                SharePreferenceUtils.put(context, Constants.NEWPASSWORD, userBean.getNewPassword());
                SharePreferenceUtils.put(context, Constants.NAME, userBean.getName());
                SharePreferenceUtils.put(context, Constants.USERTYPE, userBean.getUserType());
                SharePreferenceUtils.put(context, Constants.ISLOGIN, true);//标记用户已经登录
            }
        }).start();
    }

    /**
     * 功能描述：用户退出登录清除数据
     * <br>创建时间： 2017-07-24 17:16:10
     *
     * @author <a href="mailto:sikefeng.xu@sikefeng.com">Richard</a>
     */
    public static void exitLogin() {
        String loginName = (String) SharePreferenceUtils.get(context, Constants.LOGINNAME, "");
        String newPassword = (String) SharePreferenceUtils.get(context, Constants.NEWPASSWORD, "");
        SharePreferenceUtils.clear(XXApplication.getContext());
        SharePreferenceUtils.put(XXApplication.getContext(), Constants.ISLOGIN, false);
        SharePreferenceUtils.put(context, Constants.LOGINNAME, loginName);
        SharePreferenceUtils.put(context, Constants.NEWPASSWORD, newPassword);
    }


}
