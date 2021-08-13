package com.enzo.commonlib.widget.togglebutton.ios;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.enzo.commonlib.R;

/**
 * 文 件 名: TabLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/1/11
 * 邮   箱: xiaofangyinwork@163.com
 */
public class FlToggleButton extends View implements OnClickListener {

    private Paint paint;
    private RadialGradient shadowGradient;
    private boolean isOpen = false;//现在状态 true 开  false 关
    private int onColor = Color.parseColor("#FFFFDA44");
    private int offColor = Color.parseColor("#FFFFFFFF");
    private int strokeColor = Color.parseColor("#8CAAAAAA");
    private int width;//宽度
    private int height;//高度
    private int centerY;//垂直中间坐标
    private int padding;//内边距
    private int margin;//圆球里圆角矩形距离
    private float radius;//圆形半径
    private float maxLeft;//滑动最大距离
    private float minLeft;//滑动最小距离
    private float slideBtn_left;//滑动按钮的圆心X坐标
    private RectF rectF;//圆角矩形

    public FlToggleButton(Context context) {
        this(context, null);
    }

    public FlToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlToggleButton);
        try {
            isOpen = a.getBoolean(R.styleable.FlToggleButton_fl_defaultState, false);
        } finally {
            a.recycle();
        }
        // 初始化 画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        // 初始化 圆角矩形
        rectF = new RectF();
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minimumWidth = getSuggestedMinimumWidth();
        int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = (int) dip2px(55);
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = (int) dip2px(34);
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                break;
        }
        return defaultHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        padding = dip2px(2f);
        margin = dip2px(1f);
        width = w - padding * 2;
        height = h - padding * 2;
        centerY = h / 2;
        radius = h / 2f - margin - padding;
        minLeft = radius + margin + padding;
        maxLeft = w - minLeft;
        slideBtn_left = isOpen ? maxLeft : minLeft;
        shadowGradient = new RadialGradient(minLeft, centerY, radius, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //主要用于控制offRectF的大小，isOpen为true时，percent = 1;isOpen为false时，percent = 0;
        float percent = (slideBtn_left - minLeft) / (getWidth() - radius * 2 - margin * 2 - padding * 2);
        if (isEnabled()) {//可用状态
            paint.reset();
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setColor(onColor);
            rectF.set(padding, padding, width + padding, height + padding);
            canvas.drawRoundRect(rectF, height / 2f, height / 2f, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(offColor);
            rectF.set(width / 2f * percent + padding, height / 2f * percent + padding,
                    width - width / 2f * percent + padding, height - height / 2f * percent + padding);
            canvas.drawRoundRect(rectF, height / 2f * (1 - percent), height / 2f * (1 - percent), paint);

            if (!isOpen) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(strokeColor);
                rectF.set(padding, padding, width + padding, height + padding);
                canvas.drawRoundRect(rectF, height / 2f, height / 2f, paint);
            }

            //绘制阴影
            canvas.save();
            canvas.translate(slideBtn_left - minLeft, radius * 0.2f);
            paint.setStyle(Paint.Style.FILL);
            paint.setShader(shadowGradient);
            canvas.drawCircle(minLeft, centerY, radius, paint);
            paint.setShader(null);
            canvas.restore();

            //绘制实心圆
            paint.setColor(Color.WHITE);
            canvas.drawCircle(slideBtn_left, centerY, radius, paint);

            //绘制边框
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawCircle(slideBtn_left, centerY, radius, paint);
        } else {//不可用状态
            paint.reset();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setColor(offColor);
            paint.setAlpha(127);//半透明
            rectF.set(padding, padding, getWidth() - padding, getHeight() - padding);
            canvas.drawRoundRect(rectF, height / 2f, height / 2f, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            paint.setAlpha(127);//半透明
            rectF.set(padding, padding, width + padding, height + padding);
            canvas.drawRoundRect(rectF, height / 2f, height / 2f, paint);

            //绘制阴影
            canvas.save();
            canvas.translate(slideBtn_left - minLeft, radius * 0.2f);
            paint.setStyle(Paint.Style.FILL);
            paint.setShader(shadowGradient);
            canvas.drawCircle(minLeft, centerY, radius, paint);
            paint.setShader(null);
            canvas.restore();

            //绘制实心圆
            paint.setColor(Color.WHITE);
            canvas.drawCircle(slideBtn_left, centerY, radius, paint);

            //绘制边框
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawCircle(slideBtn_left, centerY, radius, paint);
        }
    }

    @Override
    public void onClick(View v) {
        if (isOpen)
            toggleOff();
        else
            toggleOn();
    }

    /**
     * 设置显示成打开样式，触发toggle事件
     */
    public void toggleOn() {
        setToggleOn(true);
        if (toggleListener != null)
            toggleListener.onToggle(true);
    }

    /**
     * 设置显示成关闭样式，触发toggle事件
     */
    public void toggleOff() {
        setToggleOff(true);
        if (toggleListener != null)
            toggleListener.onToggle(false);
    }

    /**
     * 设置显示成打开样式，不会触发toggle事件,默认不带动画
     */
    public void setToggleOn() {
        setToggleOn(false);
    }

    /**
     * 设置显示成关闭样式，不会触发toggle事件,默认不带动画
     */
    public void setToggleOff() {
        setToggleOff(false);
    }

    /**
     * 注：如果在activity onCreate()中调用setToggleOn(true),由于这时View还没执行onMeasure(),slideBtn_left跟maxLeft
     * 都是0，因此加个getWidth != 0判断
     *
     * @param animate 是否需要动画
     */
    public void setToggleOn(boolean animate) {
        if (!isOpen) {
            isOpen = true;
            if (animate && getWidth() != 0) {
                startValueAnimator(slideBtn_left, maxLeft);
            } else {
                slideBtn_left = maxLeft;
                invalidate();
            }
        }
    }

    /**
     * 注：如果在activity onCreate()中调用setToggleOff(true),由于这时View还没执行onMeasure(),slideBtn_left跟minLeft
     * 都是0，因此加个getWidth != 0判断
     *
     * @param animate 是否需要动画
     */
    public void setToggleOff(boolean animate) {
        if (isOpen) {
            isOpen = false;
            if (animate && getWidth() != 0) {
                startValueAnimator(slideBtn_left, minLeft);
            } else {
                slideBtn_left = minLeft;
                invalidate();
            }
        }
    }

    /**
     * 现在开关状态
     *
     * @return 开关状态
     */
    public boolean getState() {
        return isOpen;
    }

    /**
     * 改为相反状态
     */
    public void revertState() {
        isOpen = !isOpen;
        startValueAnimator(slideBtn_left, isOpen ? maxLeft : minLeft);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        invalidate();
    }

    private ValueAnimator valueAnimator;

    private void startValueAnimator(float startX, float stopX) {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
        }
        valueAnimator = ValueAnimator.ofFloat(startX, stopX);
        valueAnimator.setDuration(300);
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                slideBtn_left = (Float) valueAnimator.getAnimatedValue();
                flushView();
            }
        });
        valueAnimator.start();
    }

    private void flushView() {
        // 确保 slideBtn_left >= minLeft
        slideBtn_left = Math.max(slideBtn_left, minLeft);
        // 确保 slideBtn_left <= maxLeft
        slideBtn_left = Math.min(slideBtn_left, maxLeft);
        invalidate();
    }

    private OnToggleChanged toggleListener;

    public void setOnToggleChanged(OnToggleChanged listener) {
        this.toggleListener = listener;
    }

    public interface OnToggleChanged {
        void onToggle(boolean on);
    }

    private int dip2px(float dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale);
    }
}
