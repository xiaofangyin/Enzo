package com.ifenglian.commonlib.widget.view.alertdialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import com.ifenglian.commonlib.R;

/**
 * 文 件 名: BaseBottomDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public abstract class BaseBottomDialog extends BaseDialog {

    public BaseBottomDialog(Context context) {
        super(context);
        configScreenSize(context);
    }

    @Override
    protected int setWindowAnimation() {
        return R.style.photo_choose_dialog_anim_style;
    }

    private void configScreenSize(Context context) {
        DisplayMetrics display = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(display);
        int mScreenWidth = Math.min(display.heightPixels, display.widthPixels);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = mScreenWidth;
        getWindow().setAttributes(lp);
        getWindow().setGravity(Gravity.BOTTOM);// 底部展示
    }
}
