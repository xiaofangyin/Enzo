package com.ifenglian.commonlib.widget.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ifenglian.commonlib.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * 文 件 名: SGLSeekBar
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/8/17
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SGLSeekBar extends View {

    private Paint paint;
    private RectF line;
    private Thumb thumb;

    private int colorLineSelected, colorLineUnSelected, colorEdge;
    private int lineTop, lineBottom, lineLeft, lineRight;
    private int lineWidth, lineHeight;
    private int lineCorners;
    private int seekBarRadius;
    private int cellsCount = 1;
    private float cellsPercent;

    public SGLSeekBar(Context context) {
        this(context, null);
    }

    public SGLSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SGLSeekBar);
        try {
            colorLineSelected = a.getColor(R.styleable.SGLSeekBar_lineColorSelected, 0xFF97DAFF);
            colorLineUnSelected = a.getColor(R.styleable.SGLSeekBar_lineColorUnSelected, 0xFFD9D9D9);
            colorEdge = a.getColor(R.styleable.SGLSeekBar_circleColorEdge, 0xFF30B5FF);
            lineHeight = (int) a.getDimension(R.styleable.SGLSeekBar_lineHeight, 5);
            int cells = a.getInt(R.styleable.SGLSeekBar_cells, 1);
            setRules(cells);
        } finally {
            a.recycle();
        }
    }

    /**
     * 将SeekBar分成几份
     *
     * @param cells 需要分成的数量
     */
    public void setRules(int cells) {
        if (cells < 1) {
            cells = 1;
        }
        cellsCount = cells;
        cellsPercent = 1f / cellsCount;
        invalidate();
    }

    /**
     * 设置进度
     *
     * @param progress 进度值
     */
    public void setProgress(int progress) {
        if (progress < 0) {
            thumb.currPercent = 0;
        } else if (progress > 100) {
            thumb.currPercent = 1;
        } else {
            thumb.currPercent = progress / 100f;
        }
        invalidate();
    }

    /**
     * 获取进度
     *
     * @return 返回进度值
     */
    public int getProgress() {
        return Math.round(thumb.currPercent * 100);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        seekBarRadius = h / 2;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        lineLeft = seekBarRadius;
        lineRight = w - seekBarRadius;
        lineTop = seekBarRadius - lineHeight / 2;
        lineBottom = seekBarRadius + lineHeight / 2;
        lineWidth = lineRight - lineLeft;

        line = new RectF();
        line.set(lineLeft, lineTop, lineRight, lineBottom);
        lineCorners = (int) ((lineBottom - lineTop) * 0.5f);

        thumb = new Thumb();
        thumb.onSizeChanged(seekBarRadius, seekBarRadius, h, lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        line.set(lineLeft, lineTop, lineRight, lineBottom);
        paint.setColor(colorLineUnSelected);
        canvas.drawRoundRect(line, lineCorners, lineCorners, paint);

        paint.setColor(colorLineSelected);
        line.set(lineLeft, lineTop, thumb.left + thumb.widthSize / 2 + thumb.lineWidth * thumb.currPercent, lineBottom);
        canvas.drawRoundRect(line, lineCorners, lineCorners, paint);

        if (cellsCount > 1) {
            paint.setColor(colorEdge);
            for (int i = 0; i <= cellsCount; i++) {
                canvas.drawCircle(lineLeft + i * cellsPercent * lineWidth, seekBarRadius, lineHeight / 2, paint);
            }
        }
        thumb.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                boolean touchResult = false;
                if (thumb.onTouchEvent(event)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    touchResult = true;
                    thumb.material = 1;
                    invalidate();
                    if (callback != null) {
                        callback.onStartTrackingTouch(SGLSeekBar.this, Math.round(thumb.currPercent * 100));
                    }
                }
                return touchResult;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float percent = (x - lineLeft) * 1f / (lineWidth);
                if (cellsCount > 1) {
                    int cellsValue = Math.round(percent / cellsPercent);
                    percent = cellsValue * cellsPercent;
                }
                if (percent < 0) {
                    percent = 0;
                } else if (percent > 1) {
                    percent = 1;
                }
                int progress = Math.round(percent * 100);
                int thumbProgress = Math.round(thumb.currPercent * 100);
                if (progress != thumbProgress) {
                    thumb.currPercent = percent;
                    invalidate();

                    if (callback != null) {
                        callback.onProgressChanged(SGLSeekBar.this, progress);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                thumb.materialRestore();
                if (callback != null) {
                    callback.onStopTrackingTouch(SGLSeekBar.this, Math.round(thumb.currPercent * 100));
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private class Thumb {
        Paint defaultPaint;
        RadialGradient shadowGradient;
        ValueAnimator valueAnimator;
        int lineWidth;
        int widthSize, heightSize;
        int left, right, top, bottom;
        float currPercent;
        float material;

        final TypeEvaluator<Integer> te = new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                int alpha = (int) (Color.alpha(startValue) + fraction * (Color.alpha(endValue) - Color.alpha(startValue)));
                int red = (int) (Color.red(startValue) + fraction * (Color.red(endValue) - Color.red(startValue)));
                int green = (int) (Color.green(startValue) + fraction * (Color.green(endValue) - Color.green(startValue)));
                int blue = (int) (Color.blue(startValue) + fraction * (Color.blue(endValue) - Color.blue(startValue)));
                return Color.argb(alpha, red, green, blue);
            }
        };

        void onSizeChanged(int centerX, int centerY, int hSize, int parentLineWidth) {
            heightSize = hSize;
            widthSize = (int) (heightSize * 0.8f);
            left = centerX - widthSize / 2;
            right = centerX + widthSize / 2;
            top = centerY - heightSize / 2;
            bottom = centerY + heightSize / 2;
            lineWidth = parentLineWidth;

            defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            defaultPaint.setAntiAlias(true);
            defaultPaint.setDither(true);
            int radius = (int) (widthSize * 0.5f);
            int mShadowCenterX = widthSize / 2;
            int mShadowCenterY = heightSize / 2;
            shadowGradient = new RadialGradient(mShadowCenterX, mShadowCenterY, radius, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        }

        boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            if (cellsCount > 1) {
                for (int i = 0; i <= cellsCount; i++) {
                    if (x > left + i * cellsPercent * lineWidth && x < right + i * cellsPercent * lineWidth && y > top && y < bottom) {
                        return true;
                    }
                }
            } else {
                int offset = (int) (lineWidth * currPercent);
                return x > left + offset && x < right + offset && y > top && y < bottom;
            }
            return false;
        }

        void draw(Canvas canvas) {
            int offset = (int) (lineWidth * currPercent);
            canvas.save();
            canvas.translate(offset + left, 0);
            drawThumb(canvas);
            canvas.restore();
        }

        private void drawThumb(Canvas canvas) {
            int centerX = widthSize / 2;
            int centerY = heightSize / 2;
            int radius = widthSize / 2;
            // 绘制阴影
            defaultPaint.setStyle(Paint.Style.FILL);
            canvas.save();
            canvas.translate(0, radius * 0.25f);
            defaultPaint.setShader(shadowGradient);
            canvas.drawCircle(centerX, centerY, radius, defaultPaint);
            defaultPaint.setShader(null);
            canvas.restore();
            // 绘制实心圆
            defaultPaint.setStyle(Paint.Style.FILL);
            defaultPaint.setColor(te.evaluate(material, 0xFFFFFFFF, 0xFFE7E7E7));
            canvas.drawCircle(centerX, centerY, radius, defaultPaint);
            // 绘制边框
            defaultPaint.setStyle(Paint.Style.STROKE);
            defaultPaint.setColor(0xFFD7D7D7);
            canvas.drawCircle(centerX, centerY, radius, defaultPaint);
        }

        private void materialRestore() {
            if (valueAnimator != null) valueAnimator.cancel();
            valueAnimator = ValueAnimator.ofFloat(material, 0);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    material = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    material = 0;
                    invalidate();
                }
            });
            valueAnimator.start();
        }
    }

    private OnSeekBarChangedListener callback;

    public void setOnSeekChangedListener(OnSeekBarChangedListener listener) {
        callback = listener;
    }

    public interface OnSeekBarChangedListener {
        void onProgressChanged(SGLSeekBar seekBar, int percent);

        void onStartTrackingTouch(SGLSeekBar seekBar, int percent);

        void onStopTrackingTouch(SGLSeekBar seekBar, int percent);
    }
}

