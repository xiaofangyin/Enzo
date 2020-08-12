package com.enzo.commonlib.utils.common;

import android.content.Context;
import android.os.Environment;

import java.io.File;

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
     * apk下载目录
     */
    public static File getApkDownloadDirectory(Context context) {
        File file = new File(getExternalCacheDir(context), "apk");
        if (!file.exists()) file.mkdirs();
        return file;
    }

    /**
     * 网络请求日志缓存目录
     */
    public static File getImageCacheDirectory(Context context) {
        File file = new File(getExternalCacheDir(context), "image");
        if (!file.exists()) file.mkdirs();
        return file;
    }

    /**
     * 头像缓存目录
     */
    public static File getAvatarCacheDirectory(Context context) {
        File file = new File(getExternalCacheDir(context), "avatar");
        if (!file.exists()) file.mkdirs();
        return file;
    }

    /**
     * 网络请求日志缓存目录
     */
    public static File getNetLogCacheDirectory(Context context) {
        File file = new File(getExternalCacheDir(context), "log");
        if (!file.exists()) file.mkdirs();
        return file;
    }

    /**
     * crash缓存目录
     */
    public static File getCrashDir(Context context) {
        File file = new File(getExternalCacheDir(context), "crash");
        if (!file.exists()) file.mkdirs();
        return file;
    }

    /**
     * cache目录
     */
    private static String getExternalCacheDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null) {
                return context.getExternalCacheDir().getPath();
            } else {
                return context.getCacheDir().getPath();
            }
        } else {
            return context.getCacheDir().getPath();
        }
    }
}
