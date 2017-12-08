package com.ifenglian.commonlib.widget.view.alertdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.ifenglian.commonlib.R;

/**
 * 文 件 名: BaseDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        this(context, R.style.BaseDialogTheme);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        if (setWindowAnimation() != 0) {
            getWindow().setWindowAnimations(setWindowAnimation());
        }
        setContentView(bindView());
        findView();
    }

    protected int setWindowAnimation() {
        return 0;
    }

    protected abstract int bindView();

    protected void findView() {

    }

    protected void addExtView(View view) {

    }
}
