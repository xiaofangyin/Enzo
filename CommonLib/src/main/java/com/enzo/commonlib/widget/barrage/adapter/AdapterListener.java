package com.enzo.commonlib.widget.barrage.adapter;

public interface AdapterListener<T> {
    // 点击事件
    void onItemClick(BaseBarrageAdapter.BarrageViewHolder<T> holder, T item);
}
