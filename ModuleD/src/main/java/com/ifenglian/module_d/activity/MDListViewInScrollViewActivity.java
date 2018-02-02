package com.ifenglian.module_d.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.scrollview.XinInnerListView;
import com.ifenglian.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDListViewInScrollViewActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDListViewInScrollViewActivity extends BaseActivity {
    private int maxListSize = 300;
    private List<String> list;
    private MyAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_listview_in_scrollview;
    }

    @Override
    public void initView() {
        XinInnerListView mListView = findViewById(R.id.listview);
        list = getList();
        mAdapter = new MyAdapter(this, list);
        mListView.setAdapter(mAdapter);
//        mListView.setOnLastItemVisibleListener(new XinInnerListView.OnLastItemVisibleListener() {
//            @Override
//            public void onLastItemVisible(AbsListView view, int position, Adapter adapter) {
//                if (list.size() < maxListSize) {
//                    list.addAll(getList());
//                    mAdapter.setData(list);
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private List<String> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            tempList.add("item " + (this.list.size() + i + 1) + " / " + maxListSize);
        }
        return tempList;
    }

    public class MyAdapter extends BaseAdapter {
        private List<String> list;
        private Context context;

        public MyAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public String getItem(int position) {
            return list == null ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
                holder.mTextView = (TextView) convertView;
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.mTextView.setText(getItem(position));
            return convertView;
        }

        public void setData(List<String> list) {
            this.list = list;
        }

        class Holder {
            private TextView mTextView;
        }
    }
}
