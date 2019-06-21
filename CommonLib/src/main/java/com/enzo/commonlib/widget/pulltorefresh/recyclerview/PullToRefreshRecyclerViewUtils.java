package com.enzo.commonlib.widget.pulltorefresh.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * 文 件 名: PullToRefreshRecyclerViewUtils
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofangyinwork@163.com
 */
public class PullToRefreshRecyclerViewUtils {

    private static final String REFRESH_NAME = "REFRESH_KEY";

    /**
     * 获取上次刷新保存的时间
     */
    public static long getLastRefreshTime(Context context, String tag) {
        @SuppressLint("WrongConstant")
        SharedPreferences s = context.getSharedPreferences(REFRESH_NAME, Context.MODE_APPEND);
        return s.getLong(tag, new Date().getTime());
    }

    /**
     * 保存本次刷新的时间
     */
    public static void saveLastRefreshTime(Context context, String tag, long refreshTime) {
        @SuppressLint("WrongConstant")
        SharedPreferences s = context.getSharedPreferences(REFRESH_NAME, Context.MODE_APPEND);
        s.edit().putLong(tag, refreshTime).apply();
    }


    /**
     * 时间转换
     */
    public static String getTimeConvert(long time) {
        //获取time距离当前的秒数
        int ct = (int) ((System.currentTimeMillis() - time) / 1000);

        if (ct <= 15) {
            return "刚刚";
        }

        if (ct < 60) {
            return ct + "秒前";
        }

        if (ct < 3600) {
            return Math.max(ct / 60, 1) + "分钟前";
        }

        if (ct < 86400) {
            return ct / 3600 + "小时前";
        }

        if (ct < 2592000) { //86400 * 30
            int day = ct / 86400;
            return day + "天前";
        }

        if (ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }
}
