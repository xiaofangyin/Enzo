package com.enzo.commonlib.widget.rolltextview;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 文 件 名: RollTextView
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class RollTextView extends AppCompatTextView {
    public RollTextView(Context context) {
        super(context);
    }

    public RollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}