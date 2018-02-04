package com.ifenglian.commonlib.widget.scrollview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 文 件 名: XinOuterLinearLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SGLOuterLinearLayout extends LinearLayout {

    private RecyclerView recyclerView;
    private int downY; // 按下时
    private View topView;
    private View secondView;
    private boolean isTopViewShow = true;
    private boolean animating;

    public SGLOuterLinearLayout(Context context) {
        super(context);
    }

    public SGLOuterLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SGLOuterLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        Log.e("AAA", "layoutParams.height: " + recyclerView.getMeasuredHeight() + "..." + topView.getMeasuredHeight());
        layoutParams.height = recyclerView.getMeasuredHeight() + topView.getMeasuredHeight();
        recyclerView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = getChildAt(0);
        secondView = getChildAt(1);
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
                    if (isTopViewShow && (currY - downY) < 0) {
                        animating = true;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topView, "translationY", 0, -topView.getHeight());
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(secondView, "translationY", 0, -topView.getHeight());
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(recyclerView, "translationY", 0, -topView.getHeight());
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(objectAnimator, objectAnimator2, objectAnimator3);
                        set.setDuration(300);
                        set.start();
                        isTopViewShow = !isTopViewShow;
                    } else if (!isTopViewShow && (currY - downY) > 0) {
                        animating = true;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topView, "translationY", -topView.getHeight(), 0);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(secondView, "translationY", -topView.getHeight(), 0);
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(recyclerView, "translationY", -topView.getHeight(), 0);
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(objectAnimator, objectAnimator2, objectAnimator3);
                        set.setDuration(300);
                        set.start();
                        isTopViewShow = !isTopViewShow;
                    }
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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("AAA", "onInterceptTouchEvent...");
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (y - downY > 0) {
                    if (SARecyclerUtil.isRecyclerViewToTop(recyclerView) && !isTopViewShow) {
                        Log.e("AAA", "y - mLastY > 0 isTopViewShow: " + isTopViewShow);
                        return true;
                    }
                }

                if (y - downY < 0) {
                    if (SARecyclerUtil.isRecyclerViewToTop(recyclerView) && isTopViewShow) {
                        Log.e("AAA", "y - mLastY < 0 isTopViewShow: " + isTopViewShow);
                        return true;
                    }
                }
                break;
        }

        return false;
    }
}
