package com.ifenglian.commonlib.widget.pulltorefresh.recyclerview.defaultview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.widget.pulltorefresh.recyclerview.base.BaseLoadMoreView;

/**
 * 文 件 名: DefaultLoadMoreView
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofy@ifenglian.com
 */
public class DefaultLoadMoreView extends BaseLoadMoreView {

    private TextView tvNoData;
    private LinearLayout llLoadMore;

    public DefaultLoadMoreView(Context context) {
        super(context);
    }

    @Override
    public void initView(Context context) {
        mContainer = LayoutInflater.from(context).inflate(R.layout.layout_default_loading_more, null);
        addView(mContainer);
        setGravity(Gravity.CENTER);
        tvNoData = mContainer.findViewById(R.id.no_data);
        llLoadMore = mContainer.findViewById(R.id.loadMore_Ll);
    }

    @Override
    public void setState(int state) {
        this.setVisibility(VISIBLE);
        switch (state) {
            case STATE_LOADING:
                llLoadMore.setVisibility(VISIBLE);
                tvNoData.setVisibility(INVISIBLE);
                break;
            case STATE_COMPLETE:
                this.setVisibility(GONE);
                break;
            case STATE_NO_DATA:
                llLoadMore.setVisibility(GONE);
                tvNoData.setVisibility(VISIBLE);
                break;
        }
        mState = state;

    }

    @Override
    public void destroy() {
    }

}
