package com.ifenglian.commonlib.widget.view.alertdialog;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 文 件 名: BaseCenterDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public abstract class BaseCenterDialog extends BaseDialog {

    public BaseCenterDialog(Context context) {
        super(context);
        configScreenSize(context);
    }

    @Override
    protected int setWindowAnimation() {
        return 0;
    }

    private void configScreenSize(Context context) {
        setCanceledOnTouchOutside(false);
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int pxDensityScale = (int) (metrics.widthPixels / metrics.density);
        if (p.width <= 720 || pxDensityScale <= 400) {
            p.width = (int) (metrics.widthPixels * 0.8f);
        } else {
            p.width = 610;
        }
        getWindow().setAttributes(p);
    }
}
