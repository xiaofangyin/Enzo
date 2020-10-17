package com.enzo.commonlib.utils.appupgrade;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.enzo.commonlib.utils.appupgrade.bean.AndroidBean;
import com.enzo.commonlib.utils.common.ExternalCacheUtil;
import com.enzo.commonlib.utils.toast.ToastUtil;

import java.io.File;
import java.util.ArrayList;

public class AppUpgradeUtil {

    //下载apk名称
    static String DOWN_LOAD_APP_NAME = "my_test.apk";
    private static final String url = "https://89e03ca66219bbe3cf0d65cd0d800c50.dd.cdntips.com/imtt.dd.qq.com/16891/apk/86E914A33DAF7E2B88725E486E907288.apk?mkey=5e8b026fb79c5ff3&f=1026&fsname=com.estrongs.android.pop_4.2.2.3_10063.apk&csr=1bbd&cip=183.156.121.6&proto=https";

    public interface UpdateListener {
        void onNewVersion(AndroidBean versionInfo);

        void onNewest();

        void onFailed(Exception e);
    }

    public static void checkVersion(final Context context, final UpdateListener updateListener) {
        int status = 1;
        AndroidBean versionInfo = new AndroidBean();
        versionInfo.setIntro("1.修复bug\\n2.优化体验");
        versionInfo.setUpdate("0");
        versionInfo.setUpgrade_url(url);
        versionInfo.setVersion("v5.3.0");
        if (status == 1) {
            updateListener.onNewVersion(versionInfo);
        } else if (status == 2) {
            updateListener.onNewest();
        } else {
            updateListener.onFailed(null);
        }
    }

    /**
     * 弹出新版本提示
     *
     * @param context     上下文
     * @param versionInfo 更新内容
     */
    public static void showDialog(final Context context, final AndroidBean versionInfo) {
        String version = versionInfo.getVersion();
        String intro = versionInfo.getIntro();
        while (intro.contains("\\n")) {
            intro = intro.replace("\\n", "\n");
        }

        new AppUpgradeDialog(context, version, intro, new AppUpgradeDialog.OnAlertViewClickListener() {
            @Override
            public void onNegBtnClick() {

            }

            @Override
            public void onPosBtnClick() {
                if (!isServiceRunning(context, AppUpgradeService.class.getName())) {
                    //新版本已经下载
                    ToastUtil.show("开始下载新版本...");
                    File file = new File(ExternalCacheUtil.getApkDownloadDirectory(context), DOWN_LOAD_APP_NAME);
                    if (file.exists()) {
                        file.delete();
                    }
                    //没有下载，则开启服务下载新版本
                    Intent intent = new Intent(context, AppUpgradeService.class);
                    intent.putExtra("downloadUrl", versionInfo.getUpgrade_url());
                    context.startService(intent);
                } else {
                    ToastUtil.show("正在下载...");
                }
            }
        }).show();
    }

    /**
     * 收起通知栏
     */
    static void collapseStatusBar(Context context) {
        try {
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 判断服务是否开启
     */
    private static boolean isServiceRunning(Context context, String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService =
                (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

}
