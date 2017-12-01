package com.ifenglian.commonlib.widget.view.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.BaseViewHolder;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.ViewHolderChild;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.ValueChild;

import java.util.List;

/**
 * 文 件 名: TopPostAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class TopPostAdapter extends BaseAdapter<ValueChild> {

    public TopPostAdapter(Context context, List<ValueChild> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected int getViewType(int position, ValueChild data) {
        return 0;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewtype_child, parent, false);
        return new ViewHolderChild(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setUpView(mDatas.get(position), position, this);
    }
}
