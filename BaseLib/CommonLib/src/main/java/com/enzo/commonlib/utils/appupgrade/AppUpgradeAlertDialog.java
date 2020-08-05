package com.enzo.commonlib.utils.appupgrade;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.enzo.commonlib.R;

class AppUpgradeAlertDialog extends Dialog {

    private AlertDialogListener onAlertDialogListener;

    AppUpgradeAlertDialog(Context context, String title, String content,
                          String cancel, String confirm) {
        super(context, R.style.BaseDialogTheme);
        setContentView(R.layout.lib_alert_dialog_center);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        findView(title, content, cancel, confirm);
    }

    private void findView(String title, String content, String cancel, String confirm) {
        TextView tvTitle = findViewById(R.id.tv_alert_dialog_title);
        TextView tvContent = findViewById(R.id.tv_alert_dialog_content);
        TextView tvCancel = findViewById(R.id.tv_alert_dialog_cancel);
        TextView tvConfirm = findViewById(R.id.tv_alert_dialog_confirm);

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
        if (!TextUtils.isEmpty(cancel)) {
            tvCancel.setText(cancel);
        } else {
            tvCancel.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(confirm)) {
            tvConfirm.setText(confirm);
        } else {
            tvConfirm.setVisibility(View.GONE);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAlertDialogListener != null) {
                    onAlertDialogListener.onNegClick();
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAlertDialogListener != null) {
                    onAlertDialogListener.onPosClick();
                }
            }
        });
    }

    void setOnAlertDialogListener(AlertDialogListener listener){
        this.onAlertDialogListener = listener;
    }

    public interface AlertDialogListener {

        void onNegClick();

        void onPosClick();
    }
}
