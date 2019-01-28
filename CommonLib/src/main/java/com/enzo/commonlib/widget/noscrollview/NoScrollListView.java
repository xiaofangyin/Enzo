package com.enzo.commonlib.widget.noscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 去掉滚效果的ListView ,Item的root布局必须是linearLayout
 * Created by Eiffelyk on 2016/7/1.
 */

public class NoScrollListView extends ListView {
    public NoScrollListView(Context context) {
        super(context);
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override

/**   只重写该方法，达到使ListView适应ScrollView的效果   */

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
