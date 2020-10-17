package com.enzo.module_d.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.enzo.commonlib.utils.common.LogUtil;

/**
 * 文 件 名: MyViewGroup
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MyViewGroup extends FrameLayout {


    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.d("MyViewGroup dispatchTouchEvent...");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.d("MyViewGroup onInterceptTouchEvent...");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("MyViewGroup onTouchEvent...");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            default:
                return false;
        }
    }
}
