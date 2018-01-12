package com.ifenglian.commonlib.widget.progress;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 文 件 名: CircularProgressBarWithRate
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public class CircularProgressBarWithRate extends FrameLayout implements CircularProgressBar.OnProgressChangeListener {

    private CircularProgressBar mCircularProgressBar;
    private TextView mRateText;

    public CircularProgressBarWithRate(Context context) {
        super(context);
        init();
    }

    public CircularProgressBarWithRate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mCircularProgressBar = new CircularProgressBar(getContext());
        this.addView(mCircularProgressBar);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        mCircularProgressBar.setLayoutParams(lp);

        mRateText = new TextView(getContext());
        this.addView(mRateText);
        mRateText.setLayoutParams(lp);
        mRateText.setGravity(Gravity.CENTER);
        mRateText.setTextColor(Color.YELLOW);
        mRateText.setTextSize(20);

        mCircularProgressBar.setOnProgressChangeListener(this);
    }

    /**
     * 设置最大值
     */
    public void setMax(int max) {
        mCircularProgressBar.setMax(max);
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        mCircularProgressBar.setProgress(progress);
    }

    /**
     * 设置环形的宽度
     *
     * @param circleWidth
     */
    public void setCircleWidth(float circleWidth) {
        mCircularProgressBar.setCircleWidth(circleWidth);
    }

    /**
     * 得到 CircularProgressBar 对象，用来设置其他的一些属性
     *
     * @return
     */
    public CircularProgressBar getCircularProgressBar() {
        return mCircularProgressBar;
    }

    /**
     * 设置中间进度百分比文字的尺寸
     *
     * @param size
     */
    public void setTextSize(float size) {
        mRateText.setTextSize(size);
    }

    /**
     * 设置中间进度百分比文字的颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        mRateText.setTextColor(color);
    }

    @Override
    public void onChange(int duration, int progress, float rate) {
        mRateText.setText(String.valueOf((int) (rate * 100) + "%"));
    }

}
