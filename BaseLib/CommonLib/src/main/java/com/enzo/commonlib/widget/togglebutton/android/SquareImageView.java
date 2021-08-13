package com.enzo.commonlib.widget.togglebutton.android;

/**
 * Created by Riccardo Moro on 29/07/2016.
 */

import android.content.Context;
import android.util.AttributeSet;

/**
 * A simple squared image view
 */
public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Simply return the same measure as height for width
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
