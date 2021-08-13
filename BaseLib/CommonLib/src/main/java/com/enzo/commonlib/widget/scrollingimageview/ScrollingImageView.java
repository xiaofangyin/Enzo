package com.enzo.commonlib.widget.scrollingimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.enzo.commonlib.R;

import static java.lang.Math.abs;

public class ScrollingImageView extends View {

    private Bitmap mBitmap;
    private Rect clipBounds;

    private float speed;
    private float offset;
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
            mBitmap = BitmapFactory.decodeResource(getContext().getResources(), ta.getResourceId(R.styleable.ScrollingImageView_src, 0));
        } finally {
            ta.recycle();
        }

        start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = mBitmap == null ? 0 : mBitmap.getHeight();
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!isInEditMode()) {
            super.onDraw(canvas);
            canvas.getClipBounds(clipBounds);

            while (offset <= -mBitmap.getWidth()) {
                offset += mBitmap.getWidth();
            }

            //例如图片长2750 clipBounds.width()为1080，当offset(left)为-2600时，while判断条件成立，执行绘制bitmap;
            //(left + mBitmap.getWidth()) = (-2600 + 2750) = 150,150 < 1080成立，while判断条件又成立，canvas又从150位置开始绘制bitmap，
            //这样就把图片的首跟尾衔接起来了
            float left = offset;
            while (left < clipBounds.width()) {
                canvas.drawBitmap(mBitmap, getBitmapLeft(mBitmap.getWidth(), left), 0, null);
                left += mBitmap.getWidth();
            }

            if (isStarted && speed != 0) {
                offset -= abs(speed);
                invalidate();
            }
        }
    }

    private float getBitmapLeft(float bitmapWidth, float left) {
        if (speed < 0) {
            return clipBounds.width() - bitmapWidth - left;
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
