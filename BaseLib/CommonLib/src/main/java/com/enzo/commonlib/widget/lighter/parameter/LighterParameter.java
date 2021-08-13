package com.enzo.commonlib.widget.lighter.parameter;

import android.graphics.RectF;
import android.view.View;
import android.view.animation.Animation;

import com.enzo.commonlib.widget.lighter.shape.LighterShape;

public class LighterParameter {
    private int highlightedViewId;
    private View highlightedView;
    private RectF highlightedViewRect;

    private int tipLayoutId;
    private View tipView;

    private LighterShape lighterShape;

    private float shapeXOffset;
    private float shapeYOffset;

    private int tipViewRelativeDirection;
    private MarginOffset tipViewRelativeMarginOffset;
    private Animation tipViewDisplayAnimation;

    private LighterParameter() {
    }


    public void setHighlightedViewId(int highlightedViewId) {
        this.highlightedViewId = highlightedViewId;
    }

    public void setHighlightedView(View highlightedView) {
        this.highlightedView = highlightedView;
    }

    public void setTipLayoutId(int tipLayoutId) {
        this.tipLayoutId = tipLayoutId;
    }

    public void setTipView(View tipView) {
        this.tipView = tipView;
    }

    public void setLighterShape(LighterShape lighterShape) {
        this.lighterShape = lighterShape;
    }

    public void setShapeXOffset(float shapeXOffset) {
        this.shapeXOffset = shapeXOffset;
    }

    public void setShapeYOffset(float shapeYOffset) {
        this.shapeYOffset = shapeYOffset;
    }

    public void setTipViewRelativeDirection(int tipViewRelativeDirection) {
        this.tipViewRelativeDirection = tipViewRelativeDirection;
    }

    public void setTipViewRelativeMarginOffset(MarginOffset marginOffset) {
        this.tipViewRelativeMarginOffset = marginOffset;
    }

    public void setHighlightedViewRect(RectF highlightedViewRect) {
        this.highlightedViewRect = highlightedViewRect;
    }

    public void setTipViewDisplayAnimation(Animation tipViewDisplayAnimation) {
        this.tipViewDisplayAnimation = tipViewDisplayAnimation;
    }

    public RectF getHighlightedViewRect() {
        return highlightedViewRect;
    }

    public int getTipViewRelativeDirection() {
        return tipViewRelativeDirection;
    }

    public Animation getTipViewDisplayAnimation() {
        return tipViewDisplayAnimation;
    }

    public MarginOffset getTipViewRelativeMarginOffset() {
        return tipViewRelativeMarginOffset;
    }

    public int getHighlightedViewId() {
        return highlightedViewId;
    }

    public View getHighlightedView() {
        return highlightedView;
    }

    public int getTipLayoutId() {
        return tipLayoutId;
    }

    public View getTipView() {
        return tipView;
    }

    public LighterShape getLighterShape() {
        return lighterShape;
    }

    public float getShapeXOffset() {
        return shapeXOffset;
    }

    public float getShapeYOffset() {
        return shapeYOffset;
    }

    public static class Builder {
        private LighterParameter mLighterParameter;

        public Builder() {
            mLighterParameter = new LighterParameter();
        }

        public Builder setHighlightedViewId(int highlightedViewId) {
            mLighterParameter.setHighlightedViewId(highlightedViewId);
            return this;
        }

        public Builder setHighlightedView(View highlightedView) {
            mLighterParameter.setHighlightedView(highlightedView);
            return this;
        }

        public Builder setTipLayoutId(int tipLayoutId) {
            mLighterParameter.setTipLayoutId(tipLayoutId);
            return this;
        }

        public Builder setTipView(View tipView) {
            mLighterParameter.setTipView(tipView);
            return this;
        }

        public Builder setLighterShape(LighterShape lighterShape) {
            mLighterParameter.setLighterShape(lighterShape);
            return this;
        }

        public Builder setShapeXOffset(float xOffset) {
            mLighterParameter.setShapeXOffset(xOffset);
            return this;
        }

        public Builder setShapeYOffset(float yOffset) {
            mLighterParameter.setShapeYOffset(yOffset);
            return this;
        }

        public Builder setTipViewRelativeDirection(int tipViewRelativeDirection) {
            mLighterParameter.setTipViewRelativeDirection(tipViewRelativeDirection);
            return this;
        }

        public Builder setTipViewRelativeOffset(MarginOffset marginOffset) {
            mLighterParameter.setTipViewRelativeMarginOffset(marginOffset);
            return this;
        }

        public Builder setTipViewDisplayAnimation(Animation tipViewDisplayAnimation) {
            mLighterParameter.setTipViewDisplayAnimation(tipViewDisplayAnimation);
            return this;
        }

        public LighterParameter build() {
            return mLighterParameter;
        }
    }
}
