package com.ifenglian.module_d.activity;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.pulltorefresh.PullToRefreshRecyclerView;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDPullToRefreshAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDPullToRefreshActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/31
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDPullToRefreshActivity extends BaseActivity implements PullToRefreshRecyclerView.OnRefreshAndLoadMoreListener {

    private PullToRefreshRecyclerView mRecyclerView;
    private MDPullToRefreshAdapter adapter;
    private List<String> mData;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_pull_to_refresh;
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.md_pull_to_refresh_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setRefreshAndLoadMoreListener(this);
    }

    @Override
    public void initData() {
        mData = new ArrayList<>();
        adapter = new MDPullToRefreshAdapter();
        adapter.setNewData(mData);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setAutoRefresh();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onRecyclerViewRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.clear();
                for (int i = 0; i < 30; i++) {
                    mData.add("Item" + (mData.size() + 1));
                }
                adapter.setNewData(mData);

                refreshUI();
            }
        }, 3000);
    }

    @Override
    public void onRecyclerViewLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    list.add("load more" + i);
                }
                adapter.setLoadMoreData(list);

                refreshUI();
            }
        }, 3000);
    }

    public void refreshUI() {
        if (mRecyclerView != null) {
            if (mRecyclerView.isLoading()) {
                mRecyclerView.loadMoreComplete();
            } else if (mRecyclerView.isRefreshing()) {
                mRecyclerView.refreshComplete();
            }
        }
    }
}
