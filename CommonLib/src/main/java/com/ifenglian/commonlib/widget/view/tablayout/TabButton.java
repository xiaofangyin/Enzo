package com.ifenglian.commonlib.widget.view.tablayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import com.ifenglian.commonlib.utils.common.DensityUtil;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * 文 件 名: TabButton
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class TabButton extends View {

    private Bitmap mDrawBitmap;
    private Bitmap mNormalBitmap;
    private Bitmap mSelectedBitmap;
    private int mNormalColor = 0xFFAAAAAA;
    private int mSelectedColor = 0xFF30B5FF;
    private float mTextSize;
    private String mText = "";
    private Rect mTextRect;
    private Paint mTextPaint;
    private int mMessageNumber;

    private Paint mMessagePaint;
    private Rect mMessageRect;
    private RectF mMessageRectF;

    private Paint mRedPointPaint;
    private RectF mRedPointRectF;
    private boolean mShowRedPoint;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextRect = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setColor(mNormalColor);
        //数字画笔内容大小等创建
        mMessagePaint = new Paint();
        mMessageRect = new Rect();
        mMessageRectF = new RectF();

        mRedPointPaint = new Paint();
        mRedPointRectF = new RectF();

    }

    public void initIcon(int normalIcon, int selectedIcon) {
        mNormalBitmap = BitmapFactory.decodeResource(getResources(), normalIcon);
        mSelectedBitmap = BitmapFactory.decodeResource(getResources(), selectedIcon);
        mDrawBitmap = mNormalBitmap;
    }

    public void initText(String text, int textSize, int normalColor, int selectedColor) {
        mText = text;
        mTextSize = textSize;
        mNormalColor = normalColor;
        mSelectedColor = selectedColor;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        mTextPaint.setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);//绘制原文本
        drawBitmap(canvas, mDrawBitmap);
        if (mMessageNumber > 0) {
            drawMessages(canvas);
        } else if (mShowRedPoint) {
            drawRedPoint(canvas);
        }
    }

    /**
     * 绘制文本
     */
    private void drawText(Canvas canvas) {
        int marginTop = getHeight() / 2 - (mTextRect.height() + mDrawBitmap.getHeight()) / 2;
        int x = getMeasuredWidth() / 2 - mTextRect.width() / 2;
        int y = marginTop + mDrawBitmap.getHeight() + mTextRect.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 画图标
     */
    private void drawBitmap(Canvas canvas, Bitmap bitmap) {
        int marginLeft = getWidth() / 2 - bitmap.getWidth() / 2;
        int marginTop = getHeight() / 2 - (mTextRect.height() + mDrawBitmap.getHeight()) / 2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawBitmap(bitmap, marginLeft, marginTop, paint);
    }

    /**
     * 画小红点
     */
    private void drawRedPoint(Canvas canvas) {
        mRedPointPaint.setStyle(Paint.Style.FILL);
        mRedPointPaint.setColor(0xFFFF0000);
        mRedPointPaint.setAntiAlias(true);
        mRedPointPaint.setDither(true);

        int radius = DensityUtil.dip2px(getContext(), 3);
        mRedPointRectF.left = getWidth() / 2 + mDrawBitmap.getWidth() / 2 - radius;
        mRedPointRectF.top = getHeight() / 2 - (mDrawBitmap.getHeight() + mTextRect.height()) / 2;
        mRedPointRectF.right = getWidth() / 2 + mDrawBitmap.getWidth() / 2 + radius;
        mRedPointRectF.bottom = getHeight() / 2 - (mDrawBitmap.getHeight() + mTextRect.height()) / 2 + radius * 2;
        canvas.drawOval(mRedPointRectF, mRedPointPaint);
    }

    /**
     * 画消息数量
     */
    private void drawMessages(Canvas canvas) {
        String text = mMessageNumber > 99 ? "99+" : mMessageNumber + "";
        int textSize;
        if (text.length() == 1) {
            textSize = DensityUtil.sp2px(getContext(), 12);
        } else if (text.length() == 2) {
            textSize = DensityUtil.sp2px(getContext(), 10);
        } else {
            textSize = DensityUtil.sp2px(getContext(), 8);
        }

        mMessagePaint.setColor(0xDDFFFFFF);
        mMessagePaint.setFakeBoldText(true);
        mMessagePaint.setAntiAlias(true);
        mMessagePaint.setTextSize(textSize);
        mMessagePaint.setTypeface(Typeface.MONOSPACE);
        mMessagePaint.getTextBounds(text, 0, text.length(), mMessageRect);
        mMessagePaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mMessagePaint.getFontMetrics();

        //画圆
        int radius = DensityUtil.dip2px(getContext(), 8);
        mMessageRectF.left = getWidth() / 2 + mDrawBitmap.getWidth() / 2 - radius;
        mMessageRectF.top = getHeight() / 2 - (mDrawBitmap.getHeight() + mTextRect.height()) / 2;
        mMessageRectF.right = getWidth() / 2 + mDrawBitmap.getWidth() / 2 + radius;
        mMessageRectF.bottom = getHeight() / 2 - (mDrawBitmap.getHeight() + mTextRect.height()) / 2 + radius * 2;

        mRedPointPaint.setStyle(Paint.Style.FILL);
        mRedPointPaint.setColor(0xFFFF0000);
        mRedPointPaint.setAntiAlias(true);
        mRedPointPaint.setDither(true);
        canvas.drawOval(mMessageRectF, mRedPointPaint);
        //苗边
        mRedPointPaint.setStyle(Paint.Style.STROKE);
        mRedPointPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 1f));
        mRedPointPaint.setColor(0xFFFFFFFF);
        canvas.drawOval(mMessageRectF, mRedPointPaint);

        //画数字
        float x = mMessageRectF.right - mMessageRectF.width() / 2f;
        float y = mMessageRectF.bottom - mMessageRectF.height() / 2f - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;
        canvas.drawText(text, x, y, mMessagePaint);
    }

    /**
     * 消息数量变化并刷新
     */
    public void addMessageNumber(int number) {
        mMessageNumber += number;
        invalidateView();
    }

    /**
     * 小红点
     */
    public void showRedPoint(boolean showRedPoint) {
        mShowRedPoint = showRedPoint;
        invalidateView();
    }

    public void setSelected(boolean selected, boolean animate) {
        mTextPaint.setColor(selected ? mSelectedColor : mNormalColor);
        if (animate) {
            if (selected) {
                startScaleAnim(1.0f, 1.15f, mSelectedBitmap);
            } else {
                startScaleAnim(1.15f, 1f, mNormalBitmap);
            }
        } else {
            if (selected) {
                Matrix matrix = new Matrix();
                matrix.postScale(1.15f, 1.15f);
                mDrawBitmap = Bitmap.createBitmap(mSelectedBitmap, 0, 0, mSelectedBitmap.getWidth(), mSelectedBitmap.getHeight(),
                        matrix, true);
            } else {
                mDrawBitmap = mNormalBitmap;
            }
        }
        invalidateView();
    }

    private void startScaleAnim(float startValue, float endValue, final Bitmap bitmap) {
        if (getWidth() > 0) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(startValue, endValue);
            valueAnimator.setDuration(200);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    Matrix matrix = new Matrix();
                    matrix.postScale(value, value);
                    mDrawBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    invalidateView();
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
