package com.ifenglian.module_d.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ifenglian.module_d.R;

import java.util.List;

public class MDAutoLoadListViewAdapter extends BaseAdapter {

    List<String> items;
    Context context;

    public MDAutoLoadListViewAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    public void addItem(String text) {
        items.add(text);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.md_item_list_autoload_layout, null);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(items.get(position));
        return view;
    }

}
