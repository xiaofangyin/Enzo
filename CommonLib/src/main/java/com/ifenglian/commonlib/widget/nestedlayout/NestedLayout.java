package com.ifenglian.commonlib.widget.nestedlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.OverScroller;

import com.ifenglian.commonlib.utils.common.LogUtil;

/**
 * 文 件 名: SGLOuterLinearLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class NestedLayout extends LinearLayout {

    private ListView listView;
    private View topView, secondView;
    private int downY;
    private int mHeight;
    //控制滑动
    private OverScroller mOverScroller;
    //速度获取
    private VelocityTracker mVelocityTracker;

    public NestedLayout(Context context) {
        this(context, null);
    }

    public NestedLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mOverScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();
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
        mVelocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int currY = (int) ev.getY();
                if (currY == downY) {
                    break;
                }
                LogUtil.e("currY - downY: " + (currY - downY));
                scrollBy(0, -(currY - downY));
                downY = currY;
                break;
            case MotionEvent.ACTION_UP:
                //处理松手后的Fling 1000 表示1000毫秒内运动了多少像素
                mVelocityTracker.computeCurrentVelocity(1000, ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity());
                int velocityX = (int) mVelocityTracker.getXVelocity();
                if (Math.abs(velocityX) > ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity()) {
                    fling(-velocityX);
                }
                mVelocityTracker.clear();
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                getParent().requestDisallowInterceptTouchEvent(false);
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
                    if (listView.getChildAt(0).getTop() == 0 && isTopViewShow() && intercept()) {//向上滑
                        LogUtil.d("onInterceptTouchEvent... 向上滑");
                        return true;
                    }
                }

                if (y - downY > 0) {
                    if (listView.getChildAt(0).getTop() == 0) {//向下滑
                        LogUtil.d("onInterceptTouchEvent... 向下滑");
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    private void fling(int vX) {
        mOverScroller.fling(getScrollX(), 0, vX, 0, 0, getHeight(), 0, 0);
        invalidate();
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        LogUtil.e("scrollTo y: " + y);
        if (y < 0) {
            y = 0;
        }
        if (y > topView.getHeight()) {
            y = topView.getHeight();
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
//            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
//            postInvalidate();
//            if (!mOverScroller.computeScrollOffset() ) {
//
//            }
        }
    }

    /**
     * 当ListView 内容的高度大于 底部剩余高度是 需要拦截事件
     */
    private boolean intercept() {
        return (mHeight - topView.getMeasuredHeight() - secondView.getMeasuredHeight() < getListViewHeight(listView));
    }

    private boolean isTopViewShow() {
        LogUtil.e("isTopViewShow getScrollY(): " + getScrollY());
        return getScrollY() < topView.getHeight();
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
