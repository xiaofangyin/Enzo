package com.ifenglian.commonlib.widget.scrollview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 文 件 名: XinOuterLinearLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class XinOuterLinearLayout extends LinearLayout {

    private ListView listView;
    private int downX, downY; // 按下时
    private int currX, currY; // 移动时
    private int moveY; // 从按下到移动的Y距离
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
        listView = (ListView) getChildAt(2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("AAA", "父类消费事件。。。");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                currX = (int) ev.getX();
                currY = (int) ev.getY();
                moveY = Math.abs(currY - downY);
                if (currY == downY) {
                    break;
                }
                // 垂直滑动
                if (moveY > Math.abs(currX - downX)) {
                    Log.e("AAA", "currX - downX: " + (currY - downY));
                    if (!isShow && !animating && (currY - downY) < 0) {
                        animating = true;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationY", 0, -topView.getHeight());
                        objectAnimator.setDuration(300);
                        objectAnimator.start();
                        objectAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animating = false;
                                isShow = !isShow;
                            }
                        });
                    } else if (isShow && !animating && (currY - downY) > 0) {
                        animating = true;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationY", -topView.getHeight(), 0);
                        objectAnimator.setDuration(300);
                        objectAnimator.start();
                        objectAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animating = false;
                                isShow = !isShow;
                            }
                        });
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
