package com.enzo.commonlib.utils.appupgrade;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.enzo.commonlib.R;
import com.enzo.commonlib.net.download.DownloadUtil;
import com.enzo.commonlib.utils.common.ApkUtils;
import com.enzo.commonlib.utils.common.ExternalCacheUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.notification.NotificationUtils;

import java.io.File;

/**
 * * 由于Android Q版本限制后台应用启动Activity，所以下载完成会发送一个通知至通知栏（忽略showNotification的值，需要允许发送通知）
 * * 由于Android Q版本限制应用访问外部存储目录（访问需要同时满足两个条件详情见文档）所以Q版本以上不要设置下载目录
 */
public class AppUpgradeService extends Service {

    private static final int NOTIFY_ID = 633980;
    //下载进度
    private int mCurrentProgress;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("UpdateVersionService onStartCommand...");
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("downloadUrl");
        sendNotification("0%", 0);
        downLoadFile(url);
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotification(String content, int progress) {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(true)
                //设置状态栏的标题
                .setTicker("来通知消息啦")
                //设置优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //设置进度
                .setProgress(progress)
                //必须设置的属性，发送通知
                .sendNotification(NOTIFY_ID, "正在下载：" + getResources().getString(R.string.app_name), content, R.mipmap.sa_skull_2);
    }

    public void downLoadFile(String url) {
        LogUtil.e("UpdateVersionService downLoadFile url: " + url);
        DownloadUtil.get().download(url,
                ExternalCacheUtil.getApkDownloadPath(getApplicationContext()),
                AppUpgradeUtil.DOWN_LOAD_APP_NAME,
                new DownloadUtil.OnDownloadListener() {

                    @Override
                    public void onDownloadStart() {
                        LogUtil.e("UpdateVersionService onDownloadStart...");
                        mCurrentProgress = 0;
                        sendNotification("0%", 0);
                    }

                    @Override
                    public void onDownloadSuccess(File file) {
                        LogUtil.e("UpdateVersionService onSuccess...");
                        mCurrentProgress = 0;
                        sendNotification("下载完成!", 100);
                        stopSelf();
                        //收起通知栏
                        AppUpgradeUtil.collapseStatusBar(AppUpgradeService.this);
                        //自动安装新版本
                        ApkUtils.installApk(AppUpgradeService.this, file);
                    }

                    @Override
                    public void onDownloading(int progress) {
                        LogUtil.e("UpdateVersionService inProgress... progress: " + progress);
                        if (mCurrentProgress != progress) {
                            mCurrentProgress = progress;
                            sendNotification(progress + "%", progress);
                        }
                    }

                    @Override
                    public void onDownloadFailed() {
                        LogUtil.e("UpdateVersionService onFailure...");
                        mCurrentProgress = 0;
                        sendNotification("网络异常！请检查网络设置！", 0);
                    }
                });
    }

    @Override
    public void onDestroy() {
        //下载完成时，清楚该通知，自动安装
        mCurrentProgress = 0;
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.cancelNotify(NOTIFY_ID);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
