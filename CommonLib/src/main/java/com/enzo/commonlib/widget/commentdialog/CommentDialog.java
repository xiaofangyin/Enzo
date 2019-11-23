package com.enzo.commonlib.widget.commentdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enzo.commonlib.R;

/**
 * 文 件 名: UGCVHHomeTopPostDetail
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/5/13
 * 邮   箱: xiaofangyinwork@163.com
 */
public class CommentDialog extends DialogFragment {

    private EditText edtComment;
    private LinearLayout llSendLayout;
    private ImageView ivSendIcon;
    private TextView tvSendText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog mDialog = new Dialog(getActivity(), R.style.CommentDialogStyle);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View rootView = View.inflate(getActivity(), R.layout.lib_layout_comment_dialog, null);
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
        edtComment = rootView.findViewById(R.id.dialog_comment_content);
        edtComment.setFocusable(true);
        edtComment.setFocusableInTouchMode(true);
        edtComment.requestFocus();

        llSendLayout = rootView.findViewById(R.id.ugc_post_detail_send_layout);
        llSendLayout.setEnabled(false);
        ivSendIcon = rootView.findViewById(R.id.ugc_post_detail_send_icon);
        tvSendText = rootView.findViewById(R.id.ugc_post_detail_send_text);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String hint = bundle.getString("hint", "");
            if (!TextUtils.isEmpty(hint)) {
                edtComment.setHint(hint);
            }
        }

        llSendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtComment.getText().toString().trim())) {
                    Toast.makeText(CommentDialog.this.getActivity(), "输入内容为空", Toast.LENGTH_LONG).show();
                } else {
                    if (commentListener != null)
                        commentListener.onSendBack(edtComment.getText().toString());
                }
            }
        });

        edtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (commentListener != null)
                    commentListener.onTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    llSendLayout.setEnabled(false);
                    ivSendIcon.setImageResource(R.mipmap.icon_comment_disable);
                    tvSendText.setTextColor(getResources().getColor(R.color.color_d9));
                } else {
                    llSendLayout.setEnabled(true);
                    ivSendIcon.setImageResource(R.mipmap.icon_comment_enable);
                    tvSendText.setTextColor(getResources().getColor(R.color.color_green));
                }

            }
        });
        return mDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (commentListener != null) {
            commentListener.onDismiss();
        }
    }

    public CommentDialogListener commentListener;

    public void setOnCommentDialogListener(CommentDialogListener listener) {
        commentListener = listener;
    }

    public interface CommentDialogListener {
        void onSendBack(String inputText);

        void onTextChanged(CharSequence inputTest);

        void onDismiss();
    }
}
