package com.enzo.commonlib.widget.loadinglayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.enzo.commonlib.R;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 文 件 名: LoadingLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2016/6/6
 * 邮   箱: xiaofangyinwork@163.com
 */
public class LoadingLayout extends FrameLayout {

    private OnClickListener onRetryClickListener;
    private AnimationDrawable loadingAnimationDrawable;

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
            inflater.inflate(emptyView, this, true);
            inflater.inflate(errorView, this, true);
            inflater.inflate(loadingView, this, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount() - 1; i++) {
            getChildAt(i).setVisibility(GONE);
            //设置失败页面重新加载回调
            if (i == 1) {
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
        ImageView imageLoading = findViewById(R.id.ab_iv_loading);
        loadingAnimationDrawable = (AnimationDrawable) imageLoading.getBackground();
        showLoading();
    }

    /**
     * 显示空页面
     */
    public void showEmpty() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            child.setVisibility(i == 0 ? View.VISIBLE : View.GONE);
        }
        stopLoadingAnim();
    }

    /**
     * 显示加载错误页面
     */
    public void showError() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            child.setVisibility(i == 1 ? View.VISIBLE : View.GONE);
        }
        stopLoadingAnim();
    }

    /**
     * 显示正在加载页面
     */
    public void showLoading() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            child.setVisibility(i == 2 ? View.VISIBLE : View.GONE);
        }
        startLoadingAnim();
    }

    /**
     * 显示加载成功页面
     */
    public void showContent() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            child.setVisibility(i == 3 ? View.VISIBLE : View.GONE);
        }
        stopLoadingAnim();

        ObjectAnimator animator = ObjectAnimator.ofFloat(getChildAt(3), "alpha", 0f, 1f);
        animator.setDuration(400);
        animator.start();
    }

    /**
     * 开启动画
     */
    private void startLoadingAnim() {
        if (loadingAnimationDrawable != null && !loadingAnimationDrawable.isRunning()) {
            loadingAnimationDrawable.start();
        }
    }

    /**
     * 结束动画
     */
    private void stopLoadingAnim() {
        if (loadingAnimationDrawable != null && loadingAnimationDrawable.isRunning()) {
            loadingAnimationDrawable.stop();
        }
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }
}
