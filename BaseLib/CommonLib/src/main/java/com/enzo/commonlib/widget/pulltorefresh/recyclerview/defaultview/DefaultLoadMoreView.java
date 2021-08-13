package com.enzo.commonlib.widget.pulltorefresh.recyclerview.defaultview;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enzo.commonlib.R;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.base.BaseLoadMoreView;

/**
 * 文 件 名: DefaultLoadMoreView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofangyinwork@163.com
 */
public class DefaultLoadMoreView extends BaseLoadMoreView {

    private LinearLayout llLoadingLayout;
    private TextView tvNoData, tvLoadFailed;

    public DefaultLoadMoreView(Context context) {
        super(context);
    }

    @Override
    public void initView(Context context) {
        mContainer = LayoutInflater.from(context).inflate(R.layout.lib_layout_default_loading_more, null);
        addView(mContainer);
        setGravity(Gravity.CENTER);
        llLoadingLayout = mContainer.findViewById(R.id.ll_loading_layout);
        tvNoData = mContainer.findViewById(R.id.tv_load_no_data);
        tvLoadFailed = mContainer.findViewById(R.id.tv_load_failed);
    }

    @Override
    public void setState(int state) {
        Log.e("AAA", "DefaultLoadMoreView setState: " + state);
        this.setVisibility(View.VISIBLE);
        switch (state) {
            case STATE_LOADING:
                llLoadingLayout.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);
                tvLoadFailed.setVisibility(View.GONE);
                break;
            case STATE_SUCCESS:
                break;
            case STATE_FAILED:
                llLoadingLayout.setVisibility(View.GONE);
                tvNoData.setVisibility(View.GONE);
                tvLoadFailed.setVisibility(View.VISIBLE);
                tvLoadFailed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mRetryListener != null) {
                            mRetryListener.onLoadMoreRetry();
                        }
                    }
                });
                break;
            case STATE_NO_DATA:
                llLoadingLayout.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
                tvLoadFailed.setVisibility(View.GONE);
                break;
        }
        mState = state;
    }

    @Override
    public void destroy() {

    }
}
