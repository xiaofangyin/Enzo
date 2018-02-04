package com.ifenglian.commonlib.widget.scrollview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 文 件 名: XinOuterLinearLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class XinOuterLinearLayout extends LinearLayout {

    private RecyclerView recyclerView;
    private int downY; // 按下时
    private View topView;
    private boolean isShow;
    private boolean animating;

    public XinOuterLinearLayout(Context context) {
        super(context);
    }

    public XinOuterLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XinOuterLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = getChildAt(0);
        recyclerView = (RecyclerView) getChildAt(2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("AAA", "onTouchEvent...");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int currY = (int) ev.getY();
                if (currY == downY) {
                    break;
                }
                // 垂直滑动
                if (!animating) {
                    if (!isShow && (currY - downY) < 0) {
                        animating = true;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationY", 0, -topView.getHeight());
                        objectAnimator.setDuration(300);
                        objectAnimator.start();
                    } else if (isShow && (currY - downY) > 0) {
                        animating = true;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationY", -topView.getHeight(), 0);
                        objectAnimator.setDuration(300);
                        objectAnimator.start();
                    }
                    isShow = !isShow;
                }
                downY = currY;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                animating = false;
                break;
        }
        return true;
    }

    int mLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("AAA", "onInterceptTouchEvent...");
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (y - mLastY > 0) {
                    if (SARecyclerUtil.isRecyclerViewToTop(recyclerView) && isShow) {
                        Log.e("AAA", "y - mLastY > 0 isShow: " + isShow);
                        return true;
                    }
                }

                if (y - mLastY < 0) {
                    if (SARecyclerUtil.isRecyclerViewToTop(recyclerView) && !isShow) {
                        Log.e("AAA", "y - mLastY < 0 isShow: " + isShow);
                        return true;
                    }
                }
                break;
        }

        return false;
    }
}
