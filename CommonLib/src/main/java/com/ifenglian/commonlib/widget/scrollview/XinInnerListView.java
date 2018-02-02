package com.ifenglian.commonlib.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ListView;

/**
 * 内部ListView, 该组件不支持下拉刷新，上拉加载更多，是为了嵌套在ScrollView或者ViewPager中的，适合数据少的环境使用，最好是一次性显示所有数据
 * 提供一个接口OnLastItemVisibleListener，当最后一个item完全显示时，回调onLastItemVisible()，可以去加载更多数据。
 */
public class XinInnerListView extends ListView implements AbsListView.OnScrollListener {
    private final String TAG = "XinInnerScrollView";
    private boolean isFirstItemVisible; // 第一个item是否可见
    private boolean isLastItemVisible; // 最后一个item是否可见
    private int downX, downY; // 按下时
    private int currX, currY; // 移动时
    private int moveY; // 从按下到移动的Y距离

    public XinInnerListView(Context context) {
        super(context);
        setOnScrollListener(this);
    }

    public XinInnerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
    }

    public XinInnerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnScrollListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
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
                    if (isFirstItemVisible) { // 当前处于顶部
                        if (currY - downY > 0) {
                            printLog("onTouchEvent ACTION_MOVE 已到顶部 下滑 父处理");
                            getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            printLog("onTouchEvent ACTION_MOVE 已到顶部 上滑 子处理");
                        }
                    } else if (isLastItemVisible) {
                        // 当前处于底部
                        if (currY - downY < 0) {
                            printLog("onTouchEvent ACTION_MOVE 已到底部 上滑 父处理");
                            getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            printLog("onTouchEvent ACTION_MOVE 已到底部 下滑 子处理");
                        }
                    } else {
                        // 当前处于中间
                        printLog("onTouchEvent ACTION_MOVE 在中间 子处理");
                    }
                } else {
                    // 水平滚动
                    printLog("onTouchEvent ACTION_MOVE 水平滚动 父处理");
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                printLog("onTouchEvent ACTION_UP ========================");
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 判断最后listView中最后一个item是否完全显示出来
     *
     * @return
     */
    protected boolean isLastItemVisible() {
        Adapter adapter = getAdapter();
        if (null == adapter || adapter.isEmpty()) {
            return true;
        }
        int lastVisiblePosition = getLastVisiblePosition();
        if (lastVisiblePosition >= (adapter.getCount() - 1) - 1) {
            View lastVisibleChild = getChildAt(Math.min(lastVisiblePosition - getFirstVisiblePosition(), getChildCount() - 1));
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= getBottom();
            }
        }
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        boolean isTop = false;
        if (view.getChildCount() > 0) {
            isTop = view.getChildAt(0).getTop() == 0;
        }
        isFirstItemVisible = firstVisibleItem == 0 && isTop;
        isLastItemVisible = isLastItemVisible();
        if (isLastItemVisible) {
            if (onLastItemVisibleListener != null) {
                onLastItemVisibleListener.onLastItemVisible(view, firstVisibleItem + visibleItemCount - 1, getAdapter());
            }
        }
    }

    public void setOnLastItemVisibleListener(OnLastItemVisibleListener onLastItemVisibleListener) {
        this.onLastItemVisibleListener = onLastItemVisibleListener;
    }

    private OnLastItemVisibleListener onLastItemVisibleListener;

    /**
     * 最后一个Item显示的监听器
     */
    public interface OnLastItemVisibleListener {
        void onLastItemVisible(AbsListView view, int position, Adapter adapter);
    }

    public void printLog(String msg) {
        Log.d(TAG, msg);
    }
}
