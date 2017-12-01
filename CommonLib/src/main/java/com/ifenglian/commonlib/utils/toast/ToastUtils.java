package com.ifenglian.commonlib.utils.toast;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.ifenglian.commonlib.base.BaseApplication;

/**
 * 文 件 名: ToastUtils
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class ToastUtils {

    private static Toast toast;

    private static View view;

    private ToastUtils() {
    }

    private static void getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        if (view == null) {
            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
        }
        toast.setView(view);
    }

    public static void showShortToast(CharSequence msg) {
        showToast(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(int resId) {
        showToast(BaseApplication.getInstance(), resId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(CharSequence msg) {
        showToast(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(int resId) {
        showToast(BaseApplication.getInstance(), resId, Toast.LENGTH_LONG);
    }

    private static void showToast(Context context, CharSequence msg, int duration) {
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showToast(Context context, int resId, int duration) {
        try {
            if (resId == 0) {
                return;
            }
            getToast(context);
            toast.setText(resId);
            toast.setDuration(duration);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
