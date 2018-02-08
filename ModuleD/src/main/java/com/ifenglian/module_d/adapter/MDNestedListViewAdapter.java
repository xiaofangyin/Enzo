package com.ifenglian.module_d.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ifenglian.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDNestedListViewAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/8
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDNestedListViewAdapter extends BaseAdapter {

    private List<String> mData = new ArrayList<>();

    public void setData(List<String> data) {
        mData.clear();
        mData.addAll(data);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.md_item_nested, null);
        }
        return convertView;
    }
}
