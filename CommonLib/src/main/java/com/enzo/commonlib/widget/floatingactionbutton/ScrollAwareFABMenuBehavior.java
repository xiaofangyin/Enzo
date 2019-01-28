package com.enzo.commonlib.widget.floatingactionbutton;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.enzo.commonlib.utils.common.DensityUtil;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Lynn on 2016-7-11.
 */

public class ScrollAwareFABMenuBehavior extends CoordinatorLayout.Behavior<View> {

    private boolean isShowed = true;
    private float value;
    private ObjectAnimator objectAnimator;

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull View child,
                               @NonNull View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (dyConsumed > 5) {
            if (isShowed) {
                isShowed = false;
                startAnim(child, value, DensityUtil.dip2px(150));
            }
        } else if (dyConsumed < -5) {
            if (!isShowed) {
                isShowed = true;
                startAnim(child, value, 0);
            }
        }
    }

    private void startAnim(final View view, float start, float end) {
        if (objectAnimator != null && objectAnimator.isRunning()) {
            objectAnimator.removeAllUpdateListeners();
            objectAnimator.cancel();
        }

        objectAnimator = ObjectAnimator.ofFloat(view, "translationY", start, end);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                value = (float) valueAnimator.getAnimatedValue();
            }
        });
        objectAnimator.start();
    }
}