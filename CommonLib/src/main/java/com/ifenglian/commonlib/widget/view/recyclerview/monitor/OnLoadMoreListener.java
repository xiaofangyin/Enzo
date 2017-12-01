package com.ifenglian.commonlib.widget.view.recyclerview.monitor;

/**
 * 文 件 名: OnLoadMoreListener
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public interface OnLoadMoreListener {
    /**
     * 加载更多的回调方法
     * @param isReload 是否是重新加载，只有加载失败后，点击重新加载时为true
     */
    void onLoadMore(boolean isReload);
}
