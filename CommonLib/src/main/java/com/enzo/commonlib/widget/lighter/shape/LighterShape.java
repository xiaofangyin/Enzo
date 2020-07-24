package com.enzo.commonlib.widget.lighter.shape;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public abstract class LighterShape {

    protected RectF highlightedViewRect;
    protected Path path;
    protected Paint paint;

    protected LighterShape(float blurRadius) {
        path = new Path();
        paint = new Paint();

        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        if (blurRadius > 0) {
            paint.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.SOLID));
        }
    }

    public void onDraw(Canvas canvas) {
        if (path != null
                && paint != null) {
            canvas.drawPath(path, paint);
        }
    }

    public void setViewRect(RectF rect) {
        highlightedViewRect = rect;
    }

    public RectF getViewRect() {
        return highlightedViewRect;
    }

    public boolean isViewRectEmpty() {
        return highlightedViewRect == null
                || highlightedViewRect.isEmpty();
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Path getShapePath() {
        return path;
    }
}
