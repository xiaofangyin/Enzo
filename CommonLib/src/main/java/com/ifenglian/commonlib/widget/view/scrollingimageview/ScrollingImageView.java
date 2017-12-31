package com.ifenglian.commonlib.widget.view.scrollingimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.ifenglian.commonlib.R;

import static java.lang.Math.abs;

public class ScrollingImageView extends View {

    private Bitmap bitmap;
    private Rect clipBounds;

    private float speed;
    private float offset;
    private int maxBitmapHeight;
    private boolean isStarted;

    public ScrollingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        clipBounds = new Rect();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollingImageView, 0, 0);
        try {
            speed = ta.getDimension(R.styleable.ScrollingImageView_speed, 10);
            bitmap = BitmapFactory.decodeResource(getContext().getResources(), ta.getResourceId(R.styleable.ScrollingImageView_src, 0));
            maxBitmapHeight = bitmap.getHeight();
        } finally {
            ta.recycle();
        }

        start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), maxBitmapHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!isInEditMode()) {
            super.onDraw(canvas);
            if (canvas == null) {
                return;
            }

            canvas.getClipBounds(clipBounds);

            while (offset <= -bitmap.getWidth()) {
                offset += bitmap.getWidth();
            }

            float left = offset;
            while (left < clipBounds.width()) {
                canvas.drawBitmap(bitmap, getBitmapLeft(bitmap.getWidth(), left), 0, null);
                left += bitmap.getWidth();
            }

            if (isStarted && speed != 0) {
                offset -= abs(speed);
                invalidate();
            }
        }
    }

    private float getBitmapLeft(float layerWidth, float left) {
        if (speed < 0) {
            return clipBounds.width() - layerWidth - left;
        } else {
            return left;
        }
    }

    public void start() {
        if (!isStarted) {
            isStarted = true;
            invalidate();
        }
    }

    public void stop() {
        if (isStarted) {
            isStarted = false;
            invalidate();
        }
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        if (isStarted) {
            invalidate();
        }
    }
}
