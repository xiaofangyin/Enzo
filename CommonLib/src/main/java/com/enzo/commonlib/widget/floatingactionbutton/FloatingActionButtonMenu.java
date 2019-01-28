package com.enzo.commonlib.widget.floatingactionbutton;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * FloatingActionButtonMenu
 */
public class FloatingActionButtonMenu extends LinearLayoutCompat {

    private OnMenuItemClickListener mOnMenuItemClickListener;

    public FloatingActionButtonMenu(Context context) {
        this(context, null);
    }

    public FloatingActionButtonMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionButtonMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, final int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initMenuItems();
    }

    private void initMenuItems() {
        for (int i = 0; i < getChildCount(); i++) {
            View item = getChildAt(i);
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mOnMenuItemClickListener) {
                        mOnMenuItemClickListener.onMenuItemClick(view, view.getId());
                    }
                }
            });
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        mOnMenuItemClickListener = listener;
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(View button, int btnId);
    }
}
