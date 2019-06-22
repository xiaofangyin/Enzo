package com.enzo.commonlib.widget.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enzo.commonlib.utils.common.DensityUtil;

/**
 * 文 件 名: TabButton
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class TabView extends LinearLayout {

    private TextView textView;
    private ImageView imageView;
    private int mNormalBitmap;
    private int mSelectedBitmap;

    private int mTextColorNormal;
    private int mTextColorSelected;

    private int mMessageNumber;
    private Paint mMessagePaint;
    private Rect mMessageRect;
    private RectF mMessageRectF;

    private Paint mRedPointPaint;
    private RectF mRedPointRectF;
    private boolean mShowRedPoint;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(LinearLayout.VERTICAL);
//        setBackground(getResources().getDrawable(R.drawable.lib_selector_tabview_bg));

        //数字画笔内容大小等创建
        mMessagePaint = new Paint();
        mMessageRect = new Rect();
        mMessageRectF = new RectF();

        mRedPointPaint = new Paint();
        mRedPointRectF = new RectF();
    }

    public void initTab(TabEntity entity) {
        mTextColorNormal = entity.getNormalColor();
        mTextColorSelected = entity.getSelectedColor();
        mNormalBitmap = entity.getNormalImage();
        mSelectedBitmap = entity.getSelectedImage();

        imageView = new ImageView(getContext());
        imageView.setImageResource(mNormalBitmap);

        textView = new TextView(getContext());
        textView.setText(entity.getTitle());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setTextColor(mTextColorNormal);

        LayoutParams ivLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ivLp.gravity = Gravity.CENTER_HORIZONTAL;

        LayoutParams tvLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tvLp.gravity = Gravity.CENTER_HORIZONTAL;
        tvLp.topMargin = DensityUtil.dip2px(2);

        addView(imageView, ivLp);
        addView(textView, tvLp);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mMessageNumber > 0) {
            drawMessages(canvas);
        } else if (mShowRedPoint) {
            drawRedPoint(canvas);
        }
    }

    /**
     * 画小红点
     */
    private void drawRedPoint(Canvas canvas) {
        mRedPointPaint.setStyle(Paint.Style.FILL);
        mRedPointPaint.setColor(0xFFFF0000);
        mRedPointPaint.setAntiAlias(true);
        mRedPointPaint.setDither(true);

        int radius = DensityUtil.dip2px(3);
        mRedPointRectF.left = getWidth() / 2f + imageView.getWidth() / 2f - radius;
        mRedPointRectF.top = getHeight() / 2f - (imageView.getHeight() + textView.getHeight()) / 2f;
        mRedPointRectF.right = getWidth() / 2 + imageView.getWidth() / 2 + radius;
        mRedPointRectF.bottom = getHeight() / 2f - (imageView.getHeight() + textView.getHeight()) / 2f + radius * 2;
        canvas.drawOval(mRedPointRectF, mRedPointPaint);
    }

    /**
     * 画消息数量
     */
    private void drawMessages(Canvas canvas) {
        String text = mMessageNumber > 99 ? "99+" : mMessageNumber + "";
        int textSize;
        if (text.length() == 1) {
            textSize = DensityUtil.sp2px(12);
        } else if (text.length() == 2) {
            textSize = DensityUtil.sp2px(10);
        } else {
            textSize = DensityUtil.sp2px(8);
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
        int radius = DensityUtil.dip2px(8);
        mMessageRectF.left = getWidth() / 2f + imageView.getWidth() / 2f - radius;
        mMessageRectF.top = getHeight() / 2f - (imageView.getHeight() + textView.getHeight()) / 2f;
        mMessageRectF.right = getWidth() / 2 + imageView.getWidth() / 2 + radius;
        mMessageRectF.bottom = getHeight() / 2f - (imageView.getHeight() + textView.getHeight()) / 2f + radius * 2;

        mRedPointPaint.setStyle(Paint.Style.FILL);
        mRedPointPaint.setColor(0xFFFF0000);
        mRedPointPaint.setAntiAlias(true);
        mRedPointPaint.setDither(true);
        canvas.drawOval(mMessageRectF, mRedPointPaint);
        //苗边
        mRedPointPaint.setStyle(Paint.Style.STROKE);
        mRedPointPaint.setStrokeWidth(DensityUtil.dip2px(1f));
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

    public void setMessageNumber(int number) {
        mMessageNumber = number;
        invalidateView();
    }

    public void resetMessageNumber() {
        mMessageNumber = 0;
        invalidateView();
    }

    public int getMessageNumber() {
        return mMessageNumber;
    }


    /**
     * 小红点
     */
    public void showRedPoint(boolean show) {
        if (mShowRedPoint != show) {
            mShowRedPoint = show;
            invalidateView();
        }
    }

    /**
     * 没有放大
     */
    public void setSelected(boolean selected) {
        textView.setTextColor(selected ? mTextColorSelected : mTextColorNormal);
        imageView.setImageResource(selected ? mSelectedBitmap : mNormalBitmap);
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
}
