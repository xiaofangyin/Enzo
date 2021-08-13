package com.enzo.commonlib.base;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * 文 件 名: BaseViewHolder
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/5/26
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public View findView(int resId) {
        return itemView.findViewById(resId);
    }

    public abstract void setUpView(T model, int position, RecyclerView.Adapter adapter);
}
