package com.enzo.commonlib.widget.pulltorefresh.recyclerview.defaultview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enzo.commonlib.R;
import com.enzo.commonlib.widget.avi.AVLoadingIndicatorView;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerViewUtils;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.base.BasePullToRefreshView;
import com.nineoldandroids.view.ViewHelper;

/**
 * 文 件 名: DefaultRefreshHeaderView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofangyinwork@163.com
 */
public class DefaultRefreshHeaderView extends BasePullToRefreshView implements BasePullToRefreshView.OnStateChangeListener {

    private static final int ROTATE_DURATION = 180;
    private String tag = "";
    private LinearLayout mRefreshContainer;//刷新时间布局
    private ImageView ivArrow;
    private TextView tvRefreshState;
    private AVLoadingIndicatorView progressView;
    private TextView tvLastRefreshTime;

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
        mRefreshContainer = mContainer.findViewById(R.id.refresh_time_container);

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
        tvLastRefreshTime = mContainer.findViewById(R.id.last_refresh_time);

        rotateAnimator = ObjectAnimator.ofFloat(ivArrow, "rotation", 0, -180);
        rotateAnimator.setDuration(ROTATE_DURATION);

        //测量高度
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();

        tvLastRefreshTime.setText(PullToRefreshRecyclerViewUtils.getTimeConvert(PullToRefreshRecyclerViewUtils.getLastRefreshTime(context, tag)));
    }

    @Override
    public void setRefreshTimeVisible(String tag) {
        if (mRefreshContainer != null) {
            this.tag = tag;
            mRefreshContainer.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onRefreshTime() {
        if (mState == STATE_PULL_DOWN) {
            //时间更新
            if (!TextUtils.isEmpty(tag)) {
                String time = PullToRefreshRecyclerViewUtils.getTimeConvert(PullToRefreshRecyclerViewUtils.getLastRefreshTime(context, tag));
                tvLastRefreshTime.setText(time);
            }
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
                if (ViewHelper.getRotation(ivArrow) != 0) {
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
                if (ViewHelper.getRotation(ivArrow) != 0) {
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
                //时间更新
                long currentTime = System.currentTimeMillis();
                tvLastRefreshTime.setText(PullToRefreshRecyclerViewUtils.getTimeConvert(currentTime));
                PullToRefreshRecyclerViewUtils.saveLastRefreshTime(context, tag, currentTime);

                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.setImageResource(R.mipmap.refresh_succeed);
                ViewHelper.setRotation(ivArrow, 0);

                if (progressView != null) {
                    progressView.setVisibility(View.GONE);
                }
                tvRefreshState.setText(R.string.collection_refresh_done);
                break;
            case STATE_FAILED:
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.setImageResource(R.mipmap.refresh_failed);
                ViewHelper.setRotation(ivArrow, 0);

                if (progressView != null) {
                    progressView.setVisibility(View.GONE);
                }
                tvRefreshState.setText(R.string.collection_refresh_failed);
                break;
        }
        mState = state;
    }
}
