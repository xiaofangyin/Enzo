package com.ifenglian.commonlib.widget.parallax;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;

/**
 * 让指定view在一段时间内改变到指定高度
 *
 * @author Administrator
 */
public class ResetHeightAnimation extends Animation {

    private View view;
    private int targetHeight;
    private int originalHeight;
    private int totalValue;

    public ResetHeightAnimation(View view, int targetHeight) {
        super();
        this.view = view;
        this.targetHeight = targetHeight;

        originalHeight = view.getHeight();
        totalValue = targetHeight - originalHeight;

        setDuration(400);
//		setInterpolator(new BounceInterpolator());
        setInterpolator(new OvershootInterpolator());
//		setInterpolator(new LinearInterpolator());
    }

    /**
     * interpolatedTime:0-1  标识动画执行的进度或者百分比
     * view: 10 - 60  -  110
     * time: 0  - 0.5 -  1
     */
    @Override
    protected void applyTransformation(float interpolatedTime,
                                       Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        int newHeight = (int) (originalHeight + totalValue * interpolatedTime);
//		if(newHeight<targetHeight)newHeight = targetHeight;
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }

}