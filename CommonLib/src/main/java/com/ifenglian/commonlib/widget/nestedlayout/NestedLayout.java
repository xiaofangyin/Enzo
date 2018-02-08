package com.ifenglian.commonlib.widget.nestedlayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ifenglian.commonlib.utils.common.LogUtil;
import com.nineoldandroids.view.ViewHelper;

/**
 * 文 件 名: SGLOuterLinearLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class NestedLayout extends LinearLayout {

    private ListView listView;
    private View topView, secondView;
    private boolean isTopViewShow = true;
    private boolean animating;
    private int downY;
    private int mHeight;

    public NestedLayout(Context context) {
        super(context);
    }

    public NestedLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtil.d("onFinishInflate...");
        topView = getChildAt(0);
        secondView = getChildAt(1);
        listView = (ListView) getChildAt(2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.d("onSizeChanged...");
        mHeight = h;
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - secondView.getMeasuredHeight();
        listView.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtil.d("onTouchEvent...");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int currY = (int) ev.getY();
                if (currY == downY) {
                    break;
                }
                if (!animating) {
                    if (isTopViewShow && (currY - downY) < -20 && intercept()) {//向上滑
                        animating = true;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topView, "translationY", 0, -topView.getHeight());
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(secondView, "translationY", 0, -topView.getHeight());
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(listView, "translationY", 0, -topView.getHeight());
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(objectAnimator, objectAnimator2, objectAnimator3);
                        set.setDuration(300);
                        set.start();
                        isTopViewShow = !isTopViewShow;
                    } else if (!isTopViewShow && (currY - downY) > 20) {//向下滑
                        animating = true;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topView, "translationY", -topView.getHeight(), 0);
                        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(secondView, "translationY", -topView.getHeight(), 0);
                        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(listView, "translationY", -topView.getHeight(), 0);
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
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (y - downY < 0) {
                    if (listView.getChildAt(0).getTop() == 0 && isTopViewShow && intercept()) {//向上滑
                        LogUtil.d("onInterceptTouchEvent... 向上滑");
                        return true;
                    }
                }

                if (y - downY > 0) {
                    if (listView.getChildAt(0).getTop() == 0 && !isTopViewShow) {//向下滑
                        LogUtil.d("onInterceptTouchEvent... 向下滑");
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    /**
     * 当ListView 内容的高度大于 底部剩余高度是 需要拦截事件
     */
    private boolean intercept() {
        return (mHeight - topView.getMeasuredHeight() - secondView.getMeasuredHeight() < getListViewHeight(listView));
    }

    /**
     * 还原控件的Y坐标
     */
    public void resetLayout() {
        ViewHelper.setTranslationY(topView, 0);
        ViewHelper.setTranslationY(secondView, 0);
        ViewHelper.setTranslationY(listView, 0);
        isTopViewShow = true;
    }

    /**
     * 获取ListView内容高度
     */
    public int getListViewHeight(ListView listView) {
        if (listView == null)
            return 0;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        Log.e("AAA", "totalHeight: " + totalHeight);
        return totalHeight;
    }
}
