package com.ifenglian.commonlib.widget.view.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 文 件 名: SRDiskCapacitySeekBar
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/5
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SRDiskCapacityProgressBar extends View {

    private long mCurrentProgress = 50;
    private long mTotalProgress = 100;
    private Paint paint;
    private TextPaint mTextPaint;
    private RectF rectF;
    private Path path;
    private String text = "2G/32G";

    public SRDiskCapacityProgressBar(Context context) {
        this(context, null);
    }

    public SRDiskCapacityProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SRDiskCapacityProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);// 填充方式为描边
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);// 抗锯齿
        paint.setDither(true);// 使用抖动效果

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(sp2px(12));
        mTextPaint.setColor(Color.WHITE);

        rectF = new RectF();
        path = new Path();
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
        float[] radii = {getHeight() / 2, getHeight() / 2, 0f, 0f, 0f, 0f, getHeight() / 2, getHeight() / 2};
        path.addRoundRect(rectF, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float baseline = (rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(text, getWidth() / 2, baseline, mTextPaint);
    }

    public void setProgress(long progress, long totalProgress) {
        mCurrentProgress = progress;
        mTotalProgress = totalProgress;
        text = progress + "/" + totalProgress;
    }

    private float dip2px(Context context, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
