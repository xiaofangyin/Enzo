package com.ifenglian.module_d.view.touchevent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 文 件 名: MDTouchEventView
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/4/6
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDTouchEventView extends View {

    public MDTouchEventView(Context context) {
        super(context);
    }

    public MDTouchEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MDTouchEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("AAA", "MDTouchEventView dispatchTouchEvent..." + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("AAA", "MDTouchEventView onTouchEvent down 000");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e("AAA", "MDTouchEventView onTouchEvent move 222");
                return true;
            case MotionEvent.ACTION_UP:
                Log.e("AAA", "MDTouchEventView onTouchEvent up 111");
                break;
        }
        return super.onTouchEvent(event);
    }
}
