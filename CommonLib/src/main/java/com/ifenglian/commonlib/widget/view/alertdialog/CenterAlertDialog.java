package com.ifenglian.commonlib.widget.view.alertdialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ifenglian.commonlib.R;

/**
 * 文 件 名: CenterAlertDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public class CenterAlertDialog extends BaseCenterDialog {

    private LinearLayout llContent;

    public CenterAlertDialog(Context context) {
        super(context);
    }

    @Override
    protected int bindView() {
        return R.layout.alert_dialog_center;
    }

    @Override
    protected void findView() {
        llContent = (LinearLayout) findViewById(R.id.ll_alert_dialog_content_layout);
    }

    @Override
    public void addExtView(View view){
        llContent.removeAllViews();
        llContent.addView(view);
    }

    @Override
    protected int[] setClickIDs() {
        return new int[]{R.id.tv_alert_dialog_cancel, R.id.tv_alert_dialog_confirm};
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_alert_dialog_cancel) {
            dismiss();


        } else if (i == R.id.tv_alert_dialog_confirm) {
            dismiss();


        }
    }
}
