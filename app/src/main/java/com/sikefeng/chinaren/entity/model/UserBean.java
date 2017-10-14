
package com.sikefeng.chinaren.entity.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserBean extends RealmObject {
    /**
     * 用户ID
     */
    @PrimaryKey
    private String userId;
    /**
     * 用户token
     */
    private String token;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String newPassword;
    /**
     * 验证码
     */
    private String verCode;
    /**
     * 工号
     */
    private String no;
    /**
     * 用户真实名称
     */
    private String name;
    /**
     * 邮件地址
     */
    private String email;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 移动电话号码
     */
    private String mobile;
    /**
     * 用户类别
     */
    private String userType;
    /**
     * 用户头像
     */
    private String photo;
    /**
     * 后台接口返回验证码code
     */
    private String code;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}

