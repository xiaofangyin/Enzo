package com.enzo.commonlib.widget.noscrollviewpager;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 文 件 名: NoScrollViewPager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/23
 * 邮   箱: xiaofangyinwork@163.com
 */
public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
