package com.enzo.commonlib.utils.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.widget.shadow.ShadowConfig;
import com.enzo.commonlib.widget.shadow.ShadowHelper;

public class ToastCompat {

    @SuppressLint("ShowToast,InflateParams")
    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast toast;
        if (Build.VERSION.SDK_INT == 25) {
            SafeToastContext safeToastContext = new SafeToastContext(context);
            toast = Toast.makeText(safeToastContext, text, duration);
        } else {
            toast = Toast.makeText(context, text, duration);
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            //自定义布局
            View view = inflater.inflate(R.layout.lib_layout_toast, null);

            ShadowConfig.Builder config = new ShadowConfig.Builder()
                    .setColor(ContextCompat.getColor(context, R.color.color_dark_black))
                    .setShadowColor(ContextCompat.getColor(context, R.color.color_dark_black))
                    .setOffsetX(DensityUtil.dip2px(context, 0f))
                    .setOffsetY(DensityUtil.dip2px(context, 0f));
            ShadowHelper.setShadowBgForView(view, config);

            //自定义toast文本
            TextView toastTv = view.findViewById(R.id.tv_content_default);
            toastTv.setText(text);
            toast.setView(view);
        }
        return toast;
    }
}
