package com.ifenglian.commonlib.utils.updateversion;

import android.content.Context;
import android.content.Intent;

import com.ifenglian.commonlib.utils.common.ApkUtils;
import com.ifenglian.commonlib.utils.common.NetworkUtil;
import com.ifenglian.commonlib.utils.common.SDCardUtils;
import com.ifenglian.commonlib.widget.view.alertdialog.AlertDialogCallBack;
import com.ifenglian.commonlib.widget.view.alertdialog.CenterAlertDialog;

import java.io.File;

public class UpdateVersionUtil {
    public static String DOWN_LOAD_APP_NAME = "enzo-app.apk";

    /**
     * 接口回调
     *
     * @author wenjie
     */
    public interface UpdateListener {
        void onUpdateReturned(int updateStatus, VersionInfo versionInfo);
    }

    /**
     * 本地测试
     */
    public static void localCheckedVersion(final Context context, final UpdateListener updateListener) {
        try {
            VersionInfo mVersionInfo = new VersionInfo();
            mVersionInfo.setDownloadUrl("http://gdown.baidu.com/data/wisegame/57a788487345e938/QQ_358.apk");
//            mVersionInfo.setDownloadUrl("http://p3.exmmw.cn/p1/wq/360yingshidaquan.apk");
            mVersionInfo.setVersionDesc("\n更新内容：\n1、增加xxxxx功能\n2、增加xxxx显示！\n3、用户界面优化！\n4、xxxxxx！");
            mVersionInfo.setVersionCode(2);
            mVersionInfo.setVersionName("v2020");
            mVersionInfo.setVersionSize("20.1M");
            mVersionInfo.setId("1");
            int clientVersionCode = ApkUtils.getVersionCode(context);
            int serverVersionCode = mVersionInfo.getVersionCode();
            //有新版本
            if (clientVersionCode < serverVersionCode) {
                int i = NetworkUtil.checkedNetWorkType(context);
                if (i == NetworkUtil.NOWIFI) {
                    updateListener.onUpdateReturned(UpdateStatus.NOWIFI, mVersionInfo);
                } else if (i == NetworkUtil.WIFI) {
                    updateListener.onUpdateReturned(UpdateStatus.YES, mVersionInfo);
                }
            } else {
                //无新本
                updateListener.onUpdateReturned(UpdateStatus.NO, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            updateListener.onUpdateReturned(UpdateStatus.ERROR, null);
        }
    }


    /**
     * 弹出新版本提示
     *
     * @param context     上下文
     * @param versionInfo 更新内容
     */
    public static void showDialog(final Context context, final VersionInfo versionInfo) {
        final File file = new File(SDCardUtils.getRootDirectory() + "/updateVersion", DOWN_LOAD_APP_NAME);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("最新版本：").append(versionInfo.getVersionName());
        if (file.exists() && file.getName().equals(DOWN_LOAD_APP_NAME)) {
            stringBuilder.append("\n").append("新版本已经下载，是否安装？");
        } else {
            stringBuilder.append("\n").append("新版本大小：").append(versionInfo.getVersionSize());
        }
        new CenterAlertDialog(context, "版本更新", stringBuilder.toString(), "取消", "更新", new AlertDialogCallBack() {
            @Override
            public void onNegClick() {

            }

            @Override
            public void onPosClick() {
                //新版本已经下载
                if (file.exists() && file.getName().equals(DOWN_LOAD_APP_NAME)) {
                    Intent intent = ApkUtils.getInstallIntent(file);
                    context.startActivity(intent);
                } else {
                    //没有下载，则开启服务下载新版本
                    Intent intent = new Intent(context, UpdateVersionService.class);
                    intent.putExtra("downloadUrl", versionInfo.getDownloadUrl());
                    context.startService(intent);
                }
            }
        }).show();
    }

    /**
     * 收起通知栏
     *
     * @param context
     */
    public static void collapseStatusBar(Context context) {
        try {
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
}
