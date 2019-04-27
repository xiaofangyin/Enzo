package com.enzo.commonlib.widget.barragephoto.adapter;

public interface AdapterListener<T> {
    // 点击事件
    void onItemClick(BarrageAdapter.BarrageViewHolder<T> holder, T item);
}
