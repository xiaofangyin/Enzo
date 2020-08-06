package com.enzo.commonlib.utils.toast;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class ToastUtil {

    static final int MSG_POST_CHAR_SEQUENCE = 2;

    private static Toast sToast;
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
            if (null != sToast) {
                sToast.cancel();
            }
            if (msg.what == MSG_POST_CHAR_SEQUENCE) {
                ToastInfo info = (ToastInfo) msg.obj;
                if (sAppContext != null && info != null && !TextUtils.isEmpty(info.text)) {
                    sToast = ToastCompat.makeText(sAppContext, info.text, info.length);
                    sToast.show();
                }
            }
        }
    };

    public static void show(final String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void show(final String msg, final int length) {
        showToast(msg, length);
    }

    public static void show(final int msg) {
        if (sAppContext != null) {
            showToast(sAppContext.getString(msg), Toast.LENGTH_SHORT);
        }
    }

    public static void show(final int msg, final int length) {
        if (sAppContext != null) {
            showToast(sAppContext.getString(msg), length);
        }
    }

    private static void showToast(String text, int length) {
        sHandler.removeCallbacks(runnable);
        final ToastInfo info = new ToastInfo();
        info.text = text;
        info.length = length;
        sHandler.obtainMessage(MSG_POST_CHAR_SEQUENCE, info).sendToTarget();
        sHandler.postDelayed(runnable, 5000);
    }

    private static Runnable runnable = new Runnable() {
        public void run() {
            cancelToast();
        }
    };

    public static void cancelToast() {
        if (null != sToast) {
            sToast.cancel();
            sToast = null;
        }
    }
}
