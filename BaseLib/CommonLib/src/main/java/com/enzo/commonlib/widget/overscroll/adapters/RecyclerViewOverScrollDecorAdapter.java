package com.enzo.commonlib.widget.overscroll.adapters;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewOverScrollDecorAdapter implements IOverScrollDecoratorAdapter {

    protected final RecyclerView mRecyclerView;
    protected final LinearLayoutManager mLayoutManager;

    public RecyclerViewOverScrollDecorAdapter(RecyclerView recyclerView) {
        if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException("Recycler views with non-linear layout managers are not supported by this adapter. Consider implementing a new adapter, instead");
        }

        mRecyclerView = recyclerView;
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    @Override
    public View getView() {
        return mRecyclerView;
    }

    @Override
    public boolean isInAbsoluteStart() {
        return mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
    }

    @Override
    public boolean isInAbsoluteEnd() {
        return mLayoutManager.findLastCompletelyVisibleItemPosition() == (mRecyclerView.getAdapter().getItemCount() - 1);
    }

}
