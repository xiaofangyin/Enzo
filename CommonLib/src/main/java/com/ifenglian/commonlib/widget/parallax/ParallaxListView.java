package com.ifenglian.commonlib.widget.parallax;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ListView;

public class ParallaxListView extends ListView {

    private String tag = ParallaxListView.class.getSimpleName();

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ImageView parallaxImageView;
    private int originalHeight;
    private int maxHeight;

    public void setParallaxImageView(ImageView parallaxImageView) {
        this.parallaxImageView = parallaxImageView;

        originalHeight = parallaxImageView.getHeight();
        maxHeight = parallaxImageView.getDrawable().getIntrinsicHeight();
        Log.e(tag, "originalHeight: " + originalHeight);
    }

    //listview滑动到头时候调用
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //deltaY: y方向手指滑动的距离,正：底部到头  负：顶部到头
        //scrollY：y方向滚动的距离
        //maxOverScrollY：最大可滑动的距离
        //isTouchEvent: 是否是手指拖动到头， true：是    false:惯性滑动到头
        Log.e(tag, "deltaY: " + deltaY + "  isTouchEvent:" + isTouchEvent);

        if (deltaY < 0 && isTouchEvent) {
            parallaxImageView.getLayoutParams().height = parallaxImageView.getHeight() - deltaY / 3;
            if (parallaxImageView.getLayoutParams().height > maxHeight)
                parallaxImageView.getLayoutParams().height = maxHeight;
            parallaxImageView.requestLayout();
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                ResetHeightAnimation animation = new ResetHeightAnimation(parallaxImageView, originalHeight);
                startAnimation(animation);
                break;
        }
        return super.onTouchEvent(ev);
    }


}
