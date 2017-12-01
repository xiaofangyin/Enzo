
package com.ifenglian.commonlib.widget.view.tablayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 文 件 名: TabIconView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class TabIconView extends AppCompatImageView {

    private Paint mPaint;
    private Bitmap mSelectedIcon;
    private Bitmap mNormalIcon;
    private int mSelectedAlpha = 0;

    public TabIconView(Context context) {
        super(context);
    }

    public TabIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public final void init(int normal, int selected) {
        mNormalIcon = createBitmap(normal);
        mSelectedIcon = createBitmap(selected);
        mPaint = new Paint(1);
    }

    private Bitmap createBitmap(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null) {
            return;
        }
        mPaint.setAlpha(255 - mSelectedAlpha);
        canvas.drawBitmap(mNormalIcon, getWidth() / 2 - mNormalIcon.getWidth() / 2, 0, mPaint);
        mPaint.setAlpha(mSelectedAlpha);
        canvas.drawBitmap(mSelectedIcon, getWidth() / 2 - mSelectedIcon.getWidth() / 2, 0, mPaint);
    }

    public final void changeSelectedAlpha(int alpha) {
        mSelectedAlpha = alpha;
        invalidate();
    }

    public final void transformPage(float offset) {
        changeSelectedAlpha((int) (255 * (1 - offset)));
    }
}
