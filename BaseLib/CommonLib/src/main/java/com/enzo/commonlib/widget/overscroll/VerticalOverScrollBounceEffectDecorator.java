package com.enzo.commonlib.widget.overscroll;

import android.view.MotionEvent;
import android.view.View;

import com.enzo.commonlib.widget.overscroll.adapters.IOverScrollDecoratorAdapter;

public class VerticalOverScrollBounceEffectDecorator extends OverScrollBounceEffectDecoratorBase {

    protected static class MotionAttributesVertical extends MotionAttributes {

        public boolean init(View view, MotionEvent event) {
            if (event.getHistorySize() == 0) {
                return false;
            }

            mAbsOffset = view.getTranslationY();
            mDeltaOffset = (event.getY(0) - event.getHistoricalY(0, 0)) * 2.5f;
            mDir = mDeltaOffset > 0;
            return true;
        }
    }

    protected static class AnimationAttributesVertical extends AnimationAttributes {

        public AnimationAttributesVertical() {
            mProperty = View.TRANSLATION_Y;
        }

        @Override
        protected void init(View view) {
            mAbsOffset = view.getTranslationY();
            mMaxOffset = view.getHeight();
        }
    }

    public VerticalOverScrollBounceEffectDecorator(IOverScrollDecoratorAdapter viewAdapter) {
        this(viewAdapter, DEFAULT_TOUCH_DRAG_MOVE_RATIO_FWD, DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK, DEFAULT_DECELERATE_FACTOR);
    }

    public VerticalOverScrollBounceEffectDecorator(IOverScrollDecoratorAdapter viewAdapter,
                                                   float touchDragRatioFwd, float touchDragRatioBck, float decelerateFactor) {
        super(viewAdapter, decelerateFactor, touchDragRatioFwd, touchDragRatioBck);
        mViewAdapter.getView().setOnTouchListener(this);
        mViewAdapter.getView().setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    protected MotionAttributes createMotionAttributes() {
        return new MotionAttributesVertical();
    }

    @Override
    protected AnimationAttributes createAnimationAttributes() {
        return new AnimationAttributesVertical();
    }

    @Override
    protected void translateView(View view, float offset) {
        view.setTranslationY(offset);
    }

    @Override
    protected void translateViewAndEvent(View view, float offset, MotionEvent event) {
        view.setTranslationY(offset);
        event.offsetLocation(offset - event.getY(0), 0f);
    }
}
