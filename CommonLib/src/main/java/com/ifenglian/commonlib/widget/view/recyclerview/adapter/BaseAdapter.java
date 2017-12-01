package com.ifenglian.commonlib.widget.view.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.BaseViewHolder;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.ViewHolderFooterView;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.ViewHolderNoDataView;
import com.ifenglian.commonlib.widget.view.recyclerview.monitor.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: BaseAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int TYPE_FOOTER_VIEW = 100001;//footer类型 Item
    private static final int TYPE_NO_DATA_VIEW = 100002;//初次加载无数据的默认空白view

    public List<T> mDatas;
    public Context mContext;
    private boolean mOpenLoadMore;//是否开启加载更多
    private boolean mLoading;//是否正在加载 避免多次加载
    private RecyclerView mRecyclerView;
    private View mLoadingView; //加载中view
    private RelativeLayout mFooterLayout;//footer view
    private OnLoadMoreListener mLoadMoreListener;

    protected abstract int getViewType(int position, T data);

    public BaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        mContext = context;
        mDatas = datas == null ? new ArrayList<T>() : datas;
        mOpenLoadMore = isOpenLoadMore;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.isEmpty()) {
            return TYPE_NO_DATA_VIEW;
        }
        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        }
        return getViewType(position, mDatas.get(position));
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_FOOTER_VIEW:
                if (mFooterLayout == null) {
                    mFooterLayout = new RelativeLayout(mContext);
                }
                viewHolder = new ViewHolderFooterView(mFooterLayout);
                break;
            case TYPE_NO_DATA_VIEW:
                View emptyView = LayoutInflater.from(mContext).inflate(R.layout.ugc_auto_load_empty_layout, parent, false);
                viewHolder = new ViewHolderNoDataView(emptyView);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        if (mDatas.isEmpty()) {
            return 1;
        }
        return mDatas.size() + (mOpenLoadMore ? 1 : 0);
    }

    public boolean isFooterView(int position) {
        return mOpenLoadMore && position >= getItemCount() - 1;
    }

    public boolean isCommonItemView(int viewType) {
        return viewType != TYPE_FOOTER_VIEW && viewType != TYPE_NO_DATA_VIEW;
    }

    /**
     * 此方法在onDraw方法之前调用，可以在此方法中去执行一些初始化的操作
     *
     * @param holder UGCBaseViewHolder
     */
    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isFooterView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFooterView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMore(recyclerView, layoutManager);
    }

    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (!mOpenLoadMore || mLoadMoreListener == null) {
            return;
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                        scrollLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                    scrollLoadMore();
                }
            }
        });
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return findMax(lastVisibleItemPositions);
        }
        return -1;
    }

    private int findMax(int[] lastVisiblePositions) {
        int max = lastVisiblePositions[0];
        for (int value : lastVisiblePositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 到达底部开始刷新
     */
    private void scrollLoadMore() {
        if (mFooterLayout.getChildAt(0) == mLoadingView) {
            if (mLoadMoreListener != null && !mLoading) {
                mLoading = true;
                mLoadMoreListener.onLoadMore(false);
            }
        }
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     *
     * @param datas 数据
     */
    public void setNewData(List<T> datas) {
        mLoading = false;
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
        RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
        if (findLastVisibleItemPosition(manager) + 1 == getItemCount()) {
            scrollLoadMore();
        }
    }

    /**
     * 下拉刷新，得到的新数据查到原数据起始
     *
     * @param datas 数据
     */
    public void setData(List<T> datas) {
        mLoading = false;
        mDatas.addAll(0, datas);
        notifyDataSetChanged();
        RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
        if (findLastVisibleItemPosition(manager) + 1 == getItemCount()) {
            scrollLoadMore();
        }
    }

    /**
     * 刷新加载更多的数据
     *
     * @param datas
     */
    public void setLoadMoreData(List<T> datas) {
        mLoading = false;
        int size = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size);
    }

    /**
     * 初始化加载中布局
     *
     * @param loadingId 加载中布局
     */
    public void setLoadingView(int loadingId) {
        mLoading = false;
        mLoadingView = LayoutInflater.from(mContext).inflate(loadingId, null);
        addFooterView(mLoadingView);
    }

    public void showLoadingView() {
        setLoadingView(R.layout.ugc_auto_load_loading_layout);
    }

    /**
     * 初始加载失败布局
     *
     * @param loadFailedId 加载失败布局
     */
    public void setLoadFailedView(int loadFailedId) {
        mLoading = false;
        View loadFailedView = LayoutInflater.from(mContext).inflate(loadFailedId, null);
        addFooterView(loadFailedView);
        loadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFooterView(mLoadingView);
                if (mLoadMoreListener != null && !mLoading) {
                    mLoading = true;
                    mLoadMoreListener.onLoadMore(true);
                }
            }
        });
    }

    public void showLoadFailedView() {
        setLoadFailedView(R.layout.ugc_auto_load_failed_layout);
    }

    /**
     * 初始化全部加载完成布局
     *
     * @param loadEndId 全部加载完成布局
     */
    public void setLoadEndView(int loadEndId) {
        mLoading = false;
        addFooterView(LayoutInflater.from(mContext).inflate(loadEndId, null));
    }

    public void showLoadEndView() {
        setLoadEndView(R.layout.ugc_auto_load_end_layout);
    }

    private void addFooterView(View footerView) {
        if (footerView == null) {
            return;
        }
        if (mFooterLayout == null) {
            mFooterLayout = new RelativeLayout(mContext);
        }
        mFooterLayout.removeAllViews();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(50));
        mFooterLayout.addView(footerView, params);
    }

    private int dp2px(int dp) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (scale * dp + 0.5f);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }
}
