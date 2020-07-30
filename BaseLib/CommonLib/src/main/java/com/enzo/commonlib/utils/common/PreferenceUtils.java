package com.enzo.commonlib.utils.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtils {
    private static SharedPreferences mSp;
    private final static String SP_NAME = "config";

    /**
     * 获得sharePreference内存对象
     *
     * @param context
     * @return
     */
    private static SharedPreferences getSp(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mSp;
    }

    /**
     * 获取boolean类型的值
     *
     * @param context  上下文
     * @param key      对应的键
     * @param defValue 如果没有对应的值，
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 获取boolean类型的值,如果没有对应的值，默认值返回false
     *
     * @param context 上下文
     * @param key     对应的键
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }


    /**
     * 设置int类型的值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setInt(Context context, String key, int value) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    /**
     * 设置boolean类型的值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取String类型的值
     *
     * @param context  上下文
     * @param key      对应的键
     * @param defValue 如果没有对应的值，
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getString(key, defValue);
    }

    /**
     * 获取int类型的值
     *
     * @param context  上下文
     * @param key      对应的键
     * @param defValue 如果没有对应的值，
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取String类型的值,如果没有对应的值，默认值返回null
     *
     * @param context 上下文
     * @param key     对应的键
     * @return
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * 设置String类型的值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取long类型的值
     *
     * @param context  上下文
     * @param key      对应的键
     * @param defValue 如果没有对应的值，
     * @return
     */
    public static long getLong(Context context, String key, long defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getLong(key, defValue);
    }

    /**
     * 获取long类型的值,如果没有对应的值，默认值返回0
     *
     * @param context 上下文
     * @param key     对应的键
     * @return
     */
    public static Long getLong(Context context, String key) {
        return getLong(context, key, 0);
    }

    /**
     * 设置Long类型的值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setLong(Context context, String key, long value) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 根据key值删除指定的数据
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = getSp(context);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
}
