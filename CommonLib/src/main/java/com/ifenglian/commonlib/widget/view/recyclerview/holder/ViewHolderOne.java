package com.ifenglian.commonlib.widget.view.recyclerview.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.widget.view.recyclerview.adapter.TopPostAdapter;
import com.ifenglian.commonlib.widget.view.recyclerview.decoration.ChildSpaceItemDecoration;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.Value1;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.ValueChild;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: ViewHolderOne
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class ViewHolderOne extends BaseViewHolder<Value1> {

    private boolean mFirstLoad = true;

    public ViewHolderOne(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final Value1 model, int position, RecyclerView.Adapter adapter) {
        if (mFirstLoad) {
            RecyclerView recyclerView = (RecyclerView) findView(R.id.child_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.addItemDecoration(new ChildSpaceItemDecoration());
            recyclerView.setAdapter(new TopPostAdapter(itemView.getContext(), getData(), false));
            mFirstLoad = false;
        }
    }

    private List<ValueChild> getData() {
        List<ValueChild> list = new ArrayList<>();
        list.add(new ValueChild("北京"));
        list.add(new ValueChild("上海"));
        list.add(new ValueChild("深圳"));
        list.add(new ValueChild("天津"));
        list.add(new ValueChild("广州"));
        return list;
    }
}
