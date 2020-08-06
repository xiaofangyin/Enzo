package com.enzo.commonlib.utils.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enzo.commonlib.R;

public class ToastCompat {

    @SuppressLint("ShowToast")
    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast toast;
        if (Build.VERSION.SDK_INT == 25) {
            SafeToastContext safeToastContext = new SafeToastContext(context);
            toast = Toast.makeText(safeToastContext, text, duration);
        }else{
            toast = Toast.makeText(context, text, duration);
        }
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater != null) {
            //自定义布局
            View view = inflater.inflate(R.layout.lib_layout_toast, null);
            //自定义toast文本
            TextView toastTv = (TextView) view.findViewById(R.id.tv_content_default);
            toastTv.setText(text);
            toast.setView(view);
        }
        return toast;
    }
}
