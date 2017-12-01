package com.ifenglian.commonlib.widget.view.recyclerview.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 文 件 名: SpaceItemDecoration
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class ChildSpaceItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        WindowManager manager = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        view.getLayoutParams().width = dm.widthPixels * 7 / 8;
    }
}
