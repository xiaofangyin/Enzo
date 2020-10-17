package com.enzo.commonlib.widget.loadinglayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.enzo.commonlib.R;

/**
 * 文 件 名: LoadingLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2016/6/6
 * 邮   箱: xiaofangyinwork@163.com
 */
public class LoadingLayout extends FrameLayout {

    private static final int EMPTY_VIEW_INDEX = 0;
    private static final int ERROR_VIEW_INDEX = 1;
    private static final int LOADING_VIEW_INDEX = 2;
    private static final int SUCCESS_VIEW_INDEX = 3;

    private OnClickListener onRetryClickListener;
    private ObjectAnimator animator;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        try {
            int emptyView = a.getResourceId(R.styleable.LoadingLayout_com_emptyView, R.layout.lib_loading_view_empty);
            int errorView = a.getResourceId(R.styleable.LoadingLayout_com_errorView, R.layout.lib_loading_view_error);
            int loadingView = a.getResourceId(R.styleable.LoadingLayout_com_loadingView, R.layout.lib_loading_view_loading);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            addView(inflater.inflate(emptyView, this, false), EMPTY_VIEW_INDEX);
            addView(inflater.inflate(errorView, this, false), ERROR_VIEW_INDEX);
            addView(inflater.inflate(loadingView, this, false), LOADING_VIEW_INDEX);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_dark_black));
        for (int i = 0; i < getChildCount() - 1; i++) {
            getChildAt(i).setVisibility(GONE);
            //设置失败页面重新加载回调
            if (i == ERROR_VIEW_INDEX) {
                getChildAt(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != onRetryClickListener) {
                            showLoading();
                            onRetryClickListener.onClick(v);
                        }
                    }
                });
            }
        }
        showLoading();
    }

    /**
     * 显示空页面
     */
    public void showEmpty() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            child.setVisibility(i == EMPTY_VIEW_INDEX ? View.VISIBLE : View.GONE);
        }
        stopLoadingAnim();
    }

    /**
     * 显示加载错误页面
     */
    public void showError() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            child.setVisibility(i == ERROR_VIEW_INDEX ? View.VISIBLE : View.GONE);
        }
        stopLoadingAnim();
    }

    /**
     * 显示正在加载页面
     */
    public void showLoading() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            child.setVisibility(i == LOADING_VIEW_INDEX ? View.VISIBLE : View.GONE);
        }
        startLoadingAnim();
    }

    /**
     * 显示加载成功页面
     */
    public void showContent() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            child.setVisibility(i == SUCCESS_VIEW_INDEX ? View.VISIBLE : View.GONE);
        }
        stopLoadingAnim();

        ObjectAnimator animator = ObjectAnimator.ofFloat(getChildAt(SUCCESS_VIEW_INDEX), "alpha", 0f, 1f);
        animator.setDuration(800);
        animator.start();
    }

    /**
     * AccelerateDecelerateInterpolator    先加速后减速
     * AccelerateInterpolator              一直加速
     * AnticipateInterpolator              开始的时候会在初始位置后退一点，然后加速到目标位置
     * AnticipateOvershootInterpolator     开始的时候会在初始位置后退一点，然后加速超过目标位置一点，然后返回目标位置
     * BounceInterpolator                   动画以弹弹的形式到目标位置（具体效果可以往下看）
     * CycleInterpolator                    动画循环播放特定的次数，速率改变沿着正弦曲线（在Tran中是指定偏移量正反运动（详情看效果））
     * DecelerateInterpolator                减速到达目标位置
     * LinearInterpolator                    以线性速度改变（说白了就是匀速）
     * OvershootInterpolator                 超过目标位置一点，然后回到目标位置
     * 开启动画
     */
    private void startLoadingAnim() {
        ImageView imageLoading = findViewById(R.id.ab_iv_loading);
        if (imageLoading != null) {
            if (animator == null) {
                animator = ObjectAnimator.ofFloat(imageLoading, "rotation", 0f, 360f);
                animator.setDuration(1000);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setInterpolator(new LinearInterpolator());
            }
            animator.start();
        }
    }

    /**
     * 结束动画
     */
    private void stopLoadingAnim() {
        ImageView imageLoading = findViewById(R.id.ab_iv_loading);
        if (imageLoading != null) {
            if (animator != null && animator.isRunning()) {
                animator.cancel();
            }
        }
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }
}
