package com.enzo.commonlib.utils.common;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * 文 件 名: ViewUtils
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/5
 * 邮   箱: xiaofywork@163.com
 */
public class ViewUtils {

    public static Activity getActivity(@NonNull View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    /**
     * 获取view的宽高
     */
    public static int[] getViewWidthAndHeight(View contentView) {
        int[] result = new int[2];
        if (contentView.getMeasuredHeight() == 0 || contentView.getMeasuredWidth() == 0) {
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            // 计算contentView的高宽
            int windowHeight = contentView.getMeasuredHeight();
            int windowWidth = contentView.getMeasuredWidth();
            result[0] = windowWidth;
            result[1] = windowHeight;
        }
        return result;
    }
}
