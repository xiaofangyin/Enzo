package com.enzo.commonlib.widget.nopreloadviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 文 件 名: NoScrollViewPager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class NoScrollViewPager extends NoPreloadViewPager {

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
