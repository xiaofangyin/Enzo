package com.ifenglian.module_d.activity;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.parallax.ParallaxListView;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MDNestedListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDParallaxListViewActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/8
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDParallaxListViewActivity extends BaseActivity {

    private ParallaxListView listView;
    private View head;
    private ImageView parallaxImageView;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_parallax_list_view;
    }

    @Override
    public void initView() {
        listView = findViewById(R.id.parallaxListView);

        head = View.inflate(MDParallaxListViewActivity.this, R.layout.md_layout_head, null);
        parallaxImageView = head.findViewById(R.id.parallaxImageView);
        head.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listView.setParallaxImageView(parallaxImageView);
                head.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        listView.addHeaderView(head);
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
