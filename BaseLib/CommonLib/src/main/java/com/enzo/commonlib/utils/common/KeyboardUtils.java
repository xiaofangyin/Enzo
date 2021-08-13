package com.enzo.commonlib.utils.common;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 文 件 名: KeyboardUtils
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/2
 * 邮   箱: xiaofangyinwork@163.com
 */
public class KeyboardUtils {

    public static void show(Context context) {
        if (context == null) {
            return;
        }
        //显示键盘
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void dismiss(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {

        }
    }

    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void toggleSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, 0);
        }
    }
}