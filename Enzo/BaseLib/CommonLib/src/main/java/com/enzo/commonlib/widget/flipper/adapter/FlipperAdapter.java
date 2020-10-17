package com.enzo.commonlib.widget.flipper.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;

public abstract class FlipperAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public abstract int getCount();

    public abstract View getItemView(Context context, int position);

    public final void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public final void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public final void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public final void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }
}
