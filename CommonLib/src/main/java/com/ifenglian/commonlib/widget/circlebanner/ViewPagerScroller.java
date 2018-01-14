package com.ifenglian.commonlib.widget.circlebanner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 文 件 名: ViewPagerScroller
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/14
 * 邮   箱: xiaofy@ifenglian.com
 * <p>
 * ＊由于ViewPager 默认的切换速度有点快，因此用一个Scroller 来控制切换的速度
 * <p>而实际上ViewPager 切换本来就是用的Scroller来做的，因此我们可以通过反射来</p>
 * <p>获取取到ViewPager 的 mScroller 属性，然后替换成我们自己的Scroller</p>
 */
public class ViewPagerScroller extends Scroller {
    private int mDuration = 800;// ViewPager默认的最大Duration 为600,我们默认稍微大一点。值越大越慢。
    private boolean mIsUseDefaultDuration = false;

    ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mIsUseDefaultDuration ? duration : mDuration);
    }

    public void setUseDefaultDuration(boolean useDefaultDuration) {
        mIsUseDefaultDuration = useDefaultDuration;
    }

    public boolean isUseDefaultDuration() {
        return mIsUseDefaultDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }


    public int getScrollDuration() {
        return mDuration;
    }
}
