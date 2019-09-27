package com.enzo.commonlib.utils.zxing.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.KeyboardUtils;

/**
 * 文 件 名: BarCordQueryDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/5/13
 * 邮   箱: xiaofangyinwork@163.com
 */
public class BarCordQueryDialog extends DialogFragment {

    private EditText edtBarCode;
    private String hint = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            hint = bundle.getString("hint", "");
        }

        Dialog mDialog = new Dialog(getActivity(), R.style.CommentDialogStyle);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View rootView = View.inflate(getActivity(), R.layout.layout_bar_cord_query_dialog, null);
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
        edtBarCode = rootView.findViewById(R.id.edt_query_content);
        edtBarCode.setHint(hint);
        edtBarCode.setFocusable(true);
        edtBarCode.setFocusableInTouchMode(true);
        edtBarCode.requestFocus();

        rootView.findViewById(R.id.tv_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtBarCode.getText().toString().trim())) {
                    Toast.makeText(BarCordQueryDialog.this.getActivity(), "输入内容为空", Toast.LENGTH_LONG).show();
                } else {
                    if (queryListener != null)
                        queryListener.onQuery(edtBarCode.getText().toString());
                }
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (queryListener != null)
                            queryListener.onDismiss();
                        KeyboardUtils.dismiss(BarCordQueryDialog.this.getActivity());
                    }
                }, 100);
            }
        });

        edtBarCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (queryListener != null)
                    queryListener.onTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return mDialog;
    }

    public OnQueryCallBack queryListener;

    public void setOnQueryCallBack(OnQueryCallBack listener) {
        queryListener = listener;
    }

    public interface OnQueryCallBack {
        void onQuery(String inputText);

        void onTextChanged(CharSequence inputTest);

        void onDismiss();
    }
}
