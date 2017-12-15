package com.ifenglian.commonlib.widget.view.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.utils.common.DensityUtil;
import com.nineoldandroids.animation.ValueAnimator;

public class TabButton extends View {

    private Bitmap mDrawBitmap;
    //初始显示的图标
    private Bitmap mBitmap;
    //选中之后显示的图标
    private Bitmap mClickBitmap;
    //未选中的颜色
    private int mColor = 0xFFAAAAAA;
    //选中之后的颜色
    private int mClickColor = 0xFF30B5FF;
    //字体大小
    private float mTextSize;
    //显示的文本
    private String mText = "";
    //画图位置
    private Rect mBitmapRect;
    private Rect mTextRect;
    //文本的画笔
    private Paint mTextPaint;
    //记录消息数量
    private int mMessageNumber;
    private int mIconWidth;
    //icon离左、上的距离
    private int mMarginLeft, mMarginTop;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabButton);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TabButton_image) {
                BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                mBitmap = drawable.getBitmap();
            } else if (attr == R.styleable.TabButton_clickimage) {
                BitmapDrawable clickDrawable = (BitmapDrawable) a.getDrawable(attr);
                mClickBitmap = clickDrawable.getBitmap();
            } else if (attr == R.styleable.TabButton_clickcolor) {
                mClickColor = a.getColor(attr, 0xFF3F9FE0);
            } else if (attr == R.styleable.TabButton_text) {
                mText = a.getString(attr);
            } else if (attr == R.styleable.TabButton_text_size) {
                mTextSize = a.getDimension(attr, 12);
            }
        }
        a.recycle();

        mTextRect = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setColor(mColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        mTextPaint.setAntiAlias(true);//抗锯齿

        mDrawBitmap = mBitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("AAA", "onSizeChanged ...");
        mIconWidth = mBitmap.getWidth();
        mMarginLeft = w / 2 - mIconWidth / 2;
        mMarginTop = h / 2 - (mTextRect.height() + mIconWidth) / 2;
        mBitmapRect = new Rect(mMarginLeft, mMarginTop, mMarginLeft + mIconWidth, mMarginTop + mIconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("AAA", "onDraw ...");
        drawText(canvas);//绘制原文本
        drawBitmap(canvas, mDrawBitmap);
        if (mMessageNumber > 0) {
            drawMessages(canvas);
        }
    }

    /**
     * 绘制文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        int x = getMeasuredWidth() / 2 - mTextRect.width() / 2;
        int y = mBitmapRect.bottom + mTextRect.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 画图标
     */
    private void drawBitmap(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawBitmap(bitmap, null, mBitmapRect, paint);
    }

    /**
     * 画消息数量
     */
    private void drawMessages(Canvas canvas) {
        //数字画笔内容大小等创建
        Paint textPaint = new Paint();
        Rect textRect = new Rect();
        String text = mMessageNumber > 99 ? "99+" : mMessageNumber + "";
        int textSize;
        if (text.length() == 1) {
            textSize = DensityUtil.dip2px(getContext(), 12);
        } else if (text.length() == 2) {
            textSize = DensityUtil.dip2px(getContext(), 10);
        } else {
            textSize = DensityUtil.dip2px(getContext(), 9);
        }

        textPaint.setColor(0xDDFFFFFF);
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

        //画圆
        int width = DensityUtil.dip2px(getContext(), 8);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFFFF0000);

        RectF messageRectF = new RectF(mBitmapRect.right - width,
                mBitmapRect.top,
                mBitmapRect.right + width,
                mBitmapRect.top + width * 2);
        canvas.drawOval(messageRectF, paint);

        //画数字
        float x = messageRectF.right - messageRectF.width() / 2f;
        float y = messageRectF.bottom - messageRectF.height() / 2f - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;
        canvas.drawText(text, x, y, textPaint);
    }

    /**
     * 消息数量变化并刷新
     *
     * @param number
     */
    public void addMessageNumber(int number) {
        mMessageNumber += number;
        invalidateView();
    }

    /**
     * 接收透明度变化并刷新
     */
    public void setSelected(boolean selected) {
        if (selected) {
            mTextPaint.setColor(mClickColor);
            mDrawBitmap = mClickBitmap;

            startScaleAnim(0f, 0.2f);
        } else {
            mTextPaint.setColor(mColor);
            mDrawBitmap = mBitmap;

            startScaleAnim(0.2f, 0f);
        }
        invalidateView();
    }

    private void startScaleAnim(float startValue, float endValue) {
        if (getWidth() > 0) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(startValue, endValue);
            valueAnimator.setDuration(200);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    if (mBitmapRect != null) {
                        int scaleValue = (int) (mIconWidth * value / 2);
                        mBitmapRect.set(mMarginLeft - scaleValue,
                                mMarginTop - scaleValue,
                                mMarginLeft + mIconWidth + scaleValue,
                                mMarginTop + mIconWidth + scaleValue);
                        invalidateView();
                    }
                }
            });
            valueAnimator.start();
        }
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
