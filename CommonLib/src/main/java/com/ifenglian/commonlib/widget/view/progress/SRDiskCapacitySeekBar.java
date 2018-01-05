package com.ifenglian.commonlib.widget.view.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 文 件 名: SRDiskCapacitySeekBar
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/5
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SRDiskCapacitySeekBar extends View {

    private long mCurrentProgress = 50;//进度默认为1
    private long mTotalProgress = 100;//进度默认为1
    private Paint paint;//进度条的画笔
    private RectF rectF;

    public SRDiskCapacitySeekBar(Context context) {
        this(context, null);
    }

    public SRDiskCapacitySeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SRDiskCapacitySeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);// 填充方式为描边
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);// 抗锯齿
        paint.setDither(true);// 使用抖动效果

        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(0xFFCFCFCF);
        rectF.set(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, getHeight() / 2, getHeight() / 2, paint);
        paint.setColor(0xFF30B5FF);
        rectF.set(0, 0, getWidth() * mCurrentProgress / mTotalProgress, getHeight());
        canvas.drawRoundRect(rectF, getHeight() / 2, getHeight() / 2, paint);
    }

    public void setProgress(long progress, long totalProgress) {
        mCurrentProgress = progress;
        mTotalProgress = totalProgress;
    }

    private float dip2px(Context context, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}
