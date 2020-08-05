package com.enzo.commonlib.utils.appupgrade;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.enzo.commonlib.R;
import com.enzo.commonlib.net.download.DownloadUtil;
import com.enzo.commonlib.utils.common.ApkUtils;
import com.enzo.commonlib.utils.common.ExternalCacheUtil;
import com.enzo.commonlib.utils.common.LogUtil;

import java.io.File;

/**
 * * 由于Android Q版本限制后台应用启动Activity，所以下载完成会发送一个通知至通知栏（忽略showNotification的值，需要允许发送通知）
 * * <a href="https://developer.android.google.cn/guide/components/activities/background-starts"/>
 * * </div>
 * * <div>
 * * 由于Android Q版本限制应用访问外部存储目录（访问需要同时满足两个条件详情见文档）所以Q版本以上不要设置下载目录
 * * <a href="https://developer.android.google.cn/training/data-storage/files/external-scoped"/>
 * * </div>
 * * https://developer.android.google.cn/training/data-storage/files/external-scoped
 */
public class UpgradeVersionService extends Service {

    //下载进度
    private int mCurrentProgress;
    private NotificationUtils notification;

    @Override
    public void onCreate() {
        super.onCreate();
        notification = new NotificationUtils(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("UpdateVersionService onStartCommand...");
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("downloadUrl");

        notification.sendNotificationProgress("正在下载：" + getResources().getString(R.string.app_name), "0%", mCurrentProgress, null);
        downLoadFile(url);
        return super.onStartCommand(intent, flags, startId);
    }

    public void downLoadFile(String url) {
        LogUtil.e("UpdateVersionService downLoadFile url: " + url);
        DownloadUtil.get().download(url,
                ExternalCacheUtil.getApkDownloadPath(getApplicationContext()),
                UpgradeVersionUtil.DOWN_LOAD_APP_NAME,
                new DownloadUtil.OnDownloadListener() {

                    @Override
                    public void onDownloadStart() {
                        LogUtil.e("UpdateVersionService onDownloadStart...");
                        mCurrentProgress = 0;
                        notification.sendNotificationProgress("正在下载：" + getResources().getString(R.string.app_name), 0 + "%", mCurrentProgress, null);
                    }

                    @Override
                    public void onDownloadSuccess(File file) {
                        LogUtil.e("UpdateVersionService onSuccess...");
                        mCurrentProgress = 0;
                        notification.sendNotificationProgress("正在下载：" + getResources().getString(R.string.app_name), "下载完成!", 100, null);
                        stopSelf();
                        //收起通知栏
                        UpgradeVersionUtil.collapseStatusBar(UpgradeVersionService.this);
                        //自动安装新版本
                        ApkUtils.installApk(UpgradeVersionService.this, file);
                    }

                    @Override
                    public void onDownloading(int progress) {
                        LogUtil.e("UpdateVersionService inProgress... progress: " + progress);
                        if (mCurrentProgress != progress) {
                            mCurrentProgress = progress;
                            notification.sendNotificationProgress("正在下载：" + getResources().getString(R.string.app_name), progress + "%", mCurrentProgress, null);
                        }
                    }

                    @Override
                    public void onDownloadFailed() {
                        LogUtil.e("UpdateVersionService onFailure...");
                        mCurrentProgress = 0;
                        notification.sendNotificationProgress("正在下载：" + getResources().getString(R.string.app_name), "网络异常！请检查网络设置！", mCurrentProgress, null);
                    }
                });
    }

    @Override
    public void onDestroy() {
        //下载完成时，清楚该通知，自动安装
        mCurrentProgress = 0;
        notification.cancel();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
