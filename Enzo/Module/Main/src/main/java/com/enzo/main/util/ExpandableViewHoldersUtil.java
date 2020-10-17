package com.enzo.main.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class ExpandableViewHoldersUtil {

    private static void openH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            expandView.setVisibility(View.VISIBLE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationStart(Animator animation) {
                    final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(expandView, View.ALPHA, 1);
                    alphaAnimator.addListener(new ViewHolderAnimator.ViewHolderAnimatorListener(holder));
                    alphaAnimator.setDuration(400);
                    alphaAnimator.setStartDelay(100);
                    alphaAnimator.start();
                }
            });
            animator.start();
        } else {
            expandView.setVisibility(View.VISIBLE);
            expandView.setAlpha(1);
        }
    }

    private static void closeH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            expandView.setVisibility(View.GONE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder);
            expandView.setVisibility(View.VISIBLE);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }
            });
            animator.start();
        } else {
            expandView.setVisibility(View.GONE);
            expandView.setAlpha(0);
        }
    }

    public interface Expandable {
        View getExpandView();

        View getArrow();
    }

    public static class KeepOneH<VH extends RecyclerView.ViewHolder & Expandable> {
        private int _opened = -1;

        public void bind(VH holder, int pos) {
            if (pos == _opened) {
                holder.getArrow().setRotation(180);
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), false);
            } else {
                holder.getArrow().setRotation(0);
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), false);
            }
        }

        @SuppressWarnings("unchecked")
        public void toggle(VH holder) {
            if (_opened == holder.getAdapterPosition()) {
                _opened = -1;
                holder.getArrow().setRotation(0);
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), true);
            } else {
                int previous = _opened;
                _opened = holder.getAdapterPosition();
                holder.getArrow().setRotation(180);
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), true);

                final VH oldHolder = (VH) ((RecyclerView) holder.itemView.getParent()).findViewHolderForLayoutPosition(previous);
                if (oldHolder != null) {
                    oldHolder.getArrow().setRotation(0);
                    ExpandableViewHoldersUtil.closeH(oldHolder, oldHolder.getExpandView(), true);
                }
            }
        }
    }

}
