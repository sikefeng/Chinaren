package com.sikefeng.chinaren.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;


public final class CrashApphandler extends CrashAppLog {

    /**
     * 定义崩溃日志路径
     */
    private static final String LOG_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/se";

    /**
     * 定义CrashApphandler
     */
    private static CrashApphandler mCrashApphandler = null;

    /**
     * 构造函数
     */
    private CrashApphandler(){};

    /**
     * 单根
     * @return CrashApphandler
     */
    public static CrashApphandler getInstance() {

        if (mCrashApphandler == null) {
            mCrashApphandler = new CrashApphandler();
        }

        return mCrashApphandler;

    }

    @Override
    public void initParams(CrashAppLog crashAppLog) {

        if (crashAppLog != null){
            File dir = new File(LOG_DIR);
            if(!dir.exists()){
                dir.mkdirs();
            }
            crashAppLog.setCacheCrashLog(LOG_DIR+ File.separator+"crashLog");
            crashAppLog.setLimitLogCount(Constants.VALUE_5);
        }
    }

    @Override
    public void sendCrashLogToServer(File folder, File file) {
        Log.e("*********", "文件夹:"+folder.getAbsolutePath()+" - "+file.getAbsolutePath()+"");

//        try{
////            MailManager.getInstance().sendMailWithFile("Klup 崩溃信息", "请查收附件", file.getAbsolutePath());
//        }catch (Exception e){
//            LogUtils.i("发送邮件出错："+ e.getMessage());
//        }
    }
}
