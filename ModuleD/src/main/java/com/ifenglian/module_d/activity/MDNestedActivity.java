package com.ifenglian.module_d.activity;

import android.widget.ListView;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDNestedListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDNestedActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/8
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDNestedActivity extends BaseActivity{

    private ListView listView;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_nested;
    }

    @Override
    public void initView() {
        listView = findViewById(R.id.md_recycler_view);
    }

    @Override
    public void initData() {
        MDNestedListViewAdapter adapter = new MDNestedListViewAdapter();
        listView.setAdapter(adapter);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("我是条目: " + i);
        }
        adapter.setData(data);
    }

    @Override
    public void initListener() {

    }
}
