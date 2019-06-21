package com.enzo.commonlib.widget.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 文 件 名: HorizontalProgressBar
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/6/6
 * 邮   箱: xiaofangyinwork@163.com
 */
public class HorizontalProgressBar extends View {

    private int progress = 1;//进度默认为1
    private Paint paint;//进度条的画笔

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);// 填充方式为描边
        paint.setAntiAlias(true);// 抗锯齿
        paint.setDither(true);// 使用抖动效果
        paint.setColor(0xff80ff00);//画笔设置颜色
    }

    /**
     * 设置进度
     *
     * @param progress 进度值
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();//刷新画笔
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth() * progress / 100, dip2px(getContext(), 3), paint);//画矩形从（0.0）开始到（progress,height）的区域
    }

    private float dip2px(Context context, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}