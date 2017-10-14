package com.sikefeng.chinaren.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.sikefeng.chinaren.MyApplication;
import com.sikefeng.chinaren.entity.event.PermissionEvent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;


public class PermissionUtils {

    /**
     * 定义单例
     */
    private static PermissionUtils instace;

    /**
     * 获取实例
     *
     * @return PermissionUtils
     */
    public static PermissionUtils getInstace() {
        if (instace == null) {
            instace = new PermissionUtils();
        }
        return instace;
    }

    /**
     * 方法的功能描述：授权访问
     * <br>创建时间： 2017/8/1
     *
     * @param permissions 权限集合，英文逗号隔开，例如Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
     * @param showAuth    是否显示授权对话框，当被拒绝的时候，会不断的进行权限请求，需要在Activity定义。
     * @param exitApp     是否退出App，如果true为退出，false不做任何操作。
     * @param activity    当前Activity
     * @version V1.0.0
     */
    public void authorization(Activity activity, final boolean showAuth, boolean exitApp, String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }

        final StringBuffer _SB = new StringBuffer();
        RxPermissions rxPermissions = new RxPermissions(activity);
        CompositeDisposable mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(
                rxPermissions
                        .requestEach(permissions)
                        .subscribe(permission -> { // will emit 2 Permission objects
                            if (permission.granted) {
//                        LogUtils.d("同意授权。");
                                PermissionEvent permissionEvent = new PermissionEvent();
                                permissionEvent.setGranted(true);
                                EventBus.getDefault().post(permissionEvent);
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                if (!_SB.toString().equals("Y")) {
                                    StyledDialog.buildIosAlert("授权提示", "必须授权访问才能继续使用，请重新授权，如果点击取消，将退出本此操作。", new MyDialogListener() {
                                        @Override
                                        public void onFirst() {
                                            //重新授权
                                            authorization(activity, showAuth, exitApp, permissions);
                                        }

                                        @Override
                                        public void onSecond() {
                                            if (exitApp) {
                                                ToastUtils.showLong("您没有授权访问，无法使用本程序。");
                                            } else {
                                                ToastUtils.showLong("您没有授权访问，无法使用本功能。");
                                            }
                                            if (exitApp) {
                                                System.exit(0);
                                            } else {
                                                activity.finish();
                                            }
                                        }
                                    }).show();
                                }
                                _SB.append("Y");
                            } else {
                                // Denied permission with ask never again
                                // Need to go to the settings
                                if (!_SB.toString().equals("Y")) { //目的是只打开一个对话框，因为如果有多个权限的话，会打开多个对话框。
                                    StyledDialog.buildIosAlert("授权提示", "你已经拒绝授权访问，请到设置界面重新授权访问，如果点击取消，将退出本次操作。", new MyDialogListener() {
                                        @Override
                                        public void onFirst() {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", MyApplication.getInstance().getPackageName(), null);
                                            intent.setData(uri);
                                            activity.startActivity(intent);
                                            //需要在Activity接收授权结果
                                            EventBus.getDefault().post(new PermissionEvent(true));
                                        }

                                        @Override
                                        public void onSecond() {
                                            if (exitApp) {
                                                ToastUtils.showLong("您没有授权访问，无法使用本程序。");
                                            } else {
                                                ToastUtils.showLong("您没有授权访问，无法使用本功能。");
                                            }
                                            if (exitApp) {
                                                System.exit(0);
                                            } else {
                                                activity.finish();
                                            }
                                        }
                                    }).show();
                                }

                                _SB.append("Y");
                            }

                        }));

    }

}
