package com.enzo.commonlib.widget.lighter.shape;

import android.graphics.Path;
import android.graphics.RectF;

public class CircleShape extends LighterShape {

    public CircleShape() {
        super(15);
    }

    public CircleShape(float blurRadius) {
        super(blurRadius);
    }

    @Override
    public void setViewRect(RectF rect) {
        super.setViewRect(rect);

        if (!isViewRectEmpty()) {
            path.reset();
            path.addCircle(highlightedViewRect.centerX(), highlightedViewRect.centerY(),
                    Math.max(highlightedViewRect.width(), highlightedViewRect.height()) / 2, Path.Direction.CW);
        }
    }
}
