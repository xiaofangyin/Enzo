package com.ifenglian.module_d.activity;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;
import com.ifenglian.commonlib.widget.pulltorefresh.recyclerview.listener.OnRefreshAndLoadMoreListener;
import com.ifenglian.commonlib.widget.pulltorefresh.recyclerview.listener.OnRetryListener;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDPullToRefreshAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDPullToRefreshRecyclerViewActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/31
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDPullToRefreshRecyclerViewActivity extends BaseActivity implements OnRefreshAndLoadMoreListener {

    private PullToRefreshRecyclerView mRecyclerView;
    private MDPullToRefreshAdapter adapter;
    private List<String> mData;
    private Handler mHandler;

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
        mRecyclerView.setRefreshTimeVisible(true);
        //mRecyclerView.setRefreshView(new DefinitionAnimationRefreshHeaderView(MDPullToRefreshRecyclerViewActivity.this));
        //mRecyclerView.setLoadMoreView(new DefinitionAnimationLoadMoreView(MDPullToRefreshRecyclerViewActivity.this));
        mRecyclerView.setRefreshAndLoadMoreListener(this);
    }

    @Override
    public void initData() {
        mHandler = new Handler(Looper.myLooper());
        mData = new ArrayList<>();
        adapter = new MDPullToRefreshAdapter();
        adapter.setNewData(mData);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setAutoRefresh();
        mRecyclerView.setOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry(View view) {
                onRecyclerViewLoadMore();
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onRecyclerViewRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.clear();
                for (int i = 0; i < 30; i++) {
                    mData.add("Item" + (mData.size() + 1));
                }
                adapter.setNewData(mData);

                refreshUI();
            }
        }, 2000);
    }

    @Override
    public void onRecyclerViewLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add("load more" + i);
                }
                adapter.setLoadMoreData(list);
                refreshUI();
            }
        }, 2000);
    }

    public void refreshUI() {
        if (mRecyclerView != null) {
            if (mRecyclerView.isLoading()) {
                if (adapter.getItemCount() > 50) {
                    mRecyclerView.loadMoreFailed();
                } else {
                    mRecyclerView.loadMoreSuccess();
                }
            } else if (mRecyclerView.isRefreshing()) {
                mRecyclerView.refreshSuccess();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mRecyclerView.destroy();
    }
}
