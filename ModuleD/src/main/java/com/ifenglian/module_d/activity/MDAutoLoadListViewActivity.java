package com.ifenglian.module_d.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.autoload.listview.MyListener;
import com.ifenglian.commonlib.widget.autoload.listview.PullToRefreshLayout;
import com.ifenglian.commonlib.widget.autoload.listview.AutoLoadListView;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDAutoLoadListViewActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/25
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDAutoLoadListViewActivity extends BaseActivity implements AutoLoadListView.OnLoadListener {

    private AutoLoadListView listView;
    private MyAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_autoload_listview;
    }

    @Override
    public void initView() {
        ((PullToRefreshLayout) findViewById(R.id.refresh_view)).setOnRefreshListener(new MyListener());
        listView = findViewById(R.id.content_view);
        initListView();
        listView.setOnLoadListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

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

    /**
     * ListView初始化方法
     */
    private void initListView() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add("这里是item " + i);
        }
        adapter = new MyAdapter(this, items);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        "LongClick on "
                                + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        " Click on " + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
