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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.utils.common.DensityUtil;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * 文 件 名: TabButton
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class TabButton extends RelativeLayout {

    private Bitmap mDrawBitmap;
    //初始显示的图标
    private Bitmap mBitmap;
    //选中之后显示的图标
    private Bitmap mClickBitmap;
    //未选中的颜色
    private int mColor = 0xFFAAAAAA;
    //选中之后的颜色
    private int mClickColor = 0xFF30B5FF;
    //显示的文本
    private String mText = "";
    //记录消息数量
    private int mMessageNumber;
    private ImageView ivIcon;
    private TextView tvDesc;
    private Paint textPaint;
    private Rect textRect;
    private Paint ovalPaint;
    private RectF ovalRectF;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setBackgroundColor(0xFFFFFFFF);
        View view = LayoutInflater.from(context).inflate(R.layout.lib_tab_button, this);
        ivIcon = view.findViewById(R.id.iv_tab_button);
        tvDesc = view.findViewById(R.id.tv_tab_button);

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
            }
        }
        a.recycle();

        textPaint = new Paint();
        textRect = new Rect();
        ovalPaint = new Paint();
        ovalRectF = new RectF();

        ivIcon.setImageBitmap(mBitmap);
        tvDesc.setTextColor(mColor);
        tvDesc.setText(mText);
        mDrawBitmap = mBitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("AAA", "onSizeChanged ...");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("AAA", "onDraw ...");
        if (mMessageNumber > 0) {
            drawMessages(canvas);
        }
    }

    /**
     * 画消息数量
     */
    private void drawMessages(Canvas canvas) {
        Log.e("AAA", "drawMessages ...");
        //数字画笔内容大小等创建
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

        ovalPaint.setAntiAlias(true);
        ovalPaint.setColor(0xFFFF0000);

        //画圆
        int width = DensityUtil.dip2px(getContext(), 8);
        float scale = getChildAt(0).getScaleX() - 1f;
        ovalRectF.left = ivIcon.getRight() - width + width * scale;
        ovalRectF.top = ivIcon.getTop() - width * scale;
        ovalRectF.right = ivIcon.getRight() + width + width * scale;
        ovalRectF.bottom = ivIcon.getTop() + width * 2 - width * scale;
        canvas.drawOval(ovalRectF, ovalPaint);
        //画数字
        float x = ovalRectF.right - ovalRectF.width() / 2f;
        float y = ovalRectF.bottom - ovalRectF.height() / 2f - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;
        canvas.drawText(text, x, y, textPaint);
    }

    /**
     * 消息数量变化并刷新
     *
     * @param number
     */
    public void addMessageNumber(int number) {
        Log.e("AAA", "addMessageNumber: " + number);
        mMessageNumber += number;
        invalidateView();
    }

    public void setSelected(boolean selected) {
        Log.e("AAA", "setSelected: " + selected);
        if (selected) {
            mDrawBitmap = mClickBitmap;
            ivIcon.setImageBitmap(mDrawBitmap);
            tvDesc.setTextColor(mClickColor);
            startScaleAnim(1.0f, 1.15f);
        } else {
            mDrawBitmap = mBitmap;
            ivIcon.setImageBitmap(mDrawBitmap);
            tvDesc.setTextColor(mColor);
            startScaleAnim(1.15f, 1.0f);
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
                    ViewHelper.setScaleX(getChildAt(0), value);
                    ViewHelper.setScaleY(getChildAt(0), value);
                    invalidateView();
                }
            });
            valueAnimator.start();
        } else {
            ViewHelper.setScaleX(getChildAt(0), endValue);
            ViewHelper.setScaleY(getChildAt(0), endValue);
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
