package com.ifenglian.commonlib.widget.scrollview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * recyclerview的上拉下拉效果
 *
 * Created by clg on 2016/9/5.
 */
public class SARecyclerContainerLayout extends LinearLayout {

    /**
     * 容器中的组件
     */
    private View convertView;

    /**
     * 容器中第一条item
     */
    private View firstView;

    /**
     * 如果容器中的组件为RecyclerView
     */
    private RecyclerView recyclerView;
    /**
     * 上一次滑动的坐标
     */
    private int mLastY;
    /**
     * 滚动辅助类
     */
    private Scroller mScroller;

    /**
     * 滑动监听
     */
    private RecycleContainerScrollListener scrollListener;

    private boolean isScroll;

    public SARecyclerContainerLayout(Context context) {
        this(context, null);
    }

    public SARecyclerContainerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SARecyclerContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new RuntimeException(SARecyclerContainerLayout.class.getSimpleName() + "only one child view allowed");
        }
        convertView = getChildAt(0);
        //TODO 可以拓展ListView等可滑动的组件
        if (convertView instanceof RecyclerView) {
            recyclerView = (RecyclerView) convertView;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        firstView = recyclerView.getChildAt(0);
        if(firstView != null){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();  //终止动画
                    }
                    if (SARecyclerUtil.isRecyclerViewToTop(recyclerView)) {

                    }
                    isScroll = true;
                    break;
                case MotionEvent.ACTION_UP:
                    isScroll = false;
                    break;
            }
            postInvalidate();
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (convertView instanceof RecyclerView) {
                    if (y - mLastY > 20) {
                        if (SARecyclerUtil.isRecyclerViewToTop(recyclerView)) {
                            return true;
                        }
                    }
                }
                break;
        }

        return false;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            if (scrollListener != null)
                scrollListener.onScroll(mScroller.getCurrY());
            postInvalidate();
        }
    }

    public boolean isScroll() {
        return isScroll;
    }

    public void setOnScrollListener(RecycleContainerScrollListener listener) {
        scrollListener = listener;
    }

    public interface RecycleContainerScrollListener {
        void onScroll(int y);
    }
}
