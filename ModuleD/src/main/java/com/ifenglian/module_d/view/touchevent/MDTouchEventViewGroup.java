package com.ifenglian.module_d.view.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * 文 件 名: MDTouchEventViewGroup
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/4/6
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDTouchEventViewGroup extends RelativeLayout{

    public MDTouchEventViewGroup(Context context) {
        super(context);
    }

    public MDTouchEventViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MDTouchEventViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("AAA","MDTouchEventViewGroup dispatchTouchEvent..." + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("AAA","MDTouchEventViewGroup onInterceptTouchEvent..." + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("AAA","MDTouchEventViewGroup onTouchEvent..." + event.getAction());
        return super.onTouchEvent(event);
    }
}
