package com.enzo.module_d.ui.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.enzo.module_d.R;
import com.google.android.material.appbar.AppBarLayout;

/**
 * 上滑时
 * 当AppBarLayout由展开到收起时，会依次调用onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onStopNestedScroll()
 * 当AppBarLayout收起后继续向上滑动时，会依次调用onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onNestedScroll()->onStopNestedScroll()
 * <p>
 * 下滑时
 * 当AppBarLayout全部展开时（即未到顶部时），会依次调用onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onNestedScroll()->onStopNestedScroll()
 * 当AppBarLayout全部展开时（即到顶部时），继续向下滑动屏幕，会依次调用onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onNestedScroll()->onStopNestedScroll()
 * <p>
 * <p>
 * 当有快速滑动时会在onStopNestedScroll()前依次调用onNestedPreFling()->onNestedFling()
 * 所以要修改AppBarLayout的越界行为可以重写onNestedPreScroll()或onNestedScroll()，因为AppBarLayout收起时不会调用onNestedScroll()，所以只能选择重写onNestedPreScroll()，具体原因下面会有说明。
 */
public class MDAppBarLayoutOverScrollViewBehavior extends AppBarLayout.Behavior {

    private int mAppBarHeight;
    private View mCardView;
    private boolean isAnimate;
    private float mTotalDy;
    private int mCardViewHeight;
    private int mLimitHeight;
    private View mToolBar;
    private float scaleValue = 2f / 3;// 显示卡片的三分之一 所以抛出三分之二
    private View mNameTitle;
    private ValueAnimator valueAnimator;

    public MDAppBarLayoutOverScrollViewBehavior() {
    }

    public MDAppBarLayoutOverScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, abl, layoutDirection);
        if (null == mCardView) {
            mCardView = parent.findViewById(R.id.cardview);
        }
        if (null == mToolBar) {
            mToolBar = parent.findViewById(R.id.toolbar);
        }
        if (null == mNameTitle) {
            mNameTitle = parent.findViewById(R.id.name);
        }

        init(abl);
        return handled;
    }


    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        if (velocityY > 100) {
            isAnimate = false;
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        //恢复位置
        if (abl.getBottom() > mLimitHeight) {
            recovery(abl);
        }
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        //开始滚动了 就动画归位
        isAnimate = true;
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (mCardView != null && ((dy <= 0 && child.getBottom() >= mLimitHeight) || (dy > 0 && child.getBottom() > mLimitHeight))) {
            scrollY(child, target, dy);
        } else {
            setViewAlpha(child, dy);
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull MotionEvent ev) {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            return true;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull MotionEvent ev) {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            return false;
        }
        return super.onTouchEvent(parent, child, ev);
    }

    /**
     * 初始化数据
     */
    private void init(final AppBarLayout appBarLayout) {
        appBarLayout.setClipChildren(false);
        //整个AppbarLayout高度
        mAppBarHeight = appBarLayout.getMeasuredHeight();
        //卡片的高度
        mCardViewHeight = mCardView.getMeasuredHeight();
        //折叠正常的高度
        mLimitHeight = mAppBarHeight - (int) (mCardViewHeight * scaleValue);
        appBarLayout.setBottom(mLimitHeight);

        //默认1s折叠
        valueAnimator = ValueAnimator.ofFloat(1f, 0, 1f).setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                appBarLayout.setBottom((int) (mAppBarHeight - value * mCardViewHeight * scaleValue));
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                valueAnimator = null;
            }
        });
        appBarLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (valueAnimator != null) {
                    valueAnimator.start();
                }
            }
        }, 100);
    }


    /**
     * 混动
     */
    private void scrollY(AppBarLayout child, View target, int dy) {
        mTotalDy += -dy;
        mTotalDy = Math.min(mTotalDy, mLimitHeight);
        float mLastScale = Math.max(1f, 1f + (mTotalDy / mLimitHeight));
        int mLastBottom = mLimitHeight + (int) (mCardViewHeight * scaleValue * (mLastScale - 1));
        child.setBottom(mLastBottom);
        target.setScrollY(0);
    }


    /**
     * 根据滑动设置 toolbar  名字显示效果
     */
    private void setViewAlpha(View target, int dy) {
        float percent = Math.abs(target.getY() / mLimitHeight);
        if (percent >= 1) {
            percent = 1f;
        }
        //设置toolbar的透明度
        mToolBar.setAlpha(percent * 1.3f > 1 ? 1 : percent * 1.3f);

        //设置名字缩放
        mNameTitle.setScaleX(Math.max(0.8f, 1 - percent * 0.2f));
        mNameTitle.setScaleY(Math.max(0.8f, 1 - percent * 0.2f));

        //设置名字平移
        int offset2 = (mToolBar.getBottom() / 2) + (mNameTitle.getHeight() / 2 - (mToolBar.getBottom() - mNameTitle.getTop()));
        mNameTitle.setTranslationY(-offset2 * percent);
    }

    /**
     * 恢复位置
     */
    private void recovery(final AppBarLayout abl) {
        if (mTotalDy >= 0) {
            mTotalDy = 0;
            if (isAnimate) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f).setDuration(200);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        int offsetY = abl.getBottom() - mLimitHeight;
                        abl.setBottom((int) (abl.getBottom() - (value * offsetY)));
                        abl.setScrollY(0);
                    }
                });
                valueAnimator.start();
            } else {
                abl.setBottom(mLimitHeight);
                abl.setScrollY(0);
            }
        }
    }


}
