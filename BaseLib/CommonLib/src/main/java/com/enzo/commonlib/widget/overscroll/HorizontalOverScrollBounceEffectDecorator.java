package com.enzo.commonlib.widget.overscroll;

import android.view.MotionEvent;
import android.view.View;

import com.enzo.commonlib.widget.overscroll.adapters.IOverScrollDecoratorAdapter;

public class HorizontalOverScrollBounceEffectDecorator extends OverScrollBounceEffectDecoratorBase {

    protected static class MotionAttributesHorizontal extends MotionAttributes {

        public boolean init(View view, MotionEvent event) {

            if (event.getHistorySize() == 0) {
                return false;
            }

            mAbsOffset = view.getTranslationX();
            mDeltaOffset = (event.getX(0) - event.getHistoricalX(0, 0));
            mDir = mDeltaOffset > 0;

            return true;
        }
    }

    protected static class AnimationAttributesHorizontal extends AnimationAttributes {

        public AnimationAttributesHorizontal() {
            mProperty = View.TRANSLATION_X;
        }

        @Override
        protected void init(View view) {
            mAbsOffset = view.getTranslationX();
            mMaxOffset = view.getWidth();
        }
    }

    public HorizontalOverScrollBounceEffectDecorator(IOverScrollDecoratorAdapter viewAdapter) {
        this(viewAdapter, DEFAULT_TOUCH_DRAG_MOVE_RATIO_FWD, DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK, DEFAULT_DECELERATE_FACTOR);
    }

    public HorizontalOverScrollBounceEffectDecorator(IOverScrollDecoratorAdapter viewAdapter,
                                                     float touchDragRatioFwd, float touchDragRatioBck, float decelerateFactor) {
        super(viewAdapter, decelerateFactor, touchDragRatioFwd, touchDragRatioBck);

        mViewAdapter.getView().setOnTouchListener(this);
        mViewAdapter.getView().setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    protected MotionAttributes createMotionAttributes() {
        return new MotionAttributesHorizontal();
    }

    @Override
    protected AnimationAttributes createAnimationAttributes() {
        return new AnimationAttributesHorizontal();
    }

    @Override
    protected void translateView(View view, float offset) {
        view.setTranslationX(offset);
    }

    @Override
    protected void translateViewAndEvent(View view, float offset, MotionEvent event) {
        view.setTranslationX(offset);
        event.offsetLocation(offset - event.getX(0), 0f);
    }
}
