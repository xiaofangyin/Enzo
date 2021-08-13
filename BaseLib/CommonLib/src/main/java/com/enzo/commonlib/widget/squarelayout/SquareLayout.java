package com.enzo.commonlib.widget.squarelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 文 件 名: SquareLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SquareLayout extends RelativeLayout {

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
