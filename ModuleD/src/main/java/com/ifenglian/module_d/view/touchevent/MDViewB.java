package com.ifenglian.module_d.view.touchevent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 文 件 名: MDViewB
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/4/6
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewB extends View{

    public MDViewB(Context context) {
        super(context);
    }

    public MDViewB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MDViewB(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        Log.e("AAA","MDViewB dispatchHoverEvent...");
        return super.dispatchHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("AAA","MDViewB onTouchEvent...");
        return super.onTouchEvent(event);
    }
}
