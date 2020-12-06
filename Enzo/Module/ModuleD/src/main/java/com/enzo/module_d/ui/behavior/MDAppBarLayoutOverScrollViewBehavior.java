package com.enzo.module_d.ui.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.enzo.module_d.R;
import com.google.android.material.appbar.AppBarLayout;

import org.jetbrains.annotations.NotNull;

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

    private boolean initAnimShown;//是否初始化过
    private View mToolBar;
    private View mNameTitle;
    private View mCardView;
    private View mNestedView;
    private int mAppBarHeight;
    private int mCardViewHeight;
    private int mLimitHeight;
    private float mTotalDy;
    private float scaleValue = 2f / 3;// 显示卡片的三分之一 所以抛出三分之二
    private ValueAnimator valueAnimator;
    private ValueAnimator recoveryAnim;

    public MDAppBarLayoutOverScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NotNull CoordinatorLayout parent,
                                 @NotNull AppBarLayout appBarLayout,
                                 int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, appBarLayout, layoutDirection);
        if (null == mCardView) {
            mCardView = parent.findViewById(R.id.cardview);
        }
        if (null == mToolBar) {
            mToolBar = parent.findViewById(R.id.toolbar);
        }
        if (null == mNameTitle) {
            mNameTitle = parent.findViewById(R.id.name);
        }
        if (null == mNestedView) {
            mNestedView = parent.findViewById(R.id.nested_view);
        }
        init(appBarLayout);
        return handled;
    }


    @Override
    public boolean onNestedPreFling(@NotNull CoordinatorLayout coordinatorLayout,
                                    @NotNull AppBarLayout child,
                                    @NotNull View target,
                                    float velocityX,
                                    float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }


    @Override
    public boolean onStartNestedScroll(@NotNull CoordinatorLayout parent,
                                       @NotNull AppBarLayout child,
                                       @NotNull View directTargetChild,
                                       View target, int nestedScrollAxes, int type) {
        if (recoveryAnim != null && recoveryAnim.isRunning()) {
            recoveryAnim.cancel();
        }
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }

    @Override
    public void onNestedPreScroll(@NotNull CoordinatorLayout coordinatorLayout,
                                  @NotNull AppBarLayout child, View target,
                                  int dx, int dy, int[] consumed, int type) {
        if (mCardView != null && ((dy <= 0 && child.getBottom() >= mLimitHeight) || (dy > 0 && child.getBottom() > mLimitHeight))) {
            scrollY(child, target, dy);
        } else {
            setTitleScaleAndTranslate(child);
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }

    @Override
    public void onNestedScroll(@NotNull CoordinatorLayout coordinatorLayout,
                               @NonNull AppBarLayout child,
                               View target,
                               int dxConsumed,
                               int dyConsumed,
                               int dxUnconsumed,
                               int dyUnconsumed,
                               int type,
                               int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    }

    @Override
    public void onStopNestedScroll(@NotNull CoordinatorLayout coordinatorLayout,
                                   @NotNull AppBarLayout appBarLayout,
                                   View target,
                                   int type) {
        super.onStopNestedScroll(coordinatorLayout, appBarLayout, target, type);
        //恢复位置
        if (appBarLayout.getBottom() > mLimitHeight) {
            if (recoveryAnim == null) {
                recovery(appBarLayout);
            }
        } else {
            if (type == ViewCompat.TYPE_NON_TOUCH) {
                setTitleScaleAndTranslate(appBarLayout);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull MotionEvent ev) {
        if ((valueAnimator != null)) {
            return true;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent,
                                @NonNull AppBarLayout child,
                                @NonNull MotionEvent ev) {
        if ((valueAnimator != null)) {
            return false;
        }
        if (ev.getY() > child.getTop() && ev.getY() < child.getBottom()) {
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

        appBarLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                appBarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mNestedView.setBottom(mNestedView.getBottom() + (int) (mCardViewHeight * scaleValue));
            }
        });

        if (!initAnimShown) {
            initAnimShown = true;
            //默认1s折叠
            valueAnimator = ValueAnimator.ofFloat(1f, 0, 0, 0, 0, 1f).setDuration(1500);
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
    private void setTitleScaleAndTranslate(View appBarLayout) {
        float percent = Math.abs(appBarLayout.getY() / mLimitHeight);
        if (percent >= 1) {
            percent = 1f;
        }
        //设置toolbar的透明度
        mToolBar.setAlpha(percent);

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
    private void recovery(final AppBarLayout appBarLayout) {
        if (mTotalDy >= 0) {
            recoveryAnim = ValueAnimator.ofFloat(appBarLayout.getBottom(), mLimitHeight).setDuration(200);
            recoveryAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    appBarLayout.setBottom((int) value);
                    appBarLayout.setScrollY(0);
                }
            });
            recoveryAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setTitleScaleAndTranslate(appBarLayout);
                    recoveryAnim = null;
                    mTotalDy = 0;
                }
            });
            recoveryAnim.start();
        }
    }


}
