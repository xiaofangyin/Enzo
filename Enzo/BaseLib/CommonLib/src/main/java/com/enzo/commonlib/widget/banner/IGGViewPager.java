package com.enzo.commonlib.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

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
public class IGGViewPager extends ViewPager {

    private final ArrayList<Integer> childCenterXAbs = new ArrayList<>();
    private final SparseArray<Integer> childIndex = new SparseArray();
    private boolean hasActivityDestroy;

    public IGGViewPager(Context context) {
        this(context, null);
    }

    public IGGViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClipToPadding(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        initViewPagerScroll();
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            IGGViewPagerScroller mViewPagerScroller = new IGGViewPagerScroller(getContext());
            mScroller.set(this, mViewPagerScroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param childCount
     * @param n
     * @return 第n个位置的child 的绘制索引
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int n) {
        if (n == 0 || childIndex.size() != childCount) {
            childCenterXAbs.clear();
            childIndex.clear();
            int viewCenterX = getViewCenterX(this);
            for (int i = 0; i < childCount; ++i) {
                int indexAbs = Math.abs(viewCenterX - getViewCenterX(getChildAt(i)));
                //两个距离相同，后来的那个做自增，从而保持abs不同
                if (childIndex.get(indexAbs) != null) {
                    ++indexAbs;
                }
                childCenterXAbs.add(indexAbs);
                childIndex.append(indexAbs, i);
            }
            Collections.sort(childCenterXAbs);//1,0,2  0,1,2
        }
        //那个item距离中心点远一些，就先draw它。（最近的就是中间放大的item,最后draw）
        return childIndex.get(childCenterXAbs.get(childCount - 1 - n));
    }

    private int getViewCenterX(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);
        return array[0] + view.getWidth() / 2;
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
