package com.ifenglian.module_d.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.activity.MDNavigationActivity;

/**
 * 文 件 名: NavigationFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class NavigationFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.md_navigation_fragment;
    }

    @Override
    public void initView(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new TestAdapter());
        recyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new ListScrollListener());
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View rootView) {

    }

    private static class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int padding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 16, parent.getResources().getDisplayMetrics());
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(padding, padding, padding, padding);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            return new RecyclerView.ViewHolder(textView) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if (holder.itemView instanceof TextView) {
                ((TextView) holder.itemView).setText(String.valueOf(position));
            }
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    /**
     * 监听列表的滑动来控制底部导航栏的显示与隐藏
     */
    private class ListScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 8) {//列表向上滑动
                ((MDNavigationActivity) NavigationFragment.this.getActivity()).showTabLayout(false);
            } else if (dy < -8) {//列表向下滑动
                ((MDNavigationActivity) NavigationFragment.this.getActivity()).showTabLayout(true);
            }
        }
    }
}
