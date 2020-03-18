package com.enzo.commonlib.widget.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.enzo.commonlib.utils.common.DensityUtil;

public class ColorWheelSelector extends View {

    private int currentColor;
    private Paint selectorPaint;
    private float selectorRadiusPx;
    private PointF currentPoint = new PointF();
    private float strokeWidth;

    public ColorWheelSelector(Context context) {
        this(context, null);
    }

    public ColorWheelSelector(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorWheelSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        selectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectorPaint.setColor(Color.TRANSPARENT);
        selectorPaint.setStyle(Paint.Style.FILL);
        setSelectorRadiusPx(DensityUtil.dip2px(10));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        selectorPaint.setColor(Color.WHITE);
        canvas.drawCircle(currentPoint.x, currentPoint.y, selectorRadiusPx, selectorPaint);

        selectorPaint.setColor(currentColor);
        canvas.drawCircle(currentPoint.x, currentPoint.y, strokeWidth, selectorPaint);
    }

    public void setSelectorRadiusPx(float selectorRadiusPx) {
        this.selectorRadiusPx = selectorRadiusPx;
        strokeWidth = selectorRadiusPx - DensityUtil.dip2px(2);
    }

    public void setCurrentPoint(PointF currentPoint, int color) {
        this.currentPoint = currentPoint;
        this.currentColor = color;
        invalidate();
    }
}
