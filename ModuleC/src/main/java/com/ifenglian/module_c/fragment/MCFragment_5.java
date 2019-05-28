package com.ifenglian.module_c.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.horizontal.HorizontalScrollView;
import com.ifenglian.module_c.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MCFragment_5
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_5 extends BaseFragment {

    private HorizontalScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_5;
    }

    @Override
    public void initView(View rootView) {
        mScrollView = rootView.findViewById(R.id.scroll_view);
        mRecyclerView = rootView.findViewById(R.id.recycler);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapter = new CardAdapter());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add("");
        }
        mAdapter.setNewData(list);
    }

    @Override
    public void initListener(View rootView) {
        mScrollView.setOnReleaseListener(new HorizontalScrollView.OnReleaseListener() {
            @Override
            public void onRelease() {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("");
                }
                mAdapter.setLoadMore(list);
                Toast.makeText(getContext(), "触发了查看更多回调接口", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<String> mData;

        CardAdapter() {
            mData = new ArrayList<>();
        }

        void setNewData(List<String> list) {
            mData.clear();
            if (list != null) {
                mData.addAll(list);
            }
            notifyDataSetChanged();
        }

        void setLoadMore(List<String> list) {
            mData.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mc_mei_item_card, parent, false);
            return new CardHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class CardHolder extends RecyclerView.ViewHolder {

            CardHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
