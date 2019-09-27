package com.enzo.commonlib.widget.pulltorefresh.recyclerview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.enzo.commonlib.widget.pulltorefresh.recyclerview.base.BaseLoadMoreView;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.base.BasePullToRefreshView;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.defaultview.DefaultLoadMoreView;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.defaultview.DefaultRefreshHeaderView;

import java.util.List;

/**
 * 文 件 名: PullToRefreshRecyclerView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofangyinwork@163.com
 */
public class PullToRefreshRecyclerView extends RecyclerView {

    private Handler handler;
    //是否允许刷新
    private boolean isAllowRefresh = false;
    //是否允许加载更多
    private boolean isAllowLoadMore = false;
    //是否正在加载数据
    private boolean isLoadingData = false;
    //设置一个很大的数字
    private static final int TYPE_REFRESH_HEADER = 20000;
    private static final int TYPE_LOAD_MORE_FOOTER = 20001;
    //刷新加载更多监听
    private OnLoadListener mLoadingListener;
    //设置头部底部View的适配器
    public HeaderAndFooterAdapter mHeaderAndFooterAdapter;
    private Adapter insideAdapter;
    private AdapterDataObserver mDataObserver = new HeaderAndFooterAdapterDataObserver();

    //默认下拉刷新头布局、空布局
    private BasePullToRefreshView headerRefreshView;
    private BaseLoadMoreView loadMoreView;

    //触摸参数初始化
    private float mLastY = -1;
    private static final float DRAG_RATE = 3f;

    public PullToRefreshRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 初始化下拉刷新、上拉加载更多的状态显示View
     */
    private void init(Context context) {
        handler = new Handler(Looper.getMainLooper());
        //初始化头部刷新布局
        headerRefreshView = new DefaultRefreshHeaderView(context);
        loadMoreView = new DefaultLoadMoreView(context);
    }

    /**
     * 自定义刷新View
     */
    public void setRefreshView(BasePullToRefreshView refreshView) {
        this.headerRefreshView = refreshView;
    }

    /**
     * 自定义加载View
     */
    public void setLoadMoreView(BaseLoadMoreView loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    /**
     * 没有更多数据可加载
     */
    public void setNoMoreData(boolean noMore) {
        isLoadingData = false;
        loadMoreView.setState(noMore ? BaseLoadMoreView.STATE_NO_DATA : BaseLoadMoreView.STATE_SUCCESS);
        if (noMore) {
            insideAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 自动加载
     */
    public void setAutoRefresh() {
        if (isAllowRefresh && mLoadingListener != null) {
            if (!isLoading() && !isRefreshing()) {
                isLoadingData = true;
                headerRefreshView.onRefreshTime();
                if (headerRefreshView.getOnStateChangeListener() != null) {
                    headerRefreshView.getOnStateChangeListener().onStateChange(BasePullToRefreshView.STATE_REFRESHING);
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingListener.onRecyclerViewRefresh();
                    }
                }, 500);
                this.scrollToPosition(0);
            }
        }
    }

    /**
     * 刷新数据完成
     */
    public void refreshSuccess() {
        isLoadingData = false;
        if (headerRefreshView != null) {
            headerRefreshView.refreshSuccess();
        }
        mDataObserver.onChanged();
        setNoMoreData(false);

        scrollLoadMore();
    }

    /**
     * 刷新数据失败
     */
    public void refreshFailed() {
        isLoadingData = false;
        if (headerRefreshView != null) {
            headerRefreshView.refreshFailed();
        }
        mDataObserver.onChanged();
        setNoMoreData(false);
    }

    /**
     * 加载数据完成
     */
    public void loadMoreSuccess() {
        isLoadingData = false;
        loadMoreView.setState(BaseLoadMoreView.STATE_SUCCESS);
        insideAdapter.notifyDataSetChanged();

        scrollLoadMore();
    }

    /**
     * 加载数据失败
     */
    public void loadMoreFailed() {
        isLoadingData = false;
        loadMoreView.setState(BaseLoadMoreView.STATE_FAILED);
        loadMoreView.setOnRetryListener(new OnLoadListener() {
            @Override
            public void onRecyclerViewRefresh() {

            }

            @Override
            public void onRecyclerViewLoadMore() {

            }

            @Override
            public void onLoadMoreRetry() {
                loadMoreView.setState(BaseLoadMoreView.STATE_LOADING);
                if (mLoadingListener != null) {
                    mLoadingListener.onLoadMoreRetry();
                }
            }
        });
    }

    /**
     * 是否允许刷新
     */
    public void setPullRefreshEnabled(boolean enabled) {
        isAllowRefresh = enabled;
    }

    /**
     * 是否允许加载更多
     */
    public void setLoadMoreEnabled(boolean enabled) {
        isAllowLoadMore = enabled;
        if (!enabled) {
            loadMoreView.setState(BaseLoadMoreView.STATE_SUCCESS);
        }
    }

    /**
     * 显示加载更新时间
     */
    public void setRefreshTimeVisible(String tag) {
        if (headerRefreshView != null)
            headerRefreshView.setRefreshTimeVisible(tag);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.insideAdapter = adapter;
        mHeaderAndFooterAdapter = new HeaderAndFooterAdapter(adapter);
        super.setAdapter(mHeaderAndFooterAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

    /**
     * 保证GridLayoutManager加载更多和headView占一行
     */
    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (mHeaderAndFooterAdapter != null) {
            if (layout instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) layout);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (mHeaderAndFooterAdapter.isFooter(position) || mHeaderAndFooterAdapter.isRefreshHeader(position)) ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }
    }

    /**
     * ======================================================= 滚动监听 =======================================================
     */
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            scrollLoadMore();
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        scrollLoadMore();
    }

    private void scrollLoadMore() {
        if (loadMoreView != null) {
            int loadStatus = loadMoreView.getState();
            if (mLoadingListener != null && !isLoadingData && isAllowLoadMore && loadStatus == BaseLoadMoreView.STATE_SUCCESS) {
                LayoutManager layoutManager = getLayoutManager();

                if (layoutManager.getChildCount() > 0 && (findLastVisibleItemPosition(layoutManager) + 1 == layoutManager.getItemCount())) {
                    isLoadingData = true;
                    loadMoreView.setState(BaseLoadMoreView.STATE_LOADING);
                    mLoadingListener.onRecyclerViewLoadMore();
                }
            }
        }
    }

    private int findLastVisibleItemPosition(LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            int max = lastVisibleItemPositions[0];
            for (int value : lastVisibleItemPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }
        return -1;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (headerRefreshView.getState() != BasePullToRefreshView.STATE_REFRESHING) {
                headerRefreshView.onRefreshTime();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getY() - mLastY;
                mLastY = ev.getY();
                if (isOnTop() && isAllowRefresh) {
                    if (loadMoreView.getState() != BaseLoadMoreView.STATE_LOADING) {
                        headerRefreshView.onMove(deltaY / DRAG_RATE);
                    }
                    if (headerRefreshView.getVisibleHeight() > 0 && headerRefreshView.getState() < BasePullToRefreshView.STATE_REFRESHING) {
                        return false;
                    }
                }
                break;
            default:
                mLastY = -1; // reset
                if (isOnTop() && isAllowRefresh) {
                    if (headerRefreshView.dealReleaseAction()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mLoadingListener != null) {
                                    mLoadingListener.onRecyclerViewRefresh();
                                }
                            }
                        }, 500);
                    }
                }
                break;
        }

        int status = BasePullToRefreshView.STATE_SUCCESS;
        if (headerRefreshView != null)
            status = headerRefreshView.getState();

        return status == BasePullToRefreshView.STATE_REFRESHING || super.onTouchEvent(ev);
    }

    private boolean isOnTop() {
        return headerRefreshView != null && headerRefreshView.getParent() != null;
    }

    /**
     * 是否正在loading数据
     */
    public boolean isLoading() {
        return loadMoreView.getState() == BaseLoadMoreView.STATE_LOADING;
    }

    /**
     * 正在refreshing数据
     */
    public boolean isRefreshing() {
        return headerRefreshView.getState() != BasePullToRefreshView.STATE_PULL_DOWN;
    }

    /**
     * 判断内部adapter的type是否跟定义的头部和底部itemtype一致
     */
    private boolean isDefinitionWithSame(int itemViewType) {
        return itemViewType == TYPE_REFRESH_HEADER ||
                itemViewType == TYPE_LOAD_MORE_FOOTER;
    }

    /***
     * 包装头部和底部的数据通知
     */
    private class HeaderAndFooterAdapterDataObserver extends AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mHeaderAndFooterAdapter != null) {
                mHeaderAndFooterAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mHeaderAndFooterAdapter.notifyItemRangeInserted(0, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mHeaderAndFooterAdapter.notifyItemRangeChanged(0, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mHeaderAndFooterAdapter.notifyItemRangeChanged(0, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mHeaderAndFooterAdapter.notifyItemRangeRemoved(0, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mHeaderAndFooterAdapter.notifyItemMoved(0, toPosition);
        }
    }

    /***
     * 包装头部布局和顶部布局的外层adapter
     */
    private class HeaderAndFooterAdapter extends Adapter<ViewHolder> {

        private Adapter adapter;

        HeaderAndFooterAdapter(Adapter adapter) {
            this.adapter = adapter;
        }

        boolean isFooter(int position) {
            return isAllowLoadMore && position == getItemCount() - 1;
        }

        boolean isRefreshHeader(int position) {
            return isAllowRefresh && position == 0;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_REFRESH_HEADER) {
                return new HeaderAndFooterViewHolder(headerRefreshView);
            } else if (viewType == TYPE_LOAD_MORE_FOOTER) {
                loadMoreView.setVisibility(GONE);
                return new HeaderAndFooterViewHolder(loadMoreView);
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isRefreshHeader(position)) {
                return;
            }

            int adjPosition = position - 1;

            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
            if (isRefreshHeader(position)) {
                return;
            }

            int adjPosition;

            if (isAllowRefresh) {
                adjPosition = position - 1;
            } else {
                adjPosition = position;
            }

            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    if (payloads.isEmpty()) {
                        adapter.onBindViewHolder(holder, adjPosition);
                    } else {
                        adapter.onBindViewHolder(holder, adjPosition, payloads);
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            int adjLen;
            if (isAllowRefresh) {
                adjLen = (isAllowLoadMore ? 2 : 1);
            } else {
                adjLen = (isAllowLoadMore ? 1 : 0);
            }

            if (adapter != null) {
                return adapter.getItemCount() + adjLen;
            } else {
                return adjLen;
            }
        }

        @Override
        public int getItemViewType(int position) {
            int adjPosition;
            if (isAllowRefresh) {
                adjPosition = position - 1;
            } else {
                adjPosition = position;
            }

            if (isRefreshHeader(position)) {
                return TYPE_REFRESH_HEADER;
            }

            if (isFooter(position)) {
                return TYPE_LOAD_MORE_FOOTER;
            }
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    int type = adapter.getItemViewType(adjPosition);
                    if (isDefinitionWithSame(type)) {
                        throw new IllegalStateException("内部adapter的itemType是否跟定义的头部和底部itemType一致，造成报错!");
                    }
                    return type;
                }
            }
            return 0;
        }

        @Override
        public long getItemId(int position) {
            int count;
            if (isAllowRefresh) {
                count = 1;
            } else {
                count = 0;
            }
            if (adapter != null && position >= count) {
                int adjPosition = position - count;
                if (adjPosition < adapter.getItemCount()) {
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isFooter(position) || isRefreshHeader(position)) ? gridManager.getSpanCount() : 1;
                    }
                });
            }
            adapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            adapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if ((lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams)
                    || isRefreshHeader(holder.getLayoutPosition())
                    || isFooter(holder.getLayoutPosition())) {
                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                    p.setFullSpan(true);
                }
            }
            adapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            adapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            adapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(ViewHolder holder) {
            return adapter.onFailedToRecycleView(holder);
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            adapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            adapter.registerAdapterDataObserver(observer);
        }

        private class HeaderAndFooterViewHolder extends ViewHolder {
            HeaderAndFooterViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    /**
     * ======================================================= 加载数据 =======================================================
     * 设置加载更多监听
     */
    public void setOnLoadListener(OnLoadListener listener) {
        mLoadingListener = listener;
    }

    public interface OnLoadListener {

        void onRecyclerViewRefresh();

        void onRecyclerViewLoadMore();

        void onLoadMoreRetry();
    }

    /**
     * ======================================================= 加载数据 =======================================================
     */


    /**
     * 防止内存泄漏
     */
    public void destroy() {
        if (loadMoreView != null) {
            loadMoreView.destroy();
            loadMoreView = null;
        }
        if (headerRefreshView != null) {
            headerRefreshView.destroy();
            headerRefreshView = null;
        }
    }
}
