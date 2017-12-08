package com.ifenglian.commonlib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import java.io.File;

/**
 * @项目名: gdmsaec-app
 * @包名: com.winfo.gdmsaec.app.utils
 * @类名: ApkUtils
 * @创建者: yanfeijun
 * @创建时间: 2015-10-14	上午10:57:36
 * @描述: 获取app相关信息的工具类
 * @svn版本: $Rev: 1161 $
 * @更新人: $Author: wenjie $
 * @更新时间: $Date: 2016-01-25 14:21:41 +0800 (Mon, 25 Jan 2016) $
 * @更新描述: TODO
 */
public class ApkUtils {
    private static final String TAG = ApkUtils.class.getSimpleName();

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {

        }
        return null;
    }

    /**
     * @return 当前程序的版本号
     */
    public static int getVersionCode(Context context) {
        int version;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            version = 0;
        }
        return version;
    }

    /**
     * 得到安装的intent
     *
     * @param apkFile
     * @return
     */
    public static Intent getInstallIntent(File apkFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkFile.getAbsolutePath())),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }


}
