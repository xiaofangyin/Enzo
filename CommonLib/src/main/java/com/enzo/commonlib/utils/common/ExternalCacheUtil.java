package com.enzo.commonlib.utils.common;

import android.content.Context;
import android.os.Environment;

/**
 * 文 件 名: ExternalCacheUtil
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-09-23
 * 邮   箱: xiaofangyin@360.cn
 */
public class ExternalCacheUtil {

    /**
     * Context.getFilesDir()=/data/data/com.harry.shopping/files
     * Context.getCacheDir()=/data/data/com.harry.shopping/cache
     * Environment.getExternalStorageDirectory()=/storage/emulated/0
     * getExternalFilesDir(Environment.DIRECTORY_PICTURES)=/storage/emulated/0/Android/data/com.harry.shopping/files/Pictures
     * Context.getExternalFilesDir(null)=/storage/emulated/0/Android/data/com.harry.shopping/files
     * Context.getExternalCacheDir()=/storage/emulated/0/Android/data/com.harry.shopping/cache
     */

    /**
     * 网络请求日志缓存目录
     */
    public static String getNetLogCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath() + "/log";
        } else {
            return context.getCacheDir().getPath() + "/log";
        }
    }

    /**
     * crash缓存目录
     */
    public static String getCrashDir(Context context) {
        return getExternalCacheDir(context) + "/crash/";
    }

    /**
     * cache目录
     */
    private static String getExternalCacheDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }
}
