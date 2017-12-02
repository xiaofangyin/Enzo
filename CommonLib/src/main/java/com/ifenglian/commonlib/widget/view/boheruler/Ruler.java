package com.ifenglian.commonlib.widget.view.boheruler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.OverScroller;

import com.ifenglian.commonlib.R;

/**
 * 文 件 名: Ruler2
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class Ruler extends View {

    private final String TAG = "ruler";
    private Paint mSmallScalePaint, mBigScalePaint, mTextPaint;
    private float mCurrentScale;
    //最大刻度数
    private int mMaxLength;
    //长度、最小可滑动值、最大可滑动值
    private int mLength, mMinPositionX, mMaxPositionX;
    //控制滑动
    private OverScroller mOverScroller;
    //记录落点
    private float mLastX;
    //惯性最大最小速度
    private int mMaximumVelocity, mMinimumVelocity;
    //速度获取
    private VelocityTracker mVelocityTracker;
    //回调接口
    private RulerCallback mRulerCallback;
    //一格大刻度多少格小刻度
    private int mCount = 10;
    //最小最大刻度值(以0.1kg为单位)
    private int mMinScale, mMaxScale;
    //刻度间隔
    private int mInterval;
    //大小刻度的长度
    private int mSmallScaleLength, mBigScaleLength;
    //数字Text距离顶部高度
    private int mTextMarginTop;

    public Ruler(Context context) {
        this(context, null);
        init(context);
    }

    public Ruler(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Ruler(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initPaints();
        mCount = 10;
        mMinScale = 0;
        mMaxScale = 500;
        mCurrentScale = 110;
        mMaxLength = mMaxScale - mMinScale;

        mOverScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();

        //第一次进入，跳转到设定刻度
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                goToScale(mCurrentScale);
            }
        });
    }

    //初始化画笔
    private void initPaints() {
        mSmallScalePaint = new Paint();
        mSmallScalePaint.setAntiAlias(true);
        mSmallScalePaint.setStrokeWidth(dp2px(2f));
        mSmallScalePaint.setColor(getResources().getColor(R.color.colorGray));
        mSmallScalePaint.setStrokeCap(Paint.Cap.ROUND);

        mBigScalePaint = new Paint();
        mBigScalePaint.setAntiAlias(true);
        mBigScalePaint.setStrokeWidth(dp2px(3));
        mBigScalePaint.setColor(getResources().getColor(R.color.colorGray));
        mBigScalePaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(getResources().getColor(R.color.colorLightBlack));
        mTextPaint.setTextSize(sp2px(24));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    //获取控件宽高，设置相应信息
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mInterval = dp2px(10);
        mSmallScaleLength = dp2px(20);
        mBigScaleLength = dp2px(40);
        mTextMarginTop = dp2px(80);

        mLength = (mMaxScale - mMinScale) * mInterval;
        int mHalfWidth = w / 2;
        mMinPositionX = -mHalfWidth;
        mMaxPositionX = mLength - mHalfWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
    }

    private void drawScale(Canvas canvas) {
        for (float i = mMinScale; i <= mMaxScale; i++) {
            float locationX = (i - mMinScale) * mInterval;
            if (locationX > getScrollX() && locationX < (getScrollX() + canvas.getWidth())) {
                if (i % mCount == 0) {
                    canvas.drawLine(locationX, 0, locationX, mBigScaleLength, mBigScalePaint);
                    canvas.drawText(String.valueOf(i / 10), locationX, mTextMarginTop, mTextPaint);
                } else {
                    canvas.drawLine(locationX, 0, locationX, mSmallScaleLength, mSmallScalePaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                mLastX = currentX;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = mLastX - currentX;
                mLastX = currentX;
                scrollBy((int) (moveX), 0);
                break;
            case MotionEvent.ACTION_UP:
                //处理松手后的Fling
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityX = (int) mVelocityTracker.getXVelocity();
                Log.e(TAG, "velocityX: " + velocityX + "...mMaximumVelocity: " + mMaximumVelocity + "...mMinimumVelocity: " + mMinimumVelocity);
                if (Math.abs(velocityX) > mMinimumVelocity) {
                    fling(-velocityX);
                } else {
                    scrollBackToCurrentScale();
                }
                mVelocityTracker.clear();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                break;
        }
        return true;
    }

    private void fling(int vX) {
        mOverScroller.fling(getScrollX(), 0, vX, 0, mMinPositionX, mMaxPositionX, 0, 0);
    }

    private void scrollBackToCurrentScale() {
        Log.e(TAG, "scrollBackToCurrentScale float: " + mCurrentScale + "...int: " + Math.round(mCurrentScale));
        mCurrentScale = Math.round(mCurrentScale);
        mOverScroller.startScroll(getScrollX(), 0, scaleToScrollX(mCurrentScale) - getScrollX(), 0, 1000);
        if (mRulerCallback != null) {
            mRulerCallback.afterScaleChanged(mCurrentScale);
        }
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());

            if (!mOverScroller.computeScrollOffset()) {
                scrollBackToCurrentScale();
            }
        }
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        if (x < mMinPositionX) {
            x = mMinPositionX;
        }
        if (x > mMaxPositionX) {
            x = mMaxPositionX;
        }
        super.scrollTo(x, y);

        mCurrentScale = scrollXtoScale(x);
        if (mRulerCallback != null) {
            mRulerCallback.onScaleChanging(Math.round(mCurrentScale));
        }
    }

    /**
     * 把滑动偏移量scrollX转化为刻度Scale
     */
    private float scrollXtoScale(int scrollX) {
        return ((float) (scrollX - mMinPositionX) / mLength) * mMaxLength + mMinScale;
    }

    /**
     * 把Scale转化为ScrollX
     */
    private int scaleToScrollX(float scale) {
        return (int) ((scale - mMinScale) / mMaxLength * mLength + mMinPositionX);
    }

    //设置尺子当前刻度
    public void setCurrentScale(float currentScale) {
        this.mCurrentScale = currentScale;
        goToScale(mCurrentScale);
    }

    //直接跳转到当前刻度
    public void goToScale(float scale) {
        mCurrentScale = Math.round(scale);
        scrollTo(scaleToScrollX(mCurrentScale), 0);
        if (mRulerCallback != null) {
            mRulerCallback.onScaleChanging(mCurrentScale);
        }
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getContext().getResources().getDisplayMetrics());
    }

    public void setRulerCallback(RulerCallback RulerCallback) {
        this.mRulerCallback = RulerCallback;
    }
}
