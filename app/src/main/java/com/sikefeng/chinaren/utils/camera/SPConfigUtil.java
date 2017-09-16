package com.sikefeng.chinaren.utils.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import com.sikefeng.chinaren.XXApplication;


public class SPConfigUtil {
    /**
     * TAG
     */
    static final String TAG = "SPConfigUtil";
    /**
     * 缓存名称
     */
    static final String SP_NAME = "SPConfigUtil";

    /**
     * loadLong
     *
     * @param key    String
     * @param defVal long
     * @return long
     */
    public static synchronized long loadLong(final String key, long defVal) {
        Long cf = defVal;
        String cfStr = load(key);
        try {
            cf = Long.parseLong(cfStr);
        } catch (Exception e) {

        }
        return cf;
    }

    /**
     * loadInt
     *
     * @param key    String
     * @param defVal int
     * @return int
     */
    public static synchronized int loadInt(final String key, int defVal) {
        Integer cf = defVal;
        String cfStr = load(key);
        try {
            cf = Integer.parseInt(cfStr);
        } catch (Exception e) {

        }
        return cf;
    }

    /**
     * loadBoolean
     *
     * @param key    String
     * @param defVal boolean
     * @return boolean
     */
    public static synchronized boolean loadBoolean(final String key,
                                                   boolean defVal) {
        Boolean cf = defVal;
        String cfStr = load(key);
        try {
            if (!TextUtils.isEmpty(cfStr)) {
                cf = Boolean.parseBoolean(cfStr);
            }
        } catch (Exception e) {

        }
        return cf;
    }

    /**
     * load
     * @param key String
     * @return String
     */
     public static synchronized String load(final String key) {
        String cf = null;
        if (XXApplication.getInstance() == null) {
            return cf;
        }
        try {
            SharedPreferences sp = XXApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            cf = sp.getString(key, null);
        } catch (Exception e) {
            // Log.e(TAG, e);
        }
        return cf;
    }

    /**
     * save
     * @param key String
     * @param val String
     */
     public static synchronized void save(final String key, final String val) {
        if (XXApplication.getInstance() == null || val == null) {
            return;
        }
        try {
            SharedPreferences sp = XXApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            Editor edit = sp.edit();
            edit.putString(key, val);
            edit.commit();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

    }

    /**
     * clear
     * @param key String
     */
     public static synchronized void clear(final String key) {
        try {
            SharedPreferences sp = XXApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            Editor edit = sp.edit();
            edit.remove(key);
            edit.commit();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * clearAll
     */
    public static synchronized void clearAll() {
        try {
            SharedPreferences sp = XXApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            Editor edit = sp.edit();
            edit.clear();
            edit.commit();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
