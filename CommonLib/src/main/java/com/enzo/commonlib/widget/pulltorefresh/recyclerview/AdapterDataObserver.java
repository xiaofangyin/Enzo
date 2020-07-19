package com.enzo.commonlib.widget.pulltorefresh.recyclerview;

import androidx.annotation.Nullable;

interface AdapterDataObserver {

    public void notifyItemChanged(int position);

    public void notifyItemRangeChanged(int positionStart, int itemCount);

    public void notifyItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload);

    public void notifyItemRangeInserted(int positionStart, int itemCount);

    public void notifyItemRangeRemoved(int positionStart, int itemCount);

    public void notifyItemRangeRemoved(int fromPosition, int toPosition, int itemCount);
}
