package com.enzo.commonlib.widget.lighter.shape;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class RectShape extends LighterShape {

    private float mXRadius;
    private float mYRadius;

    public RectShape() {
        this(5, 5, 15);
    }

    public RectShape(float rx, float ry, float blurRadius) {
        super(blurRadius);
        mXRadius = rx;
        mYRadius = ry;
    }

    @Override
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public void setViewRect(RectF rect) {
        super.setViewRect(rect);

        if (!isViewRectEmpty()) {
            path.reset();
            path.addRoundRect(highlightedViewRect, mXRadius, mYRadius, Path.Direction.CW);
        }
    }
}
