package com.enzo.commonlib.widget.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 文 件 名: UGCWebViewProgressBar
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/9/16
 * 邮   箱: xiaofangyinwork@163.com
 */
public class UGCWebViewProgressBar extends View {

    private int progress = 1;
    private Paint paint;

    public UGCWebViewProgressBar(Context context) {
        this(context, null);
    }

    public UGCWebViewProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UGCWebViewProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);// 抗锯齿
        paint.setDither(true);// 使用抖动效果
    }

    /**
     * 设置进度
     *
     * @param progress 进度值
     */
    public void setProgress(int progress) {
        if (this.progress != progress) {
            this.progress = progress;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.parseColor("#2604d1aa"));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint.setColor(Color.parseColor("#04d1aa"));
        canvas.drawRect(0, 0, getWidth() * progress / 100, getHeight(), paint);
    }
}