package com.ifenglian.commonlib.widget.view.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifenglian.commonlib.widget.view.recyclerview.factory.BaseViewTypeFactory;
import com.ifenglian.commonlib.widget.view.recyclerview.factory.ViewTypeFactory;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.BaseViewHolder;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.BaseValue;

import java.util.List;

/**
 * 文 件 名: RefreshAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class RefreshAdapter extends BaseAdapter<BaseValue> {

    private BaseViewTypeFactory mFactory;

    public RefreshAdapter(Context context, List<BaseValue> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mFactory = new ViewTypeFactory();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            View view = LayoutInflater.from(mContext).inflate(viewType, parent, false);
            return mFactory.createViewHolder(view, viewType);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (!isFooterView(position)) {
            holder.setUpView(mDatas.get(position), position, this);
        }
    }

    @Override
    protected int getViewType(int position, BaseValue data) {
        return data.getLayoutId(mFactory);
    }
}
