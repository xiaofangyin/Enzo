package com.enzo.commonlib.widget.checkbox;

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

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.nineoldandroids.animation.TypeEvaluator;

/**
 * 文 件 名: SmoothCheckBox
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/27
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SmoothCheckBox extends View implements Checkable, View.OnClickListener {
    private static final String KEY_INSTANCE_STATE = "InstanceState";

    private static final int COLOR_TICK = Color.WHITE;
    private static final int COLOR_UNCHECKED = Color.parseColor("#80DFDFDF");
    private static final int COLOR_CHECKED = Color.parseColor("#FF2EAA3C");
    private static final int COLOR_FLOOR_UNCHECKED = Color.parseColor("#80DFDFDF");

    private static final int DEF_DRAW_SIZE = 25;
    private static final int DEF_ANIM_DURATION = 150;

    private Paint mPaint, mTickPaint, mFloorPaint;
    private Point[] mTickPoints;
    private Point mCenterPoint;
    private Path mTickPath;

    private float mLeftLineDistance, mRightLineDistance, mDrawDistance;
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
        int tickWidth = ta.getDimensionPixelSize(R.styleable.SmoothCheckBox_tick_width, DensityUtil.dip2px(1.2f));
        mFloorColor = ta.getColor(R.styleable.SmoothCheckBox_color_unchecked_stroke, COLOR_FLOOR_UNCHECKED);
        mCheckedColor = ta.getColor(R.styleable.SmoothCheckBox_color_checked, COLOR_CHECKED);
        mUnCheckedColor = ta.getColor(R.styleable.SmoothCheckBox_color_unchecked, COLOR_UNCHECKED);
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.SmoothCheckBox_stroke_width, DensityUtil.dip2px(1));
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
        mDrawDistance = 0;
        if (isChecked()) {
            startCheckedAnimation();
        } else {
            startUnCheckedAnimation();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterPoint.x = w / 2;
        mCenterPoint.y = h / 2;
        mTickPoints[0].x = Math.round((float) w / 30 * 9);
        mTickPoints[0].y = Math.round((float) h / 30 * 15f);
        mTickPoints[1].x = Math.round((float) w / 30 * 14);
        mTickPoints[1].y = Math.round((float) h / 30 * 19f);
        mTickPoints[2].x = Math.round((float) w / 30 * 20.8f);
        mTickPoints[2].y = Math.round((float) h / 30 * 11.5f);

        mLeftLineDistance = (float) Math.sqrt(Math.pow(mTickPoints[1].x - mTickPoints[0].x, 2) +
                Math.pow(mTickPoints[1].y - mTickPoints[0].y, 2));
        mRightLineDistance = (float) Math.sqrt(Math.pow(mTickPoints[2].x - mTickPoints[1].x, 2) +
                Math.pow(mTickPoints[2].y - mTickPoints[1].y, 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawFloor(canvas);
        drawCenter(canvas);
        drawTick(canvas);
    }

    private void drawFloor(Canvas canvas) {
        mFloorPaint.setColor(mFloorColor);
        int radius = mCenterPoint.x - DensityUtil.dip2px(5);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, radius * mFloorScale, mFloorPaint);
    }

    private void drawCenter(Canvas canvas) {
        mPaint.setColor(mUnCheckedColor);
        float radius = (mCenterPoint.x - mStrokeWidth) * mScaleVal - DensityUtil.dip2px(5);
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
        if (mDrawDistance < mLeftLineDistance) {
            mDrawDistance += step;
            float stopX = mTickPoints[0].x + (mTickPoints[1].x - mTickPoints[0].x) * mDrawDistance / mLeftLineDistance;
            float stopY = mTickPoints[0].y + (mTickPoints[1].y - mTickPoints[0].y) * mDrawDistance / mLeftLineDistance;

            mTickPath.moveTo(mTickPoints[0].x, mTickPoints[0].y);
            mTickPath.lineTo(stopX, stopY);
            canvas.drawPath(mTickPath, mTickPaint);

            if (mDrawDistance > mLeftLineDistance) {
                mDrawDistance = mLeftLineDistance;
            }
        } else {
            mTickPath.moveTo(mTickPoints[0].x, mTickPoints[0].y);
            mTickPath.lineTo(mTickPoints[1].x, mTickPoints[1].y);
            canvas.drawPath(mTickPath, mTickPaint);
            if (mDrawDistance < mLeftLineDistance + mRightLineDistance) {
                float stopX = mTickPoints[1].x + (mTickPoints[2].x - mTickPoints[1].x) * (mDrawDistance - mLeftLineDistance) / mRightLineDistance;
                float stopY = mTickPoints[1].y - (mTickPoints[1].y - mTickPoints[2].y) * (mDrawDistance - mLeftLineDistance) / mRightLineDistance;

                mTickPath.moveTo(mTickPoints[1].x, mTickPoints[1].y);
                mTickPath.lineTo(stopX, stopY);
                canvas.drawPath(mTickPath, mTickPaint);

                mDrawDistance += step;
            } else {
                mTickPath.moveTo(mTickPoints[1].x, mTickPoints[1].y);
                mTickPath.lineTo(mTickPoints[2].x, mTickPoints[2].y);
                canvas.drawPath(mTickPath, mTickPaint);
            }
        }

        if (mDrawDistance < mLeftLineDistance + mRightLineDistance) {
            postInvalidate();
        }
    }

    private void startCheckedAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0f);
        animator.setDuration(DEF_ANIM_DURATION);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScaleVal = (float) animation.getAnimatedValue();
                mFloorColor = te.evaluate(1 - mScaleVal, mUnCheckedColor, mCheckedColor);
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
                mFloorColor = te.evaluate(mScaleVal, mCheckedColor, mFloorUnCheckedColor);
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
            mDrawDistance = 0f;
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
        mDrawDistance = isChecked() ? (mLeftLineDistance + mRightLineDistance) : 0;
    }

    private int measureSize(int measureSpec) {
        int defSize = DensityUtil.dip2px(DEF_DRAW_SIZE);
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

    TypeEvaluator<Integer> te = new TypeEvaluator<Integer>() {
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int alpha = (int) (Color.alpha(startValue) + fraction * (Color.alpha(endValue) - Color.alpha(startValue)));
            int red = (int) (Color.red(startValue) + fraction * (Color.red(endValue) - Color.red(startValue)));
            int green = (int) (Color.green(startValue) + fraction * (Color.green(endValue) - Color.green(startValue)));
            int blue = (int) (Color.blue(startValue) + fraction * (Color.blue(endValue) - Color.blue(startValue)));
            return Color.argb(alpha, red, green, blue);
        }
    };

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        this.mListener = l;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked);
    }
}