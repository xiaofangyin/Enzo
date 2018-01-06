package com.ifenglian.commonlib.widget.view.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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

    private long mCurrentProgress = 98;
    private long mTotalProgress = 100;
    private int mWidth, mHeight;
    private Paint paint;
    private TextPaint mTextPaint;
    private RectF rectF;
    private String text = "16G/32G";
    private PorterDuffXfermode porterDuffXfermode;

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
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, paint, Canvas.ALL_SAVE_FLAG);
        paint.setColor(0xFFCFCFCF);
        rectF.set(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectF, mHeight / 2, mHeight / 2, paint);

        paint.setColor(0xFF30B5FF);
        paint.setXfermode(porterDuffXfermode);
        rectF.set(0, 0, mWidth * mCurrentProgress / mTotalProgress, mHeight);
        canvas.drawRect(rectF, paint);
        paint.setXfermode(null);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float baseline = (rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(text, mWidth / 2, baseline, mTextPaint);


        //还原画布，与canvas.saveLayer配套使用
        canvas.restoreToCount(sc);
    }

    public void setProgress(long progress, long totalProgress) {
        mCurrentProgress = progress;
        mTotalProgress = totalProgress;
        text = progress + "/" + totalProgress;
        invalidate();
    }

    private float dip2px(Context context, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
