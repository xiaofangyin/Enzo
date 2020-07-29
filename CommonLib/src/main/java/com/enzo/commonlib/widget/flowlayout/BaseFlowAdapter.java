package com.enzo.commonlib.widget.flowlayout;

import android.view.View;

import androidx.annotation.NonNull;

public interface BaseFlowAdapter<T> {
    int getCount();

    int getItemLayoutID(int position, T bean);

    View getView(FlowLayout parent, final int position);

    void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, T bean);

    void onItemClick(int position, T bean);

    void onItemLongClick(int position, T bean);

    void notifyDataSetChanged();

    void registerAdapterDataObserver(@NonNull FlowLayoutAdapter.AdapterDataObserver observer);

    void unregisterObserver(@NonNull FlowLayoutAdapter.AdapterDataObserver observer);
}
