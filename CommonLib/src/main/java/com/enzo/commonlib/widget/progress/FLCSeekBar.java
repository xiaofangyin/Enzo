package com.enzo.commonlib.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.enzo.commonlib.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * 文 件 名: SGLSeekBar
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/8/17
 * 邮   箱: xiaofangyinwork@163.com
 */
public class FLCSeekBar extends View {

    private static final String TAG = "FLCSeekBar";
    private Paint paint;
    private RectF line;
    private Thumb thumb;

    private int colorLineSelected, colorLineUnSelected, colorEdge;
    private int lineTop, lineBottom, lineLeft, lineRight;
    private int lineWidth, lineHeight, lineCorners;
    private int cellsCount = 1;
    private float cellsPercent;
    private Bitmap bitmapThumb;

    public FLCSeekBar(Context context) {
        this(context, null);
    }

    public FLCSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        thumb = new Thumb();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FLCSeekBar);
        try {
            colorLineSelected = a.getColor(R.styleable.FLCSeekBar_lineColorSelected, getResources().getColor(R.color.color_green));
            colorLineUnSelected = a.getColor(R.styleable.FLCSeekBar_lineColorUnSelected, getResources().getColor(R.color.color_d9));
            colorEdge = a.getColor(R.styleable.FLCSeekBar_circleColorEdge, 0xFF30B5FF);
            lineHeight = (int) a.getDimension(R.styleable.FLCSeekBar_lineHeight, dip2px(4));
            bitmapThumb = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.FLCSeekBar_bitmapThumb, 0));
            int cells = a.getInt(R.styleable.FLCSeekBar_cells, 1);
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minimumWidth = getSuggestedMinimumWidth();
        int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = specSize;
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = bitmapThumb == null ? dip2px(25) : bitmapThumb.getHeight();
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                break;
        }
        return defaultHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        if (bitmapThumb == null) {
            lineLeft = h / 2;
            lineRight = w - h / 2;
            lineWidth = lineRight - lineLeft;
            thumb.onSizeChanged(h / 2, h / 2, h, lineWidth);
        } else {
            lineLeft = bitmapThumb.getWidth() / 2;
            lineRight = w - bitmapThumb.getWidth() / 2;
            lineWidth = lineRight - lineLeft;
            thumb.onSizeChanged(bitmapThumb.getWidth() / 2, h / 2, h, lineWidth);
        }
        lineTop = h / 2 - lineHeight / 2;
        lineBottom = h / 2 + lineHeight / 2;

        line = new RectF();
        line.set(lineLeft, lineTop, lineRight, lineBottom);
        lineCorners = (int) ((lineBottom - lineTop) * 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        line.set(lineLeft, lineTop, lineRight, lineBottom);
        paint.setColor(colorLineUnSelected);
        canvas.drawRoundRect(line, lineCorners, lineCorners, paint);

        paint.setColor(colorLineSelected);
        line.set(lineLeft, lineTop, thumb.left + thumb.thumbWidth / 2 + thumb.lineWidth * thumb.currPercent, lineBottom);
        canvas.drawRoundRect(line, lineCorners, lineCorners, paint);

        if (cellsCount > 1) {
            paint.setColor(colorEdge);
            for (int i = 0; i <= cellsCount; i++) {
                canvas.drawCircle(lineLeft + i * cellsPercent * lineWidth, getHeight() / 2, lineHeight / 2, paint);
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
                        callback.onStartTrackingTouch(FLCSeekBar.this, Math.round(thumb.currPercent * 100));
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
                        callback.onProgressChanged(FLCSeekBar.this, progress);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                thumb.materialRestore();
                if (callback != null) {
                    callback.onStopTrackingTouch(FLCSeekBar.this, Math.round(thumb.currPercent * 100));
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
        int parentHeight;
        int thumbWidth, thumbHeight;
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

        void onSizeChanged(int centerX, int centerY, int hSize, int lineWidth) {
            this.lineWidth = lineWidth;
            this.parentHeight = hSize;
            if (bitmapThumb == null) {
                thumbWidth = (int) (hSize * 0.8f);
                thumbHeight = hSize;
                left = centerX - thumbWidth / 2;
                right = centerX + thumbWidth / 2;

                int radius = (int) (thumbWidth * 0.5f);
                int mShadowCenterX = thumbWidth / 2;
                int mShadowCenterY = thumbHeight / 2;
                shadowGradient = new RadialGradient(mShadowCenterX, mShadowCenterY, radius, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
            } else {
                thumbWidth = bitmapThumb.getWidth();
                thumbHeight = bitmapThumb.getHeight();
                left = centerX - bitmapThumb.getWidth() / 2;
                right = centerX + bitmapThumb.getWidth() / 2;
            }

            top = centerY - thumbHeight / 2;
            bottom = centerY + thumbHeight / 2;

            defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            defaultPaint.setAntiAlias(true);
            defaultPaint.setDither(true);
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
            if (bitmapThumb == null) {
                int centerX = thumbWidth / 2;
                int centerY = thumbHeight / 2;
                int radius = thumbWidth / 2;
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
            } else {
                int top = parentHeight / 2 - bitmapThumb.getHeight() / 2;
                canvas.drawBitmap(bitmapThumb, 0, top, defaultPaint);
            }
        }

        private void materialRestore() {
            if (valueAnimator != null) valueAnimator.cancel();
            valueAnimator = ValueAnimator.ofFloat(material, 0);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    material = (float) animation.getAnimatedValue();
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
        void onProgressChanged(FLCSeekBar seekBar, int progress);

        void onStartTrackingTouch(FLCSeekBar seekBar, int progress);

        void onStopTrackingTouch(FLCSeekBar seekBar, int progress);
    }

    private int dip2px(float dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
