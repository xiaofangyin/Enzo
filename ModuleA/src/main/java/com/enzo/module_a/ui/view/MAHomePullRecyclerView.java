package com.enzo.module_a.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;

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

    @Override
    public boolean fullSpan(int position) {
        return position == 1;
    }
}
