package com.enzo.commonlib.widget.overscroll;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.enzo.commonlib.widget.overscroll.adapters.IOverScrollDecoratorAdapter;

public abstract class OverScrollBounceEffectDecoratorBase implements View.OnTouchListener {

    public static final float DEFAULT_TOUCH_DRAG_MOVE_RATIO_FWD = 3f;
    public static final float DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK = 1f;
    public static final float DEFAULT_DECELERATE_FACTOR = -2f;

    protected static final int MAX_BOUNCE_BACK_DURATION_MS = 800;
    protected static final int MIN_BOUNCE_BACK_DURATION_MS = 200;

    protected final OverScrollStartAttributes mStartAttr = new OverScrollStartAttributes();
    protected final IOverScrollDecoratorAdapter mViewAdapter;

    protected final IdleState mIdleState;
    protected final OverScrollingState mOverScrollingState;
    protected final BounceBackState mBounceBackState;
    protected IDecoratorState mCurrentState;

    protected float mVelocity;

    protected abstract static class MotionAttributes {
        public float mAbsOffset;
        public float mDeltaOffset;
        public boolean mDir; // True = 'forward', false = 'backwards'.

        protected abstract boolean init(View view, MotionEvent event);
    }

    protected static class OverScrollStartAttributes {
        protected int mPointerId;
        protected float mAbsOffset;
        protected boolean mDir; // True = 'forward', false = 'backwards'.
    }

    protected abstract static class AnimationAttributes {
        public Property<View, Float> mProperty;
        public float mAbsOffset;
        public float mMaxOffset;

        protected abstract void init(View view);
    }

    protected interface IDecoratorState {

        boolean handleMoveTouchEvent(MotionEvent event);

        boolean handleUpOrCancelTouchEvent(MotionEvent event);

        void handleEntrance();
    }

    protected class IdleState implements IDecoratorState {

        final MotionAttributes mMoveAttr;

        public IdleState() {
            mMoveAttr = createMotionAttributes();
        }

        @Override
        public boolean handleMoveTouchEvent(MotionEvent event) {

            final View view = mViewAdapter.getView();
            if (!mMoveAttr.init(view, event)) {
                return false;
            }

            if ((mViewAdapter.isInAbsoluteStart() && mMoveAttr.mDir) ||
                    (mViewAdapter.isInAbsoluteEnd() && !mMoveAttr.mDir)) {

                mStartAttr.mPointerId = event.getPointerId(0);
                mStartAttr.mAbsOffset = mMoveAttr.mAbsOffset;
                mStartAttr.mDir = mMoveAttr.mDir;

                enterState(mOverScrollingState);
                return mOverScrollingState.handleMoveTouchEvent(event);
            }

            return false;
        }

        @Override
        public boolean handleUpOrCancelTouchEvent(MotionEvent event) {
            return false;
        }

        @Override
        public void handleEntrance() {
        }
    }

    protected class OverScrollingState implements IDecoratorState {

        protected final float mTouchDragRatioFwd;
        protected final float mTouchDragRatioBck;

        final MotionAttributes mMoveAttr;

        public OverScrollingState(float touchDragRatioFwd, float touchDragRatioBck) {
            mMoveAttr = createMotionAttributes();
            mTouchDragRatioFwd = touchDragRatioFwd;
            mTouchDragRatioBck = touchDragRatioBck;
        }

        @Override
        public boolean handleMoveTouchEvent(MotionEvent event) {
            if (mStartAttr.mPointerId != event.getPointerId(0)) {
                enterState(mBounceBackState);
                return true;
            }

            final View view = mViewAdapter.getView();
            if (!mMoveAttr.init(view, event)) {
                return true;
            }

            float deltaOffset = mMoveAttr.mDeltaOffset / (mMoveAttr.mDir == mStartAttr.mDir ? mTouchDragRatioFwd : mTouchDragRatioBck);
            float newOffset = mMoveAttr.mAbsOffset + deltaOffset;

            if ((mStartAttr.mDir && !mMoveAttr.mDir && (newOffset <= mStartAttr.mAbsOffset)) ||
                    (!mStartAttr.mDir && mMoveAttr.mDir && (newOffset >= mStartAttr.mAbsOffset))) {
                translateViewAndEvent(view, mStartAttr.mAbsOffset, event);

                enterState(mIdleState);
                return true;
            }

            if (view.getParent() != null) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
            }

            long dt = event.getEventTime() - event.getHistoricalEventTime(0);
            if (dt > 0) {
                mVelocity = deltaOffset / dt;
            }

            translateView(view, newOffset);

            return true;
        }

        @Override
        public boolean handleUpOrCancelTouchEvent(MotionEvent event) {
            enterState(mBounceBackState);
            return true;
        }

        @Override
        public void handleEntrance() {
        }
    }

    protected class BounceBackState implements IDecoratorState, Animator.AnimatorListener {

        protected final Interpolator mBounceBackInterpolator = new DecelerateInterpolator();
        protected final float mDecelerateFactor;
        protected final float mDoubleDecelerateFactor;

        protected final AnimationAttributes mAnimAttributes;

        public BounceBackState(float decelerateFactor) {
            mDecelerateFactor = decelerateFactor;
            mDoubleDecelerateFactor = 2f * decelerateFactor;

            mAnimAttributes = createAnimationAttributes();
        }

        @Override
        public void handleEntrance() {
            Animator bounceBackAnim = createAnimator();
            bounceBackAnim.addListener(this);

            bounceBackAnim.start();
        }

        @Override
        public boolean handleMoveTouchEvent(MotionEvent event) {
            return true;
        }

        @Override
        public boolean handleUpOrCancelTouchEvent(MotionEvent event) {
            return true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            enterState(mIdleState);
        }

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }

        protected Animator createAnimator() {

            final View view = mViewAdapter.getView();

            mAnimAttributes.init(view);
            if (mVelocity == 0f || (mVelocity < 0 && mStartAttr.mDir) || (mVelocity > 0 && !mStartAttr.mDir)) {
                return createBounceBackAnimator(mAnimAttributes.mAbsOffset);
            }

            float slowdownDuration = -mVelocity / mDecelerateFactor;
            slowdownDuration = (slowdownDuration < 0 ? 0 : slowdownDuration); // Happens in counter-direction dragging

            float slowdownDistance = -mVelocity * mVelocity / mDoubleDecelerateFactor;
            float slowdownEndOffset = mAnimAttributes.mAbsOffset + slowdownDistance;

            ObjectAnimator slowdownAnim = ObjectAnimator.ofFloat(view, mAnimAttributes.mProperty, slowdownEndOffset);
            slowdownAnim.setDuration((int) slowdownDuration);
            slowdownAnim.setInterpolator(mBounceBackInterpolator);

            ObjectAnimator bounceBackAnim = createBounceBackAnimator(slowdownEndOffset);

            AnimatorSet wholeAnim = new AnimatorSet();
            wholeAnim.playSequentially(slowdownAnim, bounceBackAnim);
            return wholeAnim;
        }

        private ObjectAnimator createBounceBackAnimator(float startOffset) {

            final View view = mViewAdapter.getView();

            float bounceBackDuration = (Math.abs(startOffset) / mAnimAttributes.mMaxOffset) * MAX_BOUNCE_BACK_DURATION_MS;
            ObjectAnimator bounceBackAnim = ObjectAnimator.ofFloat(view, mAnimAttributes.mProperty, mStartAttr.mAbsOffset);
            bounceBackAnim.setDuration(Math.max((int) bounceBackDuration, MIN_BOUNCE_BACK_DURATION_MS));
            bounceBackAnim.setInterpolator(mBounceBackInterpolator);
            return bounceBackAnim;
        }
    }

    public OverScrollBounceEffectDecoratorBase(IOverScrollDecoratorAdapter viewAdapter, float decelerateFactor, float touchDragRatioFwd, float touchDragRatioBck) {
        mViewAdapter = viewAdapter;

        mBounceBackState = new BounceBackState(decelerateFactor);
        mOverScrollingState = new OverScrollingState(touchDragRatioFwd, touchDragRatioBck);
        mIdleState = new IdleState();

        mCurrentState = mIdleState;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                return mCurrentState.handleMoveTouchEvent(event);

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                return mCurrentState.handleUpOrCancelTouchEvent(event);
        }

        return false;
    }

    protected void enterState(IDecoratorState state) {
        mCurrentState = state;
        mCurrentState.handleEntrance();
    }

    protected abstract MotionAttributes createMotionAttributes();

    protected abstract AnimationAttributes createAnimationAttributes();

    protected abstract void translateView(View view, float offset);

    protected abstract void translateViewAndEvent(View view, float offset, MotionEvent event);
}
