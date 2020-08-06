package com.enzo.commonlib.utils.toast;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class ToastUtil {

    private static Toast sToast;
    private static String sText;
    private static Application sAppContext;

    private ToastUtil() {

    }

    public static void initialize(Application context) {
        sAppContext = context;
    }

    private static volatile Handler sHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NotNull Message msg) {
            super.handleMessage(msg);
            if (sText != null && !TextUtils.isEmpty(sText.trim()) && !"null".equals(sText)) {
                int timeLength = msg.what;
                if (null != sToast) {
                    sToast.cancel();
                }
                if (sAppContext != null) {
                    sToast = ToastCompat.makeText(sAppContext, sText, timeLength);
                    sToast.show();
                }
            }
        }
    };
    private static Runnable r = new Runnable() {
        public void run() {
            if (null != sToast) {
                sToast.cancel();
                sToast = null;
            }
        }
    };

    public static void show(final String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void show(final int msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void show(final int msg, final int length) {
        if (sAppContext != null) {
            show(sAppContext.getString(msg), length);
        }
    }

    private static void show(String text, int length) {
        sHandler.removeCallbacks(r);
        ToastUtil.sText = text;
        sHandler.sendEmptyMessage(length);
        sHandler.postDelayed(r, 5000);
    }

    public static void cancelToast() {
        if (null != sToast) {
            sToast.cancel();
            sToast = null;
        }
    }
}
