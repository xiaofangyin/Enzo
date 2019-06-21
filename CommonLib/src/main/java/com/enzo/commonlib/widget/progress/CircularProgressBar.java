package com.enzo.commonlib.widget.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.enzo.commonlib.R;

/**
 * 文 件 名: CircularProgressBar
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofangyinwork@163.com
 */
public class CircularProgressBar extends View {

    private Paint mPaint;
    private RectF mRectF;
    private int mMaxProgress;
    private int mProgress;
    private int mBackgroundColor;
    private int mPrimaryColor;
    private float mStrokeWidth;

    public CircularProgressBar(Context context) {
        this(context, null);
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mRectF = new RectF();
        mMaxProgress = 100;
        mProgress = 0;
        mBackgroundColor = Color.LTGRAY;
        mPrimaryColor = getResources().getColor(R.color.color_green);
        mStrokeWidth = dip2px(getContext(), 5);
    }

    /**
     * 圆环跟圆弧的半径： radius - mStrokeWidth / 2
     */
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int halfWidth = getWidth() / 2;
        int halfHeight = getHeight() / 2;
        int radius = halfWidth < halfHeight ? halfWidth : halfHeight;

        // 设置画笔
        mPaint.setColor(mBackgroundColor);
        mPaint.setDither(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE); // 设置图形为空心

        // 画背景
        canvas.drawCircle(halfWidth, halfHeight, radius - mStrokeWidth / 2, mPaint);

        // 画当前进度的圆环
        mPaint.setColor(mPrimaryColor); // 改变画笔颜色
        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆角弧
        mRectF.top = halfHeight - radius + mStrokeWidth / 2;
        mRectF.bottom = halfHeight + radius - mStrokeWidth / 2;
        mRectF.left = halfWidth - radius + mStrokeWidth / 2;
        mRectF.right = halfWidth + radius - mStrokeWidth / 2;
        canvas.drawArc(mRectF, -90, getRateOfProgress() * 360, false, mPaint);
        canvas.save();
    }

    public void setMax(int max) {
        if (max < 0) {
            max = 0;
        }
        mMaxProgress = max;
    }

    public void setProgress(int progress) {
        if (progress > mMaxProgress) {
            progress = mMaxProgress;
        }
        mProgress = progress;
        if (mOnChangeListener != null) {
            mOnChangeListener
                    .onChange(mMaxProgress, progress, getRateOfProgress());
        }
        invalidate();
    }

    /**
     * 得到进度条当前的值
     *
     * @return
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置进度条背景的颜色
     */
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    /**
     * 设置进度条进度的颜色
     */
    public void setPrimaryColor(int color) {
        mPrimaryColor = color;
    }

    /**
     * 设置环形的宽度
     *
     * @param width
     */
    public void setCircleWidth(float width) {
        mStrokeWidth = width;
    }


    private float getRateOfProgress() {
        return (float) mProgress / mMaxProgress;
    }

    private OnProgressChangeListener mOnChangeListener;

    public void setOnProgressChangeListener(OnProgressChangeListener l) {
        mOnChangeListener = l;
    }

    public interface OnProgressChangeListener {
        public void onChange(int duration, int progress, float rate);
    }

    private float dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dip * scale;
    }
}
