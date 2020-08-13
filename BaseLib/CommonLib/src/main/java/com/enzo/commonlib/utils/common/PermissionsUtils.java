package com.enzo.commonlib.utils.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

import com.enzo.commonlib.widget.alertdialog.CenterAlertDialog;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.Objects;

import rx.functions.Action1;

/**
 * 文 件 名: PermissionsUtils
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/12
 * 邮   箱: xiaofywork@163.com
 */
public class PermissionsUtils {

    /**
     * 判断是否有通知权限
     *
     * @return {@code true}: 有<br>{@code false}: 没有
     */
    public static boolean areNotificationsEnabled(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，
        // 低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        return manager.areNotificationsEnabled();
    }

    /**
     * 显示通知授权弹窗
     */
    public static void showEnableNotificationsDialog(final Context context) {
        new CenterAlertDialog.Builder(context)
                .title("开启通知权限")
                .content("请开启".concat(Objects.requireNonNull(ApkUtils.getAppName(context))).concat("的通知权限，获取实时信息"))
                .cancel("取消")
                .confirm("设置")
                .listener(new CenterAlertDialog.AlertDialogListener() {
                    @Override
                    public void onNegClick() {

                    }

                    @Override
                    public void onPosClick() {
                        gotoNotificationsSettingPage(context);
                    }
                })
                .build()
                .show();
    }

    /**
     * 跳转通知权限页面
     */
    private static void gotoNotificationsSettingPage(Context context) {
        try {
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);
            } else {
                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.putExtra("app_package", context.getPackageName());
                intent.putExtra("app_uid", context.getApplicationInfo().uid);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
            //下面这种方案是直接跳转到当前应用的设置界面。
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }

    /**
     * 请求读写sd卡权限
     */
    public static void requestExternalStoragePermission(Activity activity, final OnCheckCallback checkResult) {
        new RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (checkResult != null) {
                            checkResult.granted(aBoolean);
                        }
                    }
                });
    }

    /**
     * 请求相机权限
     */
    public static void requestCameraPermission(Activity activity, final OnCheckCallback checkResult) {
        new RxPermissions(activity).request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (checkResult != null) {
                            checkResult.granted(aBoolean);
                        }
                    }
                });
    }

    public interface OnCheckCallback {
        void granted(boolean granted);
    }
}
