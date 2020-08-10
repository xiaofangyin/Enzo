package com.enzo.commonlib.utils.appupgrade;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.enzo.commonlib.R;

public class AppUpgradeDialog extends AppCompatDialog implements View.OnClickListener {

    private OnAlertViewClickListener mButtonClick;

    public AppUpgradeDialog(Context context, String version, String content, OnAlertViewClickListener buttonClick) {
        super(context, R.style.BaseDialogTheme);
        init(context, version, content, buttonClick);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void init(final Context context, String title, final String content, OnAlertViewClickListener buttonClick) {
        this.mButtonClick = buttonClick;
        View v = LayoutInflater.from(context).inflate(R.layout.lib_app_update_dialog, null);
        TextView tvTitle = v.findViewById(R.id.app_update_version_text);
        TextView tvContent = v.findViewById(R.id.app_update_content_text);
        TextView tvNegButton = v.findViewById(R.id.app_update_later_button);//暂不更新
        TextView tvPosButton = v.findViewById(R.id.app_update_right_now_button);//立即更新
        if (title != null) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (content != null) {
            tvContent.setText(content);
            tvContent.setVisibility(View.VISIBLE);
        }

        tvPosButton.setOnClickListener(this);
        tvNegButton.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setContentView(v);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.app_update_later_button) {
            dismiss();
            if (mButtonClick != null) {
                mButtonClick.onNegBtnClick();
            }
        } else if (id == R.id.app_update_right_now_button) {
            dismiss();
            if (mButtonClick != null) {
                mButtonClick.onPosBtnClick();
            }
        }
    }

    public interface OnAlertViewClickListener {

        void onNegBtnClick();

        void onPosBtnClick();

    }
}
