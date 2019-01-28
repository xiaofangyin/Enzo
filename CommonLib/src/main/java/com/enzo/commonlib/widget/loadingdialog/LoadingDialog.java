package com.enzo.commonlib.widget.loadingdialog;

import android.app.Dialog;
import android.content.Context;

import com.enzo.commonlib.R;

public class LoadingDialog {

    private static volatile Dialog dialog;

    public static void show(Context context) {
        try {
            if (context != null) {
                if (dialog == null) {
                    synchronized (LoadingDialog.class) {
                        if (dialog == null) {
                            dialog = new Dialog(context, R.style.LoadingDialogStyle);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.loading_progress_view);
                        }
                    }
                }
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
