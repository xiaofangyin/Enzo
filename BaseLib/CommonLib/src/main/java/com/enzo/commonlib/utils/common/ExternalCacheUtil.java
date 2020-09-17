package com.enzo.commonlib.utils.common;

import android.content.Context;
import android.os.Environment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
        return getExpectedFile(context, "apk");
    }

    /**
     * 图片缓存目录
     */
    public static File getImageCacheDirectory(Context context) {
        return getExpectedFile(context, "images");
    }

    /**
     * 头像缓存目录
     */
    public static File getAvatarCacheDirectory(Context context) {
        return getExpectedFile(context, "avatar");
    }

    /**
     * 网络请求日志缓存目录
     */
    public static File getNetLogCacheDirectory(Context context) {
        return getExpectedFile(context, "log");
    }

    /**
     * crash缓存目录
     */
    public static File getCrashDir(Context context) {
        return getExpectedFile(context, "crash");
    }

    /**
     * crash缓存目录
     */
    public static File getThemeDir(Context context) {
        return getExpectedFile(context, "theme");
    }

    @NotNull
    private static File getExpectedFile(Context context, String child) {
        File file = new File(getExternalCacheDir(context), child);
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

    public static boolean copyAssetsFile(Context context, String fileFromName, String toDir) {
        try {
            InputStream its = context.getAssets().open(fileFromName);
            int fileLength = its.available();
            File book_file = new File(toDir);
            if (!book_file.exists()) {
                book_file.createNewFile();
            }

            FileOutputStream fots = new FileOutputStream(book_file, true);
            byte[] buffer = new byte[fileLength];
            int readCount = 0; // 已经成功读取的字节的个数
            while (readCount < fileLength) {
                readCount += its.read(buffer, readCount, fileLength - readCount);
            }
            fots.write(buffer, 0, fileLength);

            its.close();
            fots.close();
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
    }
}
