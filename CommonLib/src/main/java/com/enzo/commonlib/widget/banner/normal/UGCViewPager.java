package com.enzo.commonlib.widget.banner.normal;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;

import java.lang.reflect.Field;

/**
 * 文 件 名: UGCViewPager
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/3/16
 * 邮   箱: xiaofangyinwork@163.com
 * <p>
 * 在RecyclerView中使用ViewPager时，会出现两个诡异的bug：
 * 1.RecyclerView滚动上去，直至ViewPager看不见，再滚动下来，ViewPager下一次切换没有动画
 * 2.当ViewPage滚动到一半的时候，RecyclerView滚动上去，再滚动下来，ViewPager会卡在一半
 */
public class UGCViewPager extends ViewPager {

    private boolean hasActivityDestroy;

    public UGCViewPager(Context context) {
        this(context, null);
    }

    public UGCViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewPagerScroll();
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            UGCViewPagerScroller mViewPagerScroller = new UGCViewPagerScroller(getContext());
            mScroller.set(this, mViewPagerScroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 问题：RecyclerView滚动上去，直至ViewPager看不见，再滚动下来，ViewPager下一次切换没有动画
     * ViewPager里有一个私有变量mFirstLayout，它是表示是不是第一次显示布局，
     * 如果是true，则使用无动画的方式显示当前item，
     * 如果是false，则使用动画方式显示当前item。
     * <p>
     * 当ViewPager滚动上去后，因为RecyclerView的回收机制，ViewPager会走onDetachFromWindow，
     * 当再次滚动下来时，ViewPager会走onAttachedToWindow
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(this, false);
            getAdapter().notifyDataSetChanged();
            setCurrentItem(getCurrentItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 问题：当ViewPage滚动到一半的时候，RecyclerView滚动上去，再滚动下来，ViewPager会卡在一半
     * 当activitydestroy的时候，给自定义ViewPager一个标志位hasActivityDestroy，
     * 只有hasActivityDestroy为true的时候，才调用父类的super.onDetachedFromWindow();
     */
    @Override
    protected void onDetachedFromWindow() {
        if (hasActivityDestroy) {
            super.onDetachedFromWindow();
        }
    }

    public void setHasDestroy(boolean hasDestroy) {
        hasActivityDestroy = hasDestroy;
    }
}
