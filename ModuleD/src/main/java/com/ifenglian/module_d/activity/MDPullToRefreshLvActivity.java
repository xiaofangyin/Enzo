package com.ifenglian.module_d.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.pulltorefresh.listview.AutoLoadListView;
import com.enzo.commonlib.widget.pulltorefresh.listview.PullToRefreshLayout;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDAutoLoadListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDPullToRefreshLvActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/25
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDPullToRefreshLvActivity extends BaseActivity implements AutoLoadListView.OnLoadListener, PullToRefreshLayout.OnRefreshListener {

    private AutoLoadListView listView;
    private MDAutoLoadListViewAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_pull_to_refresh_lv;
    }

    @Override
    public void initView() {
        ((PullToRefreshLayout) findViewById(R.id.refresh_view)).setOnRefreshListener(this);
        listView = findViewById(R.id.content_view);
        initListView();
        listView.setOnLoadListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    /**
     * ListView初始化方法
     */
    private void initListView() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            items.add("这里是item " + i);
        }
        adapter = new MDAutoLoadListViewAdapter(this, items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 5000);
    }

    @Override
    public void onLoad(final AutoLoadListView listView) {
        new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                for (int i = 0; i < 5; i++)
                    adapter.addItem("这里是自动加载进来的item");
                // 千万别忘了告诉控件加载完毕了哦！
                listView.finishLoading();
            }
        }.sendEmptyMessageDelayed(0, 5000);
    }
}
