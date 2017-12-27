package com.ifenglian.commonlib.widget.view.checkbox;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Checkable;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.utils.common.DensityUtil;

/**
 * 文 件 名: SmoothCheckBox
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/27
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SmoothCheckBox extends View implements Checkable, View.OnClickListener {
    private static final String KEY_INSTANCE_STATE = "InstanceState";

    private static final int COLOR_TICK = Color.WHITE;
    private static final int COLOR_UNCHECKED = Color.WHITE;
    private static final int COLOR_CHECKED = Color.parseColor("#FB4846");
    private static final int COLOR_FLOOR_UNCHECKED = Color.parseColor("#DFDFDF");

    private static final int DEF_DRAW_SIZE = 25;
    private static final int DEF_ANIM_DURATION = 150;

    private Paint mPaint, mTickPaint, mFloorPaint;
    private Point[] mTickPoints;
    private Point mCenterPoint;
    private Path mTickPath;

    private float mLeftLineDistance, mRightLineDistance, mDrewDistance;
    private float mScaleVal = 1.0f, mFloorScale = 1.0f;
    private int mStrokeWidth;
    private int mCheckedColor, mUnCheckedColor, mFloorColor, mFloorUnCheckedColor;

    private boolean mChecked;
    private boolean mTickDrawing;
    private OnCheckedChangeListener mListener;

    public SmoothCheckBox(Context context) {
        this(context, null);
    }

    public SmoothCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmoothCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SmoothCheckBox);
        int tickColor = ta.getColor(R.styleable.SmoothCheckBox_color_tick, COLOR_TICK);
        int tickWidth = ta.getDimensionPixelSize(R.styleable.SmoothCheckBox_tick_width, DensityUtil.dip2px(getContext(), 1.8f));
        mFloorColor = ta.getColor(R.styleable.SmoothCheckBox_color_unchecked_stroke, COLOR_FLOOR_UNCHECKED);
        mCheckedColor = ta.getColor(R.styleable.SmoothCheckBox_color_checked, COLOR_CHECKED);
        mUnCheckedColor = ta.getColor(R.styleable.SmoothCheckBox_color_unchecked, COLOR_UNCHECKED);
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.SmoothCheckBox_stroke_width, DensityUtil.dip2px(getContext(), 1));
        ta.recycle();

        mFloorUnCheckedColor = mFloorColor;
        mTickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTickPaint.setStyle(Paint.Style.STROKE);
        mTickPaint.setStrokeCap(Paint.Cap.ROUND);
        mTickPaint.setColor(tickColor);
        mTickPaint.setStrokeWidth(tickWidth);

        mFloorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFloorPaint.setStyle(Paint.Style.FILL);
        mFloorPaint.setColor(mFloorColor);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mCheckedColor);

        mTickPath = new Path();
        mCenterPoint = new Point();
        mTickPoints = new Point[3];
        mTickPoints[0] = new Point();
        mTickPoints[1] = new Point();
        mTickPoints[2] = new Point();

        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        toggle();
        mTickDrawing = false;
        mDrewDistance = 0;
        if (isChecked()) {
            startCheckedAnimation();
        } else {
            startUnCheckedAnimation();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterPoint.x = w / 2;
        mCenterPoint.y = h / 2;

        mTickPoints[0].x = Math.round((float) w / 30 * 8);
        mTickPoints[0].y = Math.round((float) h / 30 * 14);
        mTickPoints[1].x = Math.round((float) w / 30 * 13);
        mTickPoints[1].y = Math.round((float) h / 30 * 20);
        mTickPoints[2].x = Math.round((float) w / 30 * 22);
        mTickPoints[2].y = Math.round((float) h / 30 * 12);

        mLeftLineDistance = (float) Math.sqrt(Math.pow(mTickPoints[1].x - mTickPoints[0].x, 2) +
                Math.pow(mTickPoints[1].y - mTickPoints[0].y, 2));
        mRightLineDistance = (float) Math.sqrt(Math.pow(mTickPoints[2].x - mTickPoints[1].x, 2) +
                Math.pow(mTickPoints[2].y - mTickPoints[1].y, 2));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawCenter(canvas);
        drawTick(canvas);
    }

    private void drawBorder(Canvas canvas) {
        mFloorPaint.setColor(mFloorColor);
        int radius = mCenterPoint.x;
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, radius * mFloorScale, mFloorPaint);
    }

    private void drawCenter(Canvas canvas) {
        mPaint.setColor(mUnCheckedColor);
        float radius = (mCenterPoint.x - mStrokeWidth) * mScaleVal;
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, radius, mPaint);
    }

    private void drawTick(Canvas canvas) {
        if (mTickDrawing && isChecked()) {
            drawTickPath(canvas);
        }
    }

    private void drawTickPath(Canvas canvas) {
        mTickPath.reset();
        float step = 4;
        if (mDrewDistance < mLeftLineDistance) {
            mDrewDistance += step;
            float stopX = mTickPoints[0].x + (mTickPoints[1].x - mTickPoints[0].x) * mDrewDistance / mLeftLineDistance;
            float stopY = mTickPoints[0].y + (mTickPoints[1].y - mTickPoints[0].y) * mDrewDistance / mLeftLineDistance;

            mTickPath.moveTo(mTickPoints[0].x, mTickPoints[0].y);
            mTickPath.lineTo(stopX, stopY);
            canvas.drawPath(mTickPath, mTickPaint);

            if (mDrewDistance > mLeftLineDistance) {
                mDrewDistance = mLeftLineDistance;
            }
        } else {
            mTickPath.moveTo(mTickPoints[0].x, mTickPoints[0].y);
            mTickPath.lineTo(mTickPoints[1].x, mTickPoints[1].y);
            canvas.drawPath(mTickPath, mTickPaint);
            if (mDrewDistance < mLeftLineDistance + mRightLineDistance) {
                float stopX = mTickPoints[1].x + (mTickPoints[2].x - mTickPoints[1].x) * (mDrewDistance - mLeftLineDistance) / mRightLineDistance;
                float stopY = mTickPoints[1].y - (mTickPoints[1].y - mTickPoints[2].y) * (mDrewDistance - mLeftLineDistance) / mRightLineDistance;

                mTickPath.moveTo(mTickPoints[1].x, mTickPoints[1].y);
                mTickPath.lineTo(stopX, stopY);
                canvas.drawPath(mTickPath, mTickPaint);

                mDrewDistance += step;
            } else {
                mTickPath.moveTo(mTickPoints[1].x, mTickPoints[1].y);
                mTickPath.lineTo(mTickPoints[2].x, mTickPoints[2].y);
                canvas.drawPath(mTickPath, mTickPaint);
            }
        }

        if (mDrewDistance < mLeftLineDistance + mRightLineDistance) {
            postInvalidate();
        }
    }

    private void startCheckedAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0f);
        animator.setDuration(DEF_ANIM_DURATION / 2);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScaleVal = (float) animation.getAnimatedValue();
                mFloorColor = getGradientColor(mUnCheckedColor, mCheckedColor, 1 - mScaleVal);
                postInvalidate();
            }
        });
        animator.start();

        ValueAnimator floorAnimator = ValueAnimator.ofFloat(1.0f, 0.8f, 1.0f);
        floorAnimator.setDuration(DEF_ANIM_DURATION);
        floorAnimator.setInterpolator(new LinearInterpolator());
        floorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFloorScale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        floorAnimator.start();

        drawTickDelayed();
    }

    private void startUnCheckedAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f);
        animator.setDuration(DEF_ANIM_DURATION);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScaleVal = (float) animation.getAnimatedValue();
                mFloorColor = getGradientColor(mCheckedColor, mFloorUnCheckedColor, mScaleVal);
                postInvalidate();
            }
        });
        animator.start();

        ValueAnimator floorAnimator = ValueAnimator.ofFloat(1.0f, 0.8f, 1.0f);
        floorAnimator.setDuration(DEF_ANIM_DURATION);
        floorAnimator.setInterpolator(new LinearInterpolator());
        floorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFloorScale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        floorAnimator.start();
    }

    private void drawTickDelayed() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mTickDrawing = true;
                postInvalidate();
            }
        }, DEF_ANIM_DURATION);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putBoolean(KEY_INSTANCE_STATE, isChecked());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            boolean isChecked = bundle.getBoolean(KEY_INSTANCE_STATE);
            setChecked(isChecked);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        this.setChecked(!isChecked());
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        reset();
        invalidate();
        if (mListener != null) {
            mListener.onCheckedChanged(SmoothCheckBox.this, mChecked);
        }
    }

    public void setChecked(boolean checked, boolean animate) {
        if (animate) {
            mTickDrawing = false;
            mChecked = checked;
            mDrewDistance = 0f;
            if (checked) {
                startCheckedAnimation();
            } else {
                startUnCheckedAnimation();
            }
            if (mListener != null) {
                mListener.onCheckedChanged(SmoothCheckBox.this, mChecked);
            }

        } else {
            this.setChecked(checked);
        }
    }

    private void reset() {
        mTickDrawing = true;
        mFloorScale = 1.0f;
        mScaleVal = isChecked() ? 0f : 1.0f;
        mFloorColor = isChecked() ? mCheckedColor : mFloorUnCheckedColor;
        mDrewDistance = isChecked() ? (mLeftLineDistance + mRightLineDistance) : 0;
    }

    private int measureSize(int measureSpec) {
        int defSize = DensityUtil.dip2px(getContext(), DEF_DRAW_SIZE);
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        int result = 0;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = Math.min(defSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }


    private static int getGradientColor(int startColor, int endColor, float percent) {
        int startA = Color.alpha(startColor);
        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endA = Color.alpha(endColor);
        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        int currentA = (int) (startA * (1 - percent) + endA * percent);
        int currentR = (int) (startR * (1 - percent) + endR * percent);
        int currentG = (int) (startG * (1 - percent) + endG * percent);
        int currentB = (int) (startB * (1 - percent) + endB * percent);
        return Color.argb(currentA, currentR, currentG, currentB);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        this.mListener = l;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked);
    }
}