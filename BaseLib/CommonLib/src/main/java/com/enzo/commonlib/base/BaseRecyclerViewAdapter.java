package com.enzo.commonlib.base;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: BaseRecyclerViewAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/5/26
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mData = new ArrayList<>();
    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<T> getData() {
        return mData;
    }

    public void setNewData(List<T> list) {
        mData.clear();
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void setLoadMoreData(List<T> list) {
        if (list != null) {
            int size = mData.size();
            mData.addAll(list);
            notifyItemInserted(size);
            notifyItemRangeChanged(size, mData.size() - size);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
