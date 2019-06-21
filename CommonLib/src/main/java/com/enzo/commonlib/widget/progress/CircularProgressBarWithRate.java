package com.enzo.commonlib.widget.progress;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.enzo.commonlib.R;

/**
 * 文 件 名: CircularProgressBarWithRate
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofangyinwork@163.com
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
        mRateText.setTextColor(getResources().getColor(R.color.color_white));
        mRateText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

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
     */
    public void setProgress(int progress) {
        mCircularProgressBar.setProgress(progress);
    }

    /**
     * 设置环形的宽度
     */
    public void setCircleWidth(float circleWidth) {
        mCircularProgressBar.setCircleWidth(circleWidth);
    }

    /**
     * 得到 CircularProgressBar 对象，用来设置其他的一些属性
     */
    public CircularProgressBar getCircularProgressBar() {
        return mCircularProgressBar;
    }

    /**
     * 设置中间进度百分比文字的尺寸
     */
    public void setTextSize(float size) {
        mRateText.setTextSize(size);
    }

    /**
     * 设置中间进度百分比文字的颜色
     */
    public void setTextColor(int color) {
        mRateText.setTextColor(color);
    }

    @Override
    public void onChange(int duration, int progress, float rate) {
        mRateText.setText(String.valueOf((int) (rate * 100) + "%"));
    }

}
