package com.ifenglian.commonlib.widget.view.danmu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenglian.commonlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: FLDanMuView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/9/19
 * 邮   箱: xiaofy@ifenglian.com
 */
public class FLDanMuView extends RelativeLayout {

    private static String TAG = FLDanMuView.class.getSimpleName();
    private static final int DURATION = 10000;
    private static final int DELAY_DURATION = 1000;
    private boolean mIsWorking = false;
    private boolean isFirst = true;
    private int mCurrentIndex;
    private List<FLDanMuBean> danMuList;

    private enum DanMuAction {
        SHOW, HIDE
    }

    public FLDanMuView(Context context) {
        this(context, null, 0);
    }

    public FLDanMuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FLDanMuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        danMuList = new ArrayList<>();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            int row = msg.what;
            final View view = createDanMuView(row);
            int transX = getScreenWidth() + view.getMeasuredWidth();
            ViewPropertyAnimator animator = view.animate().translationXBy(-transX);
            animator.setDuration(DURATION);
            animator.setInterpolator(new LinearInterpolator());
            animator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    FLDanMuView.this.removeView(view);
                }
            });
            animator.start();

            //(1.0f * view.getMeasuredWidth() / transX * DURATION) 计算view从屏幕右边全部滑出所需时间
            int delayTime = (int) ((1.0f * view.getMeasuredWidth() / transX * DURATION) + DELAY_DURATION);
            mHandler.sendEmptyMessageDelayed(row, delayTime);

            mCurrentIndex++;
            if (mCurrentIndex == danMuList.size()) {
                mCurrentIndex = 0;
            }
            if (isWorking()) {
                //只剩10条的时候 加载更多
                if (danMuList.size() > 10) {
                    if (danMuList.size() - mCurrentIndex == 10) {
                        if (mListener != null)
                            mListener.onLoadMore();
                    }
                } else {//小于10条的时候，加载最后一条的时候加载更多
                    if (mCurrentIndex == danMuList.size() - 1) {
                        if (mListener != null)
                            mListener.onLoadMore();
                    }
                }
            }
        }
    };

    private View createDanMuView(int row) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.lib_fl_item_dan_mu, this, false);
        TextView tvContent = (TextView) view.findViewById(R.id.fl_dan_mu_content);
        tvContent.setText(danMuList.get(mCurrentIndex).getContent());
        ImageView ivAvatar = (ImageView) view.findViewById(R.id.fl_dan_mu_avatar);

        view.measure(0, 0);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        switch (row) {
            case 0:
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case 1:
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case 2:
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;

        }
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.rightMargin = -view.getMeasuredWidth();
        view.setLayoutParams(lp);
        this.addView(view);
        return view;
    }

    /**
     * 用户发送弹幕
     *
     * @param danMu 弹幕内容
     */
    public void insertDanMu(FLDanMuBean danMu) {
        if (danMu != null) {
            danMuList.add(mCurrentIndex, danMu);
            if (!mHandler.hasMessages(0) && !mHandler.hasMessages(1) && !mHandler.hasMessages(2)) {
                mHandler.sendEmptyMessage(0);
            }
        }
    }

    /**
     * 联网获取更多弹幕
     *
     * @param list 弹幕集合
     */
    public void loadMoreDanMu(List<FLDanMuBean> list) {
        danMuList.addAll(list);
    }

    public boolean isWorking() {
        return mIsWorking;
    }

    public void start() {
        switchAnimation(DanMuAction.SHOW);
        if (isFirst && !danMuList.isEmpty()) {
            if (danMuList.size() > 0)
                mHandler.sendEmptyMessage(0);
            if (danMuList.size() > 1)
                mHandler.sendEmptyMessageDelayed(1, 2000);
            if (danMuList.size() > 2)
                mHandler.sendEmptyMessageDelayed(2, 1000);
            isFirst = false;
        }
        mIsWorking = true;
    }

    public void hide() {
        switchAnimation(DanMuAction.HIDE);
        mIsWorking = false;
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
        mIsWorking = false;
    }

    private void switchAnimation(final DanMuAction action) {
        AlphaAnimation animation;
        if (action == DanMuAction.HIDE) {
            animation = new AlphaAnimation(1.0f, 0.0f);
            animation.setDuration(200);
        } else {
            animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(200);
        }
        FLDanMuView.this.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (action == DanMuAction.HIDE) {
                    FLDanMuView.this.setVisibility(View.INVISIBLE);
                } else {
                    FLDanMuView.this.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private int getScreenWidth() {
        WindowManager mWm = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        mWm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


    private OnLoadMoreListener mListener;

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}

