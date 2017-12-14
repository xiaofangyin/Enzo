package com.ifenglian.module_d.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.ifenglian.commonlib.utils.common.DensityUtil;
import com.ifenglian.module_d.R;

/**
 * Created by MJJ on 2015/7/29.
 */
public class TabButton extends View {
    //初始显示的图标
    private Bitmap mBitmap;
    //选中之后显示的图标
    private Bitmap mClickBitmap;
    //未选中的颜色
    private int mColor = 0xFFAAAAAA;
    //选中之后的颜色
    private int mClickColor = 0xFF3F9FE0;
    //圆形消息的颜色
    private int mColor_message = 0xFFFF0000;
    //字体大小
    private float mTextSize;
    //显示的文本
    private String mText = "";
    //选中图标的透明度，0f为未选中，1f为选中
    private float mAlpha = 0f;
    //画图位置
    private Rect mBitmapRect;
    private Rect mTextRect;
    //文本的画笔
    private Paint mTextPaint;
    //记录消息数量
    private int mMessageNumber = 0;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TabButton);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TabButton_image) {
                BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                mBitmap = drawable.getBitmap();

            } else if (attr == R.styleable.TabButton_clickimage) {
                BitmapDrawable clickdrawable = (BitmapDrawable) a.getDrawable(attr);
                mClickBitmap = clickdrawable.getBitmap();

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
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        mTextPaint.setAntiAlias(true);//抗锯齿

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int padding = DensityUtil.dip2px(getContext(),4);
        int iconWidth = mBitmap.getWidth();

        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - (mTextRect.height() + iconWidth) / 2;
        mBitmapRect = new Rect(left, top, left + iconWidth, top + iconWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int alpha = (int) Math.ceil(255 * mAlpha);

        drawText(canvas, 255, mColor);//绘制原文本
        drawText(canvas, alpha, mClickColor);//绘制变色后的文本

        drawBitmap(canvas, 255, mColor, mBitmap);
        drawBitmap(canvas, alpha, mClickColor, mClickBitmap);

        if (mMessageNumber > 0) {
            drawMessages(canvas);
        }
    }

    /**
     * 绘制文本
     *
     * @param canvas
     * @param alpha
     * @param color
     */
    private void drawText(Canvas canvas, int alpha, int color) {
        mTextPaint.setColor(color);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - mTextRect.width() / 2;
        int y = mBitmapRect.bottom + mTextRect.height();
        canvas.drawText(mText, x, y, mTextPaint);

    }

    /**
     * 画图标
     *
     * @param canvas
     * @param alpha
     * @param color
     * @param bitmap
     */
    private void drawBitmap(Canvas canvas, int alpha, int color, Bitmap bitmap) {
        Bitmap bitmapTem = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvasTem = new Canvas(bitmapTem);
        Paint paint = new Paint();
        canvasTem.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        paint.setColor(color);
        paint.setAlpha(alpha);
        canvasTem.drawRect(mBitmapRect, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvasTem.drawBitmap(bitmap, null, mBitmapRect, paint);
        canvas.drawBitmap(bitmapTem, 0, 0, null);
    }


    /**
     * 画消息数量
     *
     * @param canvas
     */
    private void drawMessages(Canvas canvas) {
        //数字画笔内容大小等创建
        Paint textPaint = new Paint();
        Rect textRect = new Rect();
        String text = mMessageNumber > 99 ? "99+" : mMessageNumber + "";
        int textSize = 0;
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
        int width = DensityUtil.dip2px(getContext(), 18);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mColor_message);

        RectF messageRectF = new RectF(mBitmapRect.right - width, mBitmapRect.top, mBitmapRect.right, mBitmapRect.top + width);
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
     *
     * @param alpha
     */
    public void setAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
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


    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alpha";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }


}
