package com.enzo.commonlib.widget.loadinglayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.enzo.commonlib.utils.common.DensityUtil;

/**
 * 文 件 名: LoadingView
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-14
 * 邮   箱: xiaofangyin@360.cn
 */
public class LoadingView extends View {

    private final int scaleValue = DensityUtil.dip2px(getContext(), 3f);
    private int color1 = Color.parseColor("#4ff3a9");//中间
    private int color2 = Color.parseColor("#bffee7");//右边
    private int color3 = Color.parseColor("#83f7c4");//左边
    private Paint paint;
    private RectF scaleRectF;
    private int pointRadius;//小圆半径
    private float runRadius;//正常半径
    private float scaleRadius;//缩放后的半径
    private float percent;//下拉距离比例
    private float animatedValue;
    private ValueAnimator valueAnimator;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 3.5f));

        pointRadius = DensityUtil.dip2px(getContext(), 3);
        float avgHeight = h / 3f;
        runRadius = h / 2f - avgHeight;

        scaleRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (valueAnimator != null && valueAnimator.isRunning()) {
            if (animatedValue < 360) {
                paint.setStyle(Paint.Style.FILL);
                float percent = animatedValue % 360 / 360f;
                scaleRadius = runRadius - scaleValue * percent;
                double x1 = getWidth() / 2f + Math.cos(Math.toRadians(-90 + animatedValue % 360)) * scaleRadius;
                double y1 = getHeight() / 2f + Math.sin(Math.toRadians(-90 + animatedValue % 360)) * scaleRadius;
                paint.setColor(color1);
                canvas.drawCircle((float) x1, (float) y1, pointRadius, paint);

                double x2 = getWidth() / 2f + Math.cos(Math.toRadians(30 + animatedValue % 360)) * scaleRadius;
                double y2 = getHeight() / 2f + Math.sin(Math.toRadians(30 + animatedValue % 360)) * scaleRadius;
                paint.setColor(color2);
                canvas.drawCircle((float) x2, (float) y2, pointRadius, paint);

                double x3 = getWidth() / 2f + Math.cos(Math.toRadians(150 + animatedValue % 360)) * scaleRadius;
                double y3 = getHeight() / 2f + Math.sin(Math.toRadians(150 + animatedValue % 360)) * scaleRadius;
                paint.setColor(color3);
                canvas.drawCircle((float) x3, (float) y3, pointRadius, paint);
            } else if (animatedValue < 360 * 2) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                float rotateDegree = animatedValue % 360;
                scaleRectF.left = getWidth() / 2f - runRadius - (scaleRadius - runRadius) * (1 - rotateDegree / 360f);
                scaleRectF.top = getHeight() / 2f - runRadius - (scaleRadius - runRadius) * (1 - rotateDegree / 360f);
                scaleRectF.right = getWidth() / 2f + runRadius + (scaleRadius - runRadius) * (1 - rotateDegree / 360f);
                scaleRectF.bottom = getHeight() / 2f + runRadius + (scaleRadius - runRadius) * (1 - rotateDegree / 360f);

                paint.setColor(color1);
                canvas.drawArc(scaleRectF, -90 + rotateDegree, rotateDegree / 360 * (360f / 3 - 45) + 10, false, paint);

                paint.setColor(color2);
                canvas.drawArc(scaleRectF, 30 + rotateDegree, rotateDegree / 360 * (360f / 3 - 45) + 10, false, paint);

                paint.setColor(color3);
                canvas.drawArc(scaleRectF, 150 + rotateDegree, rotateDegree / 360 * (360f / 3 - 45) + 10, false, paint);
            } else if (animatedValue < 360 * 3) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                float rotateDegree = animatedValue % 360;

                paint.setColor(color1);
                canvas.drawArc(scaleRectF, -90 + rotateDegree, 90, false, paint);

                paint.setColor(color2);
                canvas.drawArc(scaleRectF, 30 + rotateDegree, 90, false, paint);

                paint.setColor(color3);
                canvas.drawArc(scaleRectF, 150 + rotateDegree, 90, false, paint);
            } else {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                float rotateDegree = animatedValue % 360;

                paint.setColor(color1);
                canvas.drawArc(scaleRectF, -90 + rotateDegree, 80 - rotateDegree / 360 * (360f / 3 - 45), false, paint);

                paint.setColor(color2);
                canvas.drawArc(scaleRectF, 30 + rotateDegree, 80 - rotateDegree / 360 * (360f / 3 - 45), false, paint);

                paint.setColor(color3);
                canvas.drawArc(scaleRectF, 150 + rotateDegree, 80 - rotateDegree / 360 * (360f / 3 - 45), false, paint);
            }
        }
    }

    public void start() {
        valueAnimator = ValueAnimator.ofFloat(0, 360 * 4f);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(1000 * 4);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void stop() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
            valueAnimator = null;
        }
        animatedValue = 0f;
    }

}
