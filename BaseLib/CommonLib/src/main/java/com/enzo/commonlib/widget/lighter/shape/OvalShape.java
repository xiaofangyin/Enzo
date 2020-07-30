package com.enzo.commonlib.widget.lighter.shape;

import android.graphics.Path;
import android.graphics.RectF;

public class OvalShape extends LighterShape {

    public OvalShape() {
        super(15);
    }

    public OvalShape(float blurRadius) {
        super(blurRadius);
    }

    @Override
    public void setViewRect(RectF rect) {
        super.setViewRect(rect);

        if (!isViewRectEmpty()) {
            path.reset();
            path.addOval(rect, Path.Direction.CW);
        }
    }
}
