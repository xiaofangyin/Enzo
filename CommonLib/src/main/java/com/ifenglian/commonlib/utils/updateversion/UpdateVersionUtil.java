package com.ifenglian.commonlib.utils.updateversion;

import android.content.Context;
import android.content.Intent;

import com.ifenglian.commonlib.widget.view.alertdialog.AlertDialogCallBack;
import com.ifenglian.commonlib.widget.view.alertdialog.CenterAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
 * @author wenjie 版本更新的工具类
 */
public class UpdateVersionUtil {

    /**
     * 接口回调
     *
     * @author wenjie
     */
    public interface UpdateListener {
        void onUpdateReturned(int updateStatus, VersionInfo versionInfo);
    }

    /**
     * 网络测试 检测版本
     *
     * @param context 上下文
     */
    public static void checkVersion(final Context context, final UpdateListener updateListener) {
        HttpRequest.get(ServerReqAddress.UPDATA_VERSION_REQ, new HttpRequest.RequestCallBackListener() {

            @Override
            public void onSuccess(String resultData) {
                try {
                    JSONObject jsonObject = JsonUtil.stringToJson(resultData);
                    JSONArray array = jsonObject.getJSONArray("data");
                    VersionInfo mVersionInfo = JsonUtil.jsonToBean(array.getJSONObject(0).toString(), VersionInfo.class);
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

            @Override
            public void onFailure(String error) {
                updateListener.onUpdateReturned(UpdateStatus.TIMEOUT, null);
            }
        });
    }


    /**
     * 本地测试
     */
    public static void localCheckedVersion(final Context context, final UpdateListener updateListener) {
        try {
//			JSONObject jsonObject = JsonUtil.stringToJson(resultData);
//			JSONArray array = jsonObject.getJSONArray("data");
//			VersionInfo mVersionInfo = JsonUtil.jsonToBean(array.getJSONObject(0).toString(), VersionInfo.class);
            VersionInfo mVersionInfo = new VersionInfo();
            mVersionInfo.setDownloadUrl("http://gdown.baidu.com/data/wisegame/57a788487345e938/QQ_358.apk");
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
        final File file = new File(SDCardUtils.getRootDirectory() + "/updateVersion/gdmsaec-app.apk");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("最新版本：").append(versionInfo.getVersionName());
        if (file.exists() && file.getName().equals("gdmsaec-app.apk")) {
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
                if (file.exists() && file.getName().equals("gdmsaec-app.apk")) {
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
