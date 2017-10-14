package com.sikefeng.chinaren.entity.event;

/**
 * 文件名：PermissionEvent <br>
 * 创建时间： 2017/8/1 下午上午11:36 <br>
 * 文件描述：<br>
 * 授权事件，例如当进入系统授权界面，可以使用本事件通知Acitivity
 *
 * @author <a href="mailto:dawson@xxxxtech.com">Dawson</a> <br>
 * @version v0.1  <br>
 * @since JDK 1.8
 */
public class PermissionEvent {

    /**
     * 进入系统授权
     */
    private boolean toSystemAuthorization;

    /**
     * 已经成功绑定
     */
    private boolean granted;

    /**
     * 构造函数
     * @param toSystemAuthorization boolean，true为进入系统授权
     */
    public PermissionEvent(boolean toSystemAuthorization) {
        this.toSystemAuthorization = toSystemAuthorization;
    }

    /**
     * 默认构造函数
     */
    public PermissionEvent() {
    }

    public boolean isToSystemAuthorization() {
        return toSystemAuthorization;
    }

    public void setToSystemAuthorization(boolean toSystemAuthorization) {
        this.toSystemAuthorization = toSystemAuthorization;
    }

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }
}
