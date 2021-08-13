package com.enzo.module_d.ui.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.module_d.ui.adapter.MDStarAdapter;

public class MDStarDecoration extends RecyclerView.ItemDecoration {

    private final int groupHeaderHeight;

    private final Paint headPaint;
    private final Paint textPaint;

    private final Rect textRect;

    public MDStarDecoration(Context context) {
        groupHeaderHeight = dp2px(context, 50);

        headPaint = new Paint();
        headPaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setColor(Color.WHITE);

        textRect = new Rect();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getAdapter() instanceof MDStarAdapter) {
            MDStarAdapter adapter = (MDStarAdapter) parent.getAdapter();
            // 当前屏幕的item个数
            int count = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < count; i++) {
                // 获取对应i的View
                View view = parent.getChildAt(i);
                // 获取View的布局位置
                int position = parent.getChildLayoutPosition(view);
                // 是否是头部
                boolean isGroupHeader = adapter.isGroupHeader(position);
                if (isGroupHeader && view.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0) {
                    c.drawRect(left, view.getTop() - groupHeaderHeight, right, view.getTop(), headPaint);
                    String groupName = adapter.getGroupName(position);
                    textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                    c.drawText(groupName, left + 20, view.getTop() -
                            groupHeaderHeight / 2f + textRect.height() / 2f, textPaint);
                } else if (view.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0) {
                    // 分割线
                    c.drawRect(left, view.getTop() - 4, right, view.getTop(), headPaint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter() instanceof MDStarAdapter) {
            MDStarAdapter adapter = (MDStarAdapter) parent.getAdapter();
            // 返回可见区域内的第一个item的position
            int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            // 获取对应position的View
            View itemView = parent.findViewHolderForAdapterPosition(position).itemView;
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();
            // 当第二个是组的头部的时候
            boolean isGroupHeader = adapter.isGroupHeader(position + 1);
            if (isGroupHeader) {
                int bottom = Math.min(groupHeaderHeight, itemView.getBottom() - parent.getPaddingTop());

                c.drawRect(left, top, right, top + bottom, headPaint);
                String groupName = adapter.getGroupName(position);
                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);

                // 绘制文字的高度不能超出区域
                c.clipRect(left, top, right, top + bottom);

                c.drawText(groupName, left + 20, top + bottom
                        - groupHeaderHeight / 2f + textRect.height() / 2f, textPaint);
            } else {
                c.drawRect(left, top, right, top + groupHeaderHeight, headPaint);
                String groupName = adapter.getGroupName(position);
                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                c.drawText(groupName, left + 20, top + groupHeaderHeight / 2f + textRect.height() / 2f, textPaint);
            }

        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getAdapter() instanceof MDStarAdapter) {
            MDStarAdapter adapter = (MDStarAdapter) parent.getAdapter();
            int position = parent.getChildLayoutPosition(view);
            boolean isGroupHeader = adapter.isGroupHeader(position);
            // 怎么判断 itemView是头部
            if (isGroupHeader) {
                // 如果是头部，预留更大的地方
                outRect.set(0, groupHeaderHeight, 0, 0);
            } else {
                // 1像素
                outRect.set(0, 4, 0, 0);
            }
        }
    }

    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
