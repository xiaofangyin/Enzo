package com.ifenglian.module_d.view.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * 文 件 名: MDViewGroup1
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/4/6
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewGroupA extends RelativeLayout{

    public MDViewGroupA(Context context) {
        super(context);
    }

    public MDViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MDViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        Log.e("AAA","MDViewGroupA dispatchHoverEvent...");
        return super.dispatchHoverEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("AAA","MDViewGroupA onInterceptTouchEvent...");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("AAA","MDViewGroupA onTouchEvent...");
        return super.onTouchEvent(event);
    }
}
