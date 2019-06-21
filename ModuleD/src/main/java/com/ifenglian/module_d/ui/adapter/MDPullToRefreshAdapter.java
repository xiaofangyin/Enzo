package com.ifenglian.module_d.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifenglian.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDPullToRefreshAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/31
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDPullToRefreshAdapter extends RecyclerView.Adapter<MDPullToRefreshAdapter.NormalViewHolder> {

    private List<String> mData;

    public MDPullToRefreshAdapter() {
        mData = new ArrayList<>();
    }

    public void setNewData(List<String> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setLoadMoreData(List<String> data) {
        int size = mData.size();
        mData.addAll(data);
        notifyItemInserted(size);
        notifyItemRangeChanged(size, mData.size() - size);
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.md_item_pull_refresh, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder, int position) {
        holder.tvTitle.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        NormalViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
        }
    }
}
