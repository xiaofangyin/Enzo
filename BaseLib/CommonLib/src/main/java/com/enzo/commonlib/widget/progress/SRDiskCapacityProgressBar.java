package com.enzo.commonlib.widget.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

/**
 * 文 件 名: SRDiskCapacitySeekBar
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/1/5
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SRDiskCapacityProgressBar extends View {

    private long mCurrentProgress;
    private long mTotalProgress;
    private int mWidth, mHeight;
    private Paint paint;
    private TextPaint mTextPaint;
    private RectF rectF;
    private String text = "/";
    private PorterDuffXfermode porterDuffXfermode;
    private DecimalFormat decimalFormat;

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
        //0 阿拉伯数字
        //# 阿拉伯数字，如果不存在则显示为空
        decimalFormat = new DecimalFormat("0.##");
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
        //设置View的离屏缓冲。在绘图的时候新建一个“层”，所有的操作都在该层而不会影响该层以外的图像
        //必须设置，否则设置的PorterDuffXfermode会无效
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, paint, Canvas.ALL_SAVE_FLAG);
        paint.setColor(0xFFCFCFCF);
        rectF.set(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectF, mHeight / 2, mHeight / 2, paint);

        paint.setColor(0xFF30B5FF);
        paint.setXfermode(porterDuffXfermode);
        rectF.set(0, 0, mTotalProgress == 0 ? 0 : mWidth * mCurrentProgress / mTotalProgress, mHeight);
        canvas.drawRect(rectF, paint);
        paint.setXfermode(null);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float baseline = mHeight / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(text, mWidth / 2, baseline, mTextPaint);

        //还原画布，与canvas.saveLayer配套使用
        canvas.restoreToCount(sc);
    }

    public void setProgress(long progress, long totalProgress) {
        mCurrentProgress = progress;
        mTotalProgress = totalProgress;
        text = getPrintSize(progress) + "/" + getPrintSize(totalProgress);
        invalidate();
    }

    /**
     * MB、GB单位换算
     *
     * @param size 单位 MB
     */
    private String getPrintSize(long size) {
        return decimalFormat.format(size * 1f / 1024) + "GB";
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
