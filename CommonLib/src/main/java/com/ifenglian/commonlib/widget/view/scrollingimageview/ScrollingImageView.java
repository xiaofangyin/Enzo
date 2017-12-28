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

import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;
import static java.util.Collections.singletonList;

public class ScrollingImageView extends View {

    private List<Bitmap> bitmaps;
    private float speed;
    private int[] scene;
    private int arrayIndex = 0;
    private int maxBitmapHeight = 0;

    private Rect clipBounds = new Rect();
    private float offset = 0;

    private boolean isStarted;

    public ScrollingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollingImageView, 0, 0);
        try {
            speed = ta.getDimension(R.styleable.ScrollingImageView_speed, 10);
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), ta.getResourceId(R.styleable.ScrollingImageView_src, 0));
            if (bitmap != null) {
                bitmaps = singletonList(bitmap);
                scene = new int[]{0};
                maxBitmapHeight = bitmaps.get(0).getHeight();
            } else {
                bitmaps = Collections.emptyList();
            }
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
            if (canvas == null || bitmaps.isEmpty()) {
                return;
            }

            canvas.getClipBounds(clipBounds);

            while (offset <= -getBitmap(arrayIndex).getWidth()) {
                offset += getBitmap(arrayIndex).getWidth();
                arrayIndex = (arrayIndex + 1) % scene.length;
            }

            float left = offset;
            for (int i = 0; left < clipBounds.width(); i++) {
                Bitmap bitmap = getBitmap((arrayIndex + i) % scene.length);
                int width = bitmap.getWidth();
                canvas.drawBitmap(bitmap, getBitmapLeft(width, left), 0, null);
                left += width;
            }

            if (isStarted && speed != 0) {
                offset -= abs(speed);
                postInvalidateOnAnimation();
            }
        }
    }

    private Bitmap getBitmap(int sceneIndex) {
        return bitmaps.get(scene[sceneIndex]);
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
            postInvalidateOnAnimation();
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
            postInvalidateOnAnimation();
        }
    }
}
