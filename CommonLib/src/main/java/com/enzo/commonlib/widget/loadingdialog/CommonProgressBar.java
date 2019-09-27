package com.enzo.commonlib.widget.loadingdialog;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by clg on 2017/2/14.
 */

public class CommonProgressBar extends ViewGroup {

    @VisibleForTesting
    private static final int CIRCLE_DIAMETER = 40;
    private static final int CIRCLE_BG_LIGHT = Color.parseColor("#E6FFFFFF");
    private static final int MAX_ALPHA = 255;

    private CommonCircleImageView mCircleView;
    private CommonProgressDrawable mProgress;

    private int mCircleDiameter;

    public CommonProgressBar(Context context) {
        this(context, null);
    }

    public CommonProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mCircleDiameter = (int) (CIRCLE_DIAMETER * metrics.density);
        createProgressView();
    }

    private void createProgressView() {
        mCircleView = new CommonCircleImageView(getContext(), CIRCLE_BG_LIGHT);
        mProgress = new CommonProgressDrawable(getContext(), this);
        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        mCircleView.setImageDrawable(mProgress);
        mCircleView.setVisibility(View.VISIBLE);
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                addView(mCircleView);
                mProgress.setAlpha(MAX_ALPHA);
                mProgress.start();
            }
        }, 500);
    }

    public void stopAndGone() {
        if (mProgress != null) {
            mProgress.stop();
        }
        this.setVisibility(GONE);
        mCircleView.setVisibility(GONE);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();

        int circleWidth = mCircleView.getMeasuredWidth();
        int circleHeight = mCircleView.getMeasuredHeight();
        mCircleView.layout((width / 2 - circleWidth / 2), (height / 2 - circleHeight / 2),
                (width / 2 + circleWidth / 2), (height / 2 + circleHeight / 2));
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCircleView.measure(MeasureSpec.makeMeasureSpec(mCircleDiameter, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mCircleDiameter, MeasureSpec.EXACTLY));
    }
}
