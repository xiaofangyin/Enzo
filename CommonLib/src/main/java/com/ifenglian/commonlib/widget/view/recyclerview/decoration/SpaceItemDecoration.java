package com.ifenglian.commonlib.widget.view.recyclerview.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 文 件 名: SpaceItemDecoration
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
    }
}
