package com.enzo.commonlib.widget.pulltorefresh.recyclerview.base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 文 件 名: BasePullToRefreshView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class BasePullToRefreshView extends LinearLayout {

    //下拉的状态（还没到下拉到固定的高度时）
    public static final int STATE_PULL_DOWN = 0;
    //下拉到固定高度提示释放刷新的状态
    public static final int STATE_RELEASE_REFRESH = 1;
    //刷新状态
    public static final int STATE_REFRESHING = 2;
    //刷新完成
    public static final int STATE_SUCCESS = 3;
    //刷新失败
    public static final int STATE_FAILED = 4;
    //初始化状态
    public int mState = STATE_PULL_DOWN;

    private Handler handler;
    public View mContainer;
    public int mMeasuredHeight;

    private OnStateChangeListener onStateChangeListener;

    public BasePullToRefreshView(Context context) {
        super(context);
        handler = new Handler(Looper.getMainLooper());
        LayoutParams lp = new LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);
        initView(context);
    }

    /**
     * 根据下拉的移动的距离进行状态切换以及顶部刷新View高度控制
     */
    public void onMove(float distance) {
        if (getVisibleHeight() > 0 || distance > 0) {
            setVisibleHeight((int) distance + getVisibleHeight());
            if (mState <= STATE_RELEASE_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisibleHeight() > mMeasuredHeight) {
                    if (onStateChangeListener != null) {
                        onStateChangeListener.onStateChange(STATE_RELEASE_REFRESH);
                    }
                } else {
                    if (onStateChangeListener != null) {
                        onStateChangeListener.onStateChange(STATE_PULL_DOWN);
                    }
                }
            }
        }
    }

    /**
     * 处理下拉刷新释放后的动作
     */
    public boolean dealReleaseAction() {
        boolean isRefreshing = false;
        //如果刷新布局没有显示，就不用进行下一步了
        int height = getVisibleHeight();
        if (height == 0)
            isRefreshing = false;

        //下拉和释放两种状态之后就进入正在刷新状态
        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            if (onStateChangeListener != null) {
                onStateChangeListener.onStateChange(STATE_REFRESHING);
            }
            isRefreshing = true;
        }

        if (mState != STATE_REFRESHING) {
            scrollTo(0);
        }

        if (mState == STATE_REFRESHING) {
            scrollTo(mMeasuredHeight);
        }
        return isRefreshing;
    }

    /**
     * 刷新完成
     */
    public void refreshSuccess() {
        if (onStateChangeListener != null) {
            onStateChangeListener.onStateChange(STATE_SUCCESS);
        }
        handler.postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 500);
    }

    /**
     * 刷新失败
     */
    public void refreshFailed() {
        if (onStateChangeListener != null) {
            onStateChangeListener.onStateChange(STATE_FAILED);
        }
        handler.postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 500);
    }

    /**
     * 重置刷新布局
     */
    public void reset() {
        scrollTo(0);
        handler.postDelayed(new Runnable() {
            public void run() {
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStateChange(STATE_PULL_DOWN);
                }
            }
        }, 500);
    }

    /**
     * 获取状态
     */
    public int getState() {
        return mState;
    }

    /**
     * 滚动到的位置
     */
    public void scrollTo(int height) {
        //动画设置高度
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), height);
        animator.setDuration(250).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * 根据状态设置刷新顶部的高度
     */
    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * 获取刷新布局的高度
     */
    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    /**
     * 组件初始化
     */
    public abstract void initView(Context context);

    /**
     * 显示加载更新时间
     */
    public abstract void setRefreshTimeVisible(String tag);

    /**
     * 手指按下回调
     */
    public abstract void onRefreshTime();

    /**
     * 销毁页面对象和动画，防止内存泄漏
     */
    public abstract void destroy();


    /**
     * 状态变化通知
     */
    public void setOnStateChangeListener(OnStateChangeListener listener) {
        onStateChangeListener = listener;
    }

    public OnStateChangeListener getOnStateChangeListener() {
        return onStateChangeListener;
    }

    public interface OnStateChangeListener {
        void onStateChange(int state);
    }
}
