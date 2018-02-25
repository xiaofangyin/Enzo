package com.ifenglian.commonlib.widget.autoload.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ifenglian.commonlib.R;

public class AutoLoadListView extends ListView implements Pullable {
    public static final int INIT = 0;
    public static final int LOADING = 1;
    private OnLoadListener mOnLoadListener;
    private ImageView mLoadingView;
    private TextView mStateTextView;
    private int state = INIT;

    public AutoLoadListView(Context context) {
        super(context);
        init(context);
    }

    public AutoLoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoLoadListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.lib_layout_refresh_footer, null);
        mLoadingView = view.findViewById(R.id.loading_icon);
        mStateTextView = view.findViewById(R.id.loadstate_tv);
        addFooterView(view, null, false);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 在滚动中判断是否满足自动加载条件
        checkLoad();
    }

    /**
     * 判断是否满足自动加载条件
     */
    private void checkLoad() {
        if (reachBottom() && mOnLoadListener != null && state != LOADING) {
            mOnLoadListener.onLoad(this);
            changeState(LOADING);
        }
    }

    private void changeState(int state) {
        this.state = state;
        switch (state) {
            case INIT:
                mLoadingView.setVisibility(View.INVISIBLE);
                mStateTextView.setText(R.string.more);
                break;

            case LOADING:
                mLoadingView.setVisibility(View.VISIBLE);
                mStateTextView.setText(R.string.loading);
                break;
        }
    }

    /**
     * 完成加载
     */
    public void finishLoading() {
        changeState(INIT);
    }

    @Override
    public boolean canPullDown() {
        if (state == LOADING) {
            return false;
        } else if (getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() >= 0) {
            // 滑到ListView的顶部了
            return true;
        }
        return false;
    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }

    /**
     * @return FooterView可见时返回true，否则返回false
     */
    public boolean reachBottom() {
        if (getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部了   getChildAt(int position)方法的参数position指的是当前可见区域中的ChildView位置
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()).getTop() < getMeasuredHeight()) {
                return true;
            }
        }
        return false;
    }

    public interface OnLoadListener {
        void onLoad(AutoLoadListView listView);
    }
}
