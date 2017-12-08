package com.ifenglian.commonlib.widget.view.alertdialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ifenglian.commonlib.R;

/**
 * 文 件 名: BottomAlertDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public class BottomAlertDialog extends BaseBottomDialog {

    private LinearLayout llContent;

    public BottomAlertDialog(Context context) {
        super(context);
    }

    @Override
    protected int bindView() {
        return R.layout.alert_dialog_bottom;
    }

    @Override
    protected void findView() {
        llContent = (LinearLayout) findViewById(R.id.ll_alert_dialog_bottom_layout);
    }

    @Override
    public void addExtView(View view) {
        llContent.removeAllViews();
        llContent.addView(view);
    }
}
