package com.ifenglian.commonlib.widget.pulltorefresh.recyclerview.listener;

/**
 * 文 件 名: OnRefreshAndLoadMoreListener
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public interface OnRefreshAndLoadMoreListener {

    void onRecyclerViewRefresh();

    void onRecyclerViewLoadMore();

    void onLoadMoreRetry();
}
