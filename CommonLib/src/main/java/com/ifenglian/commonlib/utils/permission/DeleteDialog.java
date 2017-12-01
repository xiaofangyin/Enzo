package com.ifenglian.commonlib.utils.permission;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ifenglian.commonlib.R;

public class DeleteDialog {
    private Context mContext;
    private Dialog mDialog;
    private TextView tv_content;
    private onConfirmListener listener;

    public DeleteDialog(Context context, onConfirmListener listener) {
        this.mContext = context;
        this.listener = listener;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog2);

        View mDialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_delete, null);
        Button btn_confirm = mDialogContentView.findViewById(R.id.btn_dialog_delete_comfrim);
        tv_content = mDialogContentView.findViewById(R.id.tv_dialog_delete);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != listener) {
                    listener.confirm();
                }
            }
        });
        mDialog.setCancelable(false);//不能点外面取消,也不 能点back取消
        mDialog.setContentView(mDialogContentView);
    }

    public void show() {
        mDialog.show();

    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public interface onConfirmListener {
        void confirm();

    }

    public void setTvContent(String content) {
        tv_content.setText(content);
    }
}
