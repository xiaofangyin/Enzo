package com.ifenglian.commonlib.widget.view.danmu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ifenglian.commonlib.R;

/**
 * 文 件 名: FLSendDanMuDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/9/24
 * 邮   箱: xiaofy@ifenglian.com
 */
public class FLSendDanMuDialog extends DialogFragment {

    private String textHint;
    private EditText edtDanMu;

    public FLSendDanMuDialog() {

    }

    @SuppressLint("ValidFragment")
    public FLSendDanMuDialog(String textHint, DanMuDialogListener listener) {
        this.textHint = textHint;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog mDialog = new Dialog(getActivity(), R.style.FL_SEND_DAN_MU);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View rootView = View.inflate(getActivity(), R.layout.fl_send_dan_mu_dialog_layout, null);
        mDialog.setContentView(rootView);
        mDialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        edtDanMu = (EditText) rootView.findViewById(R.id.fl_dan_mu_dialog_content);
        edtDanMu.setHint(textHint);
        edtDanMu.setFocusable(true);
        edtDanMu.setFocusableInTouchMode(true);
        edtDanMu.requestFocus();

        rootView.findViewById(R.id.fl_send_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtDanMu.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "输入内容为空", Toast.LENGTH_LONG).show();
                } else {
                    if (mListener != null)
                        mListener.onSendBack(edtDanMu.getText().toString());
                }
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        edtDanMu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mListener != null)
                    mListener.onTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return mDialog;
    }

    public DanMuDialogListener mListener;

    public interface DanMuDialogListener {
        void onSendBack(String inputText);

        void onTextChanged(CharSequence inputTest);
    }
}
