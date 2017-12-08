package com.ifenglian.commonlib.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class SDCardUtils {

    public static String getPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取SD卡的状态
     *
     * @return
     */
    public static String getState() {
        return Environment.getExternalStorageState();
    }

    /**
     * SD卡是否可用
     *
     * @return 只有当SD卡已经安装并且准备好了才返回true
     */
    public static boolean isAvailable() {
        return getState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡的根目录
     *
     * @return null：不存在SD卡
     */
    public static File getRootDirectory() {
        return isAvailable() ? Environment.getExternalStorageDirectory() : null;
    }

    /**
     * 获取SD卡的根路径
     *
     * @return null：不存在SD卡
     */
    public static String getRootPath() {
        File rootDirectory = getRootDirectory();
        return rootDirectory != null ? rootDirectory.getPath() : null;
    }

    /**
     * 获取SD卡总的容量
     *
     * @return 总容量；-1：SD卡不可用
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static long getTotalSize() {
        if (isAvailable()) {
            StatFs statFs = new StatFs(getRootDirectory().getPath());
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return statFs.getBlockCount() * statFs.getBlockSize();
            } else {
                return statFs.getBlockCount() * statFs.getBlockSize();
            }
        } else {
            return -1;
        }
    }

    /**
     * 获取SD卡中可用的容量
     *
     * @return 可用的容量；-1：SD卡不可用
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static long getAvailableSize() {
        if (isAvailable()) {
            StatFs statFs = new StatFs(getRootDirectory().getPath());
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return statFs.getAvailableBlocks() * statFs.getBlockSize();
            } else {
                return statFs.getAvailableBlocks() * statFs.getBlockSize();
            }
        } else {
            return -1;
        }
    }
}
