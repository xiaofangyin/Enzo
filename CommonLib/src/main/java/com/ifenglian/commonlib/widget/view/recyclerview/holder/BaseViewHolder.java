package com.ifenglian.commonlib.widget.view.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * 文 件 名: BaseViewHolder
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private SparseArray<View> mArray;
    private View mView;

    BaseViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView;
        mArray = new SparseArray<>();
    }

    View findView(int resId) {
        View view = mArray.get(resId);
        if (view == null) {
            view = mView.findViewById(resId);
            mArray.put(resId, view);
        }
        return view;
    }

    public abstract void setUpView(T model, int position, RecyclerView.Adapter adapter);

}
