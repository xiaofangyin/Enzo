package com.ifenglian.commonlib.utils.updateversion;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.utils.common.PreferenceUtils;
import com.ifenglian.commonlib.utils.common.SDCardUtils;

import java.io.File;

/**
 * @author wenjie
 *         下载新版本的服务类
 */
public class UpdateVersionService extends Service {

    private NotificationManager nm;
    private Notification notification;
    //标题标识
    private int titleId = 0;
    //安装文件
    private File updateFile;

    private long initTotal = 0;//文件的总长度

    @Override
    public void onCreate() {
        super.onCreate();

        updateFile = new File(SDCardUtils.getRootDirectory() + "/updateVersion",UpdateVersionUtil.DOWN_LOAD_APP_NAME);

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = "开始下载";
        notification.when = System.currentTimeMillis();
        notification.contentView = new RemoteViews(getPackageName(), R.layout.notifycation);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("downloadUrl");

        PreferenceUtils.setString(UpdateVersionService.this, "apkDownloadurl", url);

        nm.notify(titleId, notification);
        downLoadFile(url);
        return super.onStartCommand(intent, flags, startId);
    }

    public void downLoadFile(String url) {
//        MyOkHttp.get().download(url, SDCardUtils.getRootDirectory() + "/updateVersion", UpdateVersionUtil.DOWN_LOAD_APP_NAME, new DownloadResponseHandler() {
//            @Override
//            public void onFinish(File download_file) {
//                // 更改文字
//                notification.contentView.setTextViewText(R.id.msg, "下载完成!点击安装");
//                // 发送消息
//                nm.notify(0, notification);
//                stopSelf();
//                //收起通知栏
//                UpdateVersionUtil.collapseStatusBar(UpdateVersionService.this);
//                //自动安装新版本
//                Intent installIntent = ApkUtils.getInstallIntent(updateFile);
//                startActivity(installIntent);
//            }
//
//            @Override
//            public void onProgress(long currentBytes, long totalBytes) {
//                if (initTotal == 0) {//说明第一次开始下载
//                    initTotal = totalBytes;
//                }
//                if (initTotal != totalBytes) {//说明下载过程中暂停过，文件的总长度出现问题  就把初始的文件的长度赋值给他重新计算已经下载的比例
//                    totalBytes = initTotal;
//                }
//                long l = currentBytes * 100 / totalBytes;
//                notification.contentView.setTextViewText(R.id.msg, "正在下载：一起去吃鸡");
//                // 更改文字
//                notification.contentView.setTextViewText(R.id.bartext, l + "%");
//                // 更改进度条
//                notification.contentView.setProgressBar(R.id.progressBar1, 100, (int) l, false);
//                // 发送消息
//                nm.notify(0, notification);
//            }
//
//            @Override
//            public void onFailure(String error_msg) {
//                notification.contentView.setTextViewText(R.id.msg, "网络异常！请检查网络设置！");
//                // 发送消息
//                nm.notify(0, notification);
//            }
//        });
    }

    @Override
    public void onDestroy() {
        //下载完成时，清楚该通知，自动安装
        nm.cancel(titleId);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
