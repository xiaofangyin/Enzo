package com.enzo.commonlib.widget.pulltorefresh.recyclerview.defaultview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.avi.AVLoadingIndicatorView;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.base.BasePullToRefreshView;

/**
 * 文 件 名: DefaultRefreshHeaderView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofangyinwork@163.com
 */
public class DefaultRefreshHeaderView extends BasePullToRefreshView implements BasePullToRefreshView.OnStateChangeListener {

    private static final int ROTATE_DURATION = 180;
    private String tag = "";
    private ImageView ivArrow;
    private TextView tvRefreshState;
    private AVLoadingIndicatorView progressView;

    private Context context;
    private ObjectAnimator rotateAnimator;

    public DefaultRefreshHeaderView(Context context) {
        super(context);
        setOnStateChangeListener(this);
    }

    /**
     * 初始化HearView
     */
    @Override
    public void initView(Context context) {
        this.context = context;
        mContainer = LayoutInflater.from(context).inflate(R.layout.lib_layout_default_arrow_refresh, null);

        //把刷新头部的高度初始化为0
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);
        addView(mContainer, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);

        ivArrow = mContainer.findViewById(R.id.refresh_arrow);
        tvRefreshState = mContainer.findViewById(R.id.refresh_status_tv);
        progressView = mContainer.findViewById(R.id.av_progressbar);

        rotateAnimator = ObjectAnimator.ofFloat(ivArrow, "rotation", 0, -180);
        rotateAnimator.setDuration(ROTATE_DURATION);

        //测量高度
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    @Override
    public void onPullDown() {
        if (mState == STATE_PULL_DOWN) {
            LogUtil.d("header view pull down...");
        }
    }

    @Override
    public void destroy() {
        if (progressView != null) {
            progressView = null;
        }
    }

    @Override
    public void onStateChange(int state) {
        //下拉时状态相同不做继续保持原有的状态
        if (state == mState) return;

        //根据状态进行动画显示
        switch (state) {
            case STATE_PULL_DOWN:
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.setImageResource(R.mipmap.icon_refresh_arrow);
                if (ivArrow.getRotation() != 0) {
                    rotateAnimator.reverse();
                }

                tvRefreshState.setText(R.string.collection_pull_to_refresh);
                if (progressView != null) {
                    progressView.setVisibility(View.GONE);
                }
                break;
            case STATE_RELEASE_REFRESH:
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.setImageResource(R.mipmap.icon_refresh_arrow);
                rotateAnimator.start();

                tvRefreshState.setText(R.string.collection_release_refresh);
                if (progressView != null) {
                    progressView.setVisibility(View.GONE);
                }
                break;
            case STATE_REFRESHING:
                if (ivArrow.getRotation() != 0) {
                    rotateAnimator.reverse();
                }
                ivArrow.setVisibility(View.GONE);

                tvRefreshState.setText(R.string.collection_refreshing);
                if (progressView != null) {
                    progressView.setVisibility(View.VISIBLE);
                }
                scrollTo(mMeasuredHeight);
                break;
            case STATE_SUCCESS:
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.setImageResource(R.mipmap.refresh_succeed);
                ivArrow.setRotation(0);

                if (progressView != null) {
                    progressView.setVisibility(View.GONE);
                }
                tvRefreshState.setText(R.string.collection_refresh_done);
                break;
            case STATE_FAILED:
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.setImageResource(R.mipmap.refresh_failed);
                ivArrow.setRotation(0);

                if (progressView != null) {
                    progressView.setVisibility(View.GONE);
                }
                tvRefreshState.setText(R.string.collection_refresh_failed);
                break;
        }
        mState = state;
    }
}
