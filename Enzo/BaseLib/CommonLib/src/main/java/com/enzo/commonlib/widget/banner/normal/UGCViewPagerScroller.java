package com.enzo.commonlib.widget.banner.normal;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 文 件 名: UGCViewPagerScroller
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/16
 * 邮   箱: xiaofangyinwork@163.com
 */
public class UGCViewPagerScroller extends Scroller {

    private int mDuration = 1000;// ViewPager默认的最大Duration 为600。

    UGCViewPagerScroller(Context context) {
        super(context);
    }

    public UGCViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public UGCViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
}
