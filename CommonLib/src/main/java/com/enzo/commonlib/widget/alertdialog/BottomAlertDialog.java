package com.enzo.commonlib.widget.alertdialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: BottomAlertDialog
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofangyinwork@163.com
 */
public class BottomAlertDialog extends Dialog {

    private Builder mBuilder;
    private LinearLayout llContent;

    private BottomAlertDialog(Context context, Builder builder) {
        super(context, R.style.BaseDialogTheme);
        mBuilder = builder;
        getWindow().setWindowAnimations(R.style.BottomDialogStyle);
        setContentView(R.layout.lib_alert_dialog_bottom);
        configScreenSize(context);
        findView();
    }

    private void findView() {
        llContent = findViewById(R.id.ll_alert_dialog_bottom_layout);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        for (int i = 0; i < mBuilder.mItems.size(); i++) {
            final int j = i;
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtil.dip2px(45));
            layoutParams.setMargins(0, 0,
                    0, DensityUtil.dip2px(0.5f));
            textView.setLayoutParams(layoutParams);
            textView.setBackground(getContext().getResources().getDrawable(R.drawable.lib_selector_white_bg));
            textView.setTextSize(16);
            textView.setTextColor(0xff333333);
            textView.setGravity(Gravity.CENTER);
            textView.setText(mBuilder.mItems.get(i));
            llContent.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (mBuilder.itemClickListener != null) {
                        mBuilder.itemClickListener.onItemClick(j, mBuilder.mItems.get(j));
                    }
                }
            });
        }
        String cancel = TextUtils.isEmpty(mBuilder.mCancel) ? "取消" : mBuilder.mCancel;
        tvCancel.setText(cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void configScreenSize(Context context) {
        DisplayMetrics display = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(display);
        int mScreenWidth = Math.min(display.heightPixels, display.widthPixels);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = mScreenWidth;
        getWindow().setAttributes(lp);
        getWindow().setGravity(Gravity.BOTTOM);// 底部展示
    }

    public void addExtView(View view) {
        llContent.removeAllViews();
        llContent.addView(view);
    }

    public static class Builder {

        private Context mContext;
        private String mCancel;
        private List<String> mItems;
        private OnItemClickListener itemClickListener;

        public Builder(Context context) {
            this.mContext = context;
            mItems = new ArrayList<>();
        }

        public Builder cancel(String cancel) {
            mCancel = cancel;
            return this;
        }

        public Builder add(String item) {
            mItems.add(item);
            return this;
        }

        public Builder listener(OnItemClickListener listener) {
            itemClickListener = listener;
            return this;
        }

        public BottomAlertDialog build() {
            return new BottomAlertDialog(mContext, this);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i, String data);
    }
}
