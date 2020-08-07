package com.enzo.commonlib.utils.appupgrade;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.enzo.commonlib.utils.common.DensityUtil;


public class MaxHeightScrollView extends ScrollView {

    private float maxHeight;

    public MaxHeightScrollView(Context context) {
        this(context, null);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        maxHeight = DensityUtil.dip2px(context, 140);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            if (heightSize > maxHeight) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) maxHeight, MeasureSpec.AT_MOST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
