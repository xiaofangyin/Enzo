package com.enzo.module_a.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;
import com.enzo.module_a.ui.adapter.MAHomeAdapter;

/**
 * 文 件 名: view
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/13
 * 邮   箱: xiaofywork@163.com
 */
public class MAHomePullRecyclerView extends PullToRefreshRecyclerView {

    public MAHomePullRecyclerView(Context context) {
        super(context);
    }

    public MAHomePullRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MAHomePullRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 获取viewHolder两种方式
     * 1.第一次返回null
     * recyclerView.findViewHolderForAdapterPosition(position)
     * 2.这种方式没问题
     * recyclerView.getLayoutManager().findViewByPosition(position)
     * recyclerView.getChildViewHolder(view)
     */
    @Override
    public boolean fullSpan(int position) {
        if (getLayoutManager() != null) {
            View view = getLayoutManager().findViewByPosition(position);
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = getChildViewHolder(view);
                return viewHolder instanceof MAHomeAdapter.HomeBannerHolder;
            }
            return false;
        }
        return false;
    }
}
