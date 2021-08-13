package com.enzo.commonlib.utils.crashlib.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.enzo.commonlib.utils.common.ExternalCacheUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 异常捕获工具类  by zc 2018年05月07日15:24:15
 */
public final class CrashHelper {
    private static String versionName;
    private static int versionCode;

    @SuppressLint("SimpleDateFormat")
    private static final Format FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    public static void init(Context context) {

        try {
            PackageInfo pi = context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private CrashHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 拼接奔溃信息
     */
    public static String buildCrashInfo(Throwable e) {
        final StringBuilder sb = new StringBuilder();
        final String time = FORMAT.format(new Date(System.currentTimeMillis()));
        final String head = "************* Log Head ****************"
                + "\nTime Of Crash      : "
                + time
                + "\nDevice Manufacturer: "
                + Build.MANUFACTURER
                + "\nDevice Model       : "
                + Build.MODEL
                + "\nAndroid Version    : "
                + Build.VERSION.RELEASE
                + "\nAndroid SDK        : "
                + Build.VERSION.SDK_INT
                + "\nApp VersionName    : "
                + versionName
                + "\nApp VersionCode    : "
                + versionCode
                + "\n************* Log Head ****************\n\n";
        sb.append(head);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.flush();
        sb.append(sw.toString());
        return sb.toString();
    }

    /**
     * 将奔溃信息存储到本地
     */
    public static void saveCrashLogToLocal(Application application, String crashInfo) {
        final String time = FORMAT.format(new Date(System.currentTimeMillis()));
        String fileName = "crash-" + time + ".txt";
        final String fullPath = new File(getDefaultCrashDir(application).getAbsolutePath(), fileName).getAbsolutePath();
        if (createOrExistsFile(fullPath)) {
            input2File(application, crashInfo, fullPath);
        } else {
            Log.e("CrashHelper", "create " + fullPath + " failed!");
        }
    }

    /**
     * get default crash log dir
     *
     * @return dir
     */
    private static File getDefaultCrashDir(Context context) {
        return ExternalCacheUtil.getCrashDir(context);
    }

    private static void input2File(final Application application, final String input, final String filePath) {
        Future<Boolean> submit =
                Executors.newSingleThreadExecutor().submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        BufferedWriter bw = null;
                        try {
                            bw = new BufferedWriter(new FileWriter(filePath, true));
                            bw.write(input);
                            onClearFile(application);
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        } finally {
                            try {
                                if (bw != null) {
                                    bw.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        try {
            if (submit.get()) return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("CrashHelper", "write crash info to " + filePath + " failed!");
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 删除超过3天的文件，防止过多
     */
    private static void onClearFile(Application application) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = getDefaultCrashDir(application);
            if (file.exists()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 3) {
                    int index = 0;
                    for (File temp : files) {
                        long time = System.currentTimeMillis() - temp.lastModified();
                        if (time / 1000 > 3600 * 24 * 3) {
                            temp.delete();
                            index++;
                        }
                        if (index >= files.length - 3) break;
                    }
                }
            }
        }
    }
}
