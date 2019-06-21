package com.enzo.commonlib.widget.alertdialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.enzo.commonlib.R;

/**
 * 文 件 名: CenterAlertDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofangyinwork@163.com
 */
public class CenterAlertDialog extends Dialog {

    private Builder builder;

    private CenterAlertDialog(Context context, Builder builder) {
        super(context, R.style.BaseDialogTheme);
        this.builder = builder;
        setContentView(R.layout.lib_alert_dialog_center);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        findView();
    }

    private void findView() {
        TextView tvTitle = findViewById(R.id.tv_alert_dialog_title);
        TextView tvContent = findViewById(R.id.tv_alert_dialog_content);
        TextView tvCancel = findViewById(R.id.tv_alert_dialog_cancel);
        TextView tvConfirm = findViewById(R.id.tv_alert_dialog_confirm);

        if (!TextUtils.isEmpty(builder.title)) {
            tvTitle.setText(builder.title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(builder.content)) {
            tvContent.setText(builder.content);
        }
        if (!TextUtils.isEmpty(builder.cancel)) {
            tvCancel.setText(builder.cancel);
        } else {
            tvCancel.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(builder.confirm)) {
            tvConfirm.setText(builder.confirm);
        } else {
            tvConfirm.setVisibility(View.GONE);
        }
        if (builder.gravity != 0) {
            tvContent.setGravity(builder.gravity);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder.alertDialogListener != null) {
                    builder.alertDialogListener.onNegClick();
                }
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder.alertDialogListener != null) {
                    builder.alertDialogListener.onPosClick();
                }
                dismiss();
            }
        });
    }

    public static class Builder {

        private Context context;
        private String title;
        private String content;
        private String cancel;
        private String confirm;
        private int gravity;
        private AlertDialogListener alertDialogListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder cancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder confirm(String confirm) {
            this.confirm = confirm;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder listener(AlertDialogListener listener) {
            this.alertDialogListener = listener;
            return this;
        }

        public CenterAlertDialog build() {
            return new CenterAlertDialog(context, this);
        }
    }

    public interface AlertDialogListener {

        void onNegClick();

        void onPosClick();
    }
}
