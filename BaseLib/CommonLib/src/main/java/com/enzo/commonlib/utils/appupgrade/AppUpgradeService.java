package com.enzo.commonlib.utils.appupgrade;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

import com.enzo.commonlib.R;
import com.enzo.commonlib.net.download.DownloadUtil;
import com.enzo.commonlib.utils.common.ApkUtils;
import com.enzo.commonlib.utils.common.ExternalCacheUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.notification.NotificationUtils;
import com.enzo.commonlib.utils.toast.ToastUtil;

import java.io.File;

/**
 * * 由于Android Q版本限制后台应用启动Activity，所以下载完成会发送一个通知至通知栏（忽略showNotification的值，需要允许发送通知）
 * * 由于Android Q版本限制应用访问外部存储目录（访问需要同时满足两个条件详情见文档）所以Q版本以上不要设置下载目录
 */
public class AppUpgradeService extends Service {

    //通知id
    private static final int NOTIFY_ID = 633980;
    private static final String CHANNEL_ID = "UpgradeChannel";
    //在通知管理显示的名称
    private static final String CHANNEL_NAME = "Upgrade";
    //下载进度
    private int mCurrentProgress;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e(" AppUpgradeService onCreate...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(" AppUpgradeService onStartCommand...");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String url = bundle.getString("downloadUrl", "");
            if (!TextUtils.isEmpty(url)) {
                sendNotification("0%", 0);
                downLoadFile(url);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void downLoadFile(String url) {
        LogUtil.e(" AppUpgradeService downLoadFile url: " + url);
        DownloadUtil.get().download(url,
                ExternalCacheUtil.getApkDownloadDirectory(getApplicationContext()),
                AppUpgradeUtil.DOWN_LOAD_APP_NAME,
                new DownloadUtil.OnDownloadListener() {

                    @Override
                    public void onDownloadStart() {
                        LogUtil.e(" AppUpgradeService onDownloadStart...");
                        mCurrentProgress = 0;
                        sendNotification("0%", mCurrentProgress);
                    }

                    @Override
                    public void onDownloadSuccess(File file) {
                        LogUtil.e(" AppUpgradeService onSuccess...");
                        mCurrentProgress = 100;
                        sendNotification("下载完成!", mCurrentProgress);
                        stopSelf();
                        //收起通知栏
                        AppUpgradeUtil.collapseStatusBar(AppUpgradeService.this);
                        //自动安装新版本
                        ApkUtils.installApk(AppUpgradeService.this, file);
                    }

                    @Override
                    public void onDownloading(int progress) {
                        LogUtil.e(" AppUpgradeService inProgress... progress: " + progress);
                        if (mCurrentProgress != progress) {
                            mCurrentProgress = progress;
                            sendNotification(progress + "%", progress);
                        }
                    }

                    @Override
                    public void onDownloadFailed() {
                        LogUtil.e(" AppUpgradeService onFailure...");
                        mCurrentProgress = 0;
                        sendNotification("下载失败！请检查网络设置！", mCurrentProgress);
                        ToastUtil.show("下载失败！请检查网络设置！");
                        stopSelf();
                    }
                });
    }

    /**
     * 发送通知
     */
    private void sendNotification(String content, int progress) {
        NotificationUtils notificationUtils = new NotificationUtils(this, CHANNEL_ID, CHANNEL_NAME);
        NotificationUtils.Options options = new NotificationUtils.Options();
        //让通知左右滑的时候是否可以取消通知
        options.setOngoing(true)
                //设置优先级
                .setPriority(Notification.PRIORITY_HIGH)
                //设置进度
                .setProgress(progress);
        //设置状态栏的标题
        if (progress == 0) {
            options.setTicker("开始下载更新包");
        } else if (progress == 100) {
            options.setTicker("下载完成");
        } else {
            options.setTicker("正在下载更新包");
        }
        notificationUtils.setOptions(options);
        //必须设置的属性，发送通知
        notificationUtils.sendNotification(NOTIFY_ID, "正在下载：" + getResources().getString(R.string.app_name), content, R.mipmap.icon_skull_2);
    }

    @Override
    public void onDestroy() {
        //下载完成时，清楚该通知，自动安装
        mCurrentProgress = 0;
        NotificationUtils notificationUtils = new NotificationUtils(this, CHANNEL_ID, CHANNEL_NAME);
        notificationUtils.cancelNotify(NOTIFY_ID);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
