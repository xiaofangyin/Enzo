package com.ifenglian.commonlib.utils.updateversion;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.net.download.DownloadUtil;
import com.ifenglian.commonlib.utils.common.ApkUtils;
import com.ifenglian.commonlib.utils.common.PreferenceUtils;
import com.ifenglian.commonlib.utils.common.SDCardUtils;

import java.io.File;

public class UpdateVersionService extends Service {

    private NotificationManager nm;
    private Notification notification;
    //标题标识
    private int titleId = 1314;
    //安装文件
    private File updateFile;
    //下载进度
    private int mCurrentProgress;

    @Override
    public void onCreate() {
        super.onCreate();
        updateFile = new File(SDCardUtils.getRootDirectory() + "/updateVersion", UpdateVersionUtil.DOWN_LOAD_APP_NAME);

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = "开始下载";
        notification.when = System.currentTimeMillis();
        notification.contentView = new RemoteViews(getPackageName(), R.layout.lib_notifycation);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("AAA", "UpdateVersionService onStartCommand...");
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("downloadUrl");

        PreferenceUtils.setString(UpdateVersionService.this, "apkDownloadurl", url);

        nm.notify(titleId, notification);
        downLoadFile(url);
        return super.onStartCommand(intent, flags, startId);
    }

    public void downLoadFile(String url) {
        Log.e("AAA", "UpdateVersionService downLoadFile url: " + url);
        DownloadUtil.get().download(url,
                SDCardUtils.getRootDirectory() + "/updateVersion",
                UpdateVersionUtil.DOWN_LOAD_APP_NAME,
                new DownloadUtil.OnDownloadListener() {

                    @Override
                    public void onDownloadStart() {
                        Log.e("AAA", "UpdateVersionService onDownloadStart...");
                        mCurrentProgress = 0;
                        notification.contentView.setTextViewText(R.id.update_msg, "正在下载：一起去吃鸡");
                        // 更改文字
                        notification.contentView.setTextViewText(R.id.update_percent, 0 + "%");
                        // 更改进度条
                        notification.contentView.setProgressBar(R.id.update_progress_bar, 100, 0, false);
                        // 发送消息
                        nm.notify(titleId, notification);
                    }

                    @Override
                    public void onDownloadSuccess() {
                        Log.e("AAA", "UpdateVersionService onSuccess...");
                        mCurrentProgress = 0;
                        // 更改文字
                        notification.contentView.setTextViewText(R.id.update_msg, "下载完成!点击安装");
                        // 发送消息
                        nm.notify(titleId, notification);
                        stopSelf();
                        //收起通知栏
                        UpdateVersionUtil.collapseStatusBar(UpdateVersionService.this);
                        //自动安装新版本
                        Intent installIntent = ApkUtils.getInstallIntent(updateFile);
                        startActivity(installIntent);
                    }

                    @Override
                    public void onDownloading(int progress) {
                        Log.e("AAA", "UpdateVersionService inProgress... progress: " + progress);
                        if (mCurrentProgress != progress) {
                            mCurrentProgress = progress;
                            notification.contentView.setTextViewText(R.id.update_msg, "正在下载：一起去吃鸡");
                            // 更改文字
                            notification.contentView.setTextViewText(R.id.update_percent, progress + "%");
                            // 更改进度条
                            notification.contentView.setProgressBar(R.id.update_progress_bar, 100, progress, false);
                            // 发送消息
                            nm.notify(titleId, notification);
                        }
                    }

                    @Override
                    public void onDownloadFailed() {
                        Log.e("AAA", "UpdateVersionService onFailure...");
                        mCurrentProgress = 0;
                        notification.contentView.setTextViewText(R.id.update_msg, "网络异常！请检查网络设置！");
                        // 发送消息
                        nm.notify(titleId, notification);
                    }
                });
    }

    @Override
    public void onDestroy() {
        //下载完成时，清楚该通知，自动安装
        mCurrentProgress = 0;
        nm.cancel(titleId);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
