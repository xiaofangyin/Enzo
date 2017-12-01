package com.ifenglian.commonlib.widget.view.floatingactionbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class UGCObservableScrollView extends ScrollView {

    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    public UGCObservableScrollView(Context context) {
        super(context);
    }

    public UGCObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UGCObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }
}