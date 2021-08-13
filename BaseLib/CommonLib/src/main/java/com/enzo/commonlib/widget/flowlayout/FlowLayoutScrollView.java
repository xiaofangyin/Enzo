package com.enzo.commonlib.widget.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.enzo.commonlib.R;

public class FlowLayoutScrollView extends ScrollView {

    private FlowLayout flowLayout;

    public FlowLayoutScrollView(Context context) {
        this(context, null);
    }

    public FlowLayoutScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        float horizontal_space = array.getDimension(R.styleable.FlowLayout_width_space, 0);
        float vertical_space = array.getDimension(R.styleable.FlowLayout_height_space, 0);
        array.recycle();

        flowLayout = new FlowLayout(context, horizontal_space, vertical_space);
        addView(flowLayout);
    }

    public void setAdapter(FlowLayoutAdapter flowLayoutAdapter) {
        flowLayout.setAdapter(flowLayoutAdapter);
    }

    public FlowLayout getFlowLayout() {
        return flowLayout;
    }
}
