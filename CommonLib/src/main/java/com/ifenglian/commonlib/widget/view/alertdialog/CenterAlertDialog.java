package com.ifenglian.commonlib.widget.view.alertdialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifenglian.commonlib.R;

/**
 * 文 件 名: CenterAlertDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public class CenterAlertDialog extends Dialog {

    private LinearLayout llContent;
    private String mTitle, mContent, mCancel, mConfirm;
    private AlertDialogCallBack mCallBack;

    public CenterAlertDialog(Context context, String title, String content, String cancel, String confirm, AlertDialogCallBack callBack) {
        super(context, R.style.BaseDialogTheme);
        setContentView(R.layout.alert_dialog_center);
        configScreenSize(context);
        mTitle = title;
        mContent = content;
        mCancel = cancel;
        mConfirm = confirm;
        if (callBack != null) {
            mCallBack = callBack;
        }
        findView();
    }

    private void findView() {
        llContent = findViewById(R.id.ll_alert_dialog_content_layout);
        TextView tvTitle = findViewById(R.id.tv_alert_dialog_title);
        TextView tvContent = findViewById(R.id.tv_alert_dialog_content);
        TextView tvCancel = findViewById(R.id.tv_alert_dialog_cancel);
        TextView tvConfirm = findViewById(R.id.tv_alert_dialog_confirm);

        tvTitle.setText(mTitle);
        tvContent.setText(mContent);
        tvCancel.setText(mCancel);
        tvConfirm.setText(mConfirm);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    dismiss();
                    mCallBack.onNegClick();
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    dismiss();
                    mCallBack.onPosClick();
                }
            }
        });
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

    public void addExtView(View view) {
        llContent.removeAllViews();
        llContent.addView(view);
    }
}
