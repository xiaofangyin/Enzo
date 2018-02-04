package com.ifenglian.module_d.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDStickyNavAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDListViewInScrollViewActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDListViewInScrollViewActivity extends BaseActivity {
    private List<String> mData = new ArrayList<String>();

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_listview_in_scrollview;
    }

    @Override
    public void initView() {
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MDListViewInScrollViewActivity.this));
        for (int i = 0; i < 50; i++) {
            mData.add(" -> " + i);
        }
        MDStickyNavAdapter adapter = new MDStickyNavAdapter();
        adapter.setData(mData);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
