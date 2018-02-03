package com.ifenglian.module_d.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifenglian.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDStickyNavAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/3
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDStickyNavAdapter extends RecyclerView.Adapter<MDStickyNavAdapter.MDViewHolder> {

    private List<String> mData;

    public MDStickyNavAdapter() {
        mData = new ArrayList<>();
    }

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public MDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MDViewHolder holder = new MDViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MDViewHolder holder, int position) {
        holder.textView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class MDViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MDViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.id_info);
        }
    }
}
