package com.enzo.commonlib.widget.percentlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.enzo.commonlib.R;

/**
 * 文 件 名: PercentLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2016/8/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class PercentLayout extends RelativeLayout {

    public PercentLayout(Context context) {
        super(context);
    }

    public PercentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量出子控件的宽高，进行改变
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            float widthPercent = 0;
            float heightPercent = 0;
            if (layoutParams instanceof PercentLayout.LayoutParams) {
                widthPercent = ((PercentLayout.LayoutParams) layoutParams).getWidthPercent();
                heightPercent = ((PercentLayout.LayoutParams) layoutParams).getHeightPercent();
            }
            if (widthPercent != 0) {
                layoutParams.width = (int) (width * widthPercent);
            }
            if (heightPercent != 0) {
                layoutParams.height = (int) (height * heightPercent);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends RelativeLayout.LayoutParams {

        private Float widthPercent;
        private Float HeightPercent;

        private Float getWidthPercent() {
            return widthPercent;
        }

        private Float getHeightPercent() {
            return HeightPercent;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.PercentLayout);
            try {
                widthPercent = array.getFloat(R.styleable.PercentLayout_layout_widthPercent, 0);
                HeightPercent = array.getFloat(R.styleable.PercentLayout_layout_heightPercent, 0);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                array.recycle();
            }
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

    }
}
