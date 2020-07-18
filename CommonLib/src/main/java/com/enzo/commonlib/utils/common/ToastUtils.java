package com.enzo.commonlib.utils.common;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.dtoast.DToast;

/**
 * https://github.com/Dovar66/DToast
 */
public class ToastUtils {

    /**
     * 使用默认布局
     */
    public static void showToast(Context context, int msg) {
        showToast(context, context.getString(msg));
    }

    public static void showToast(Context context, String msg) {
        if (msg == null) return;
        DToast.make(context)
                .setText(R.id.tv_content_default, msg)
                .setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, DensityUtil.dip2px(context, 50))
                .show();
    }

    /**
     * 通过setView()设置自定义的Toast布局
     */
    public static void showAtCenter(Context context, String msg) {
        if (msg == null) return;
        DToast.make(context)
                .setView(View.inflate(context, R.layout.lib_layout_toast_center, null))
                .setText(R.id.tv_content_custom, msg)
                .setGravity(Gravity.CENTER, 0, 0)
                .showLong();
    }

    //退出APP时调用
    public static void cancelAll() {
        DToast.cancel();
    }
}
