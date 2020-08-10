package com.enzo.module_a.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;
import com.enzo.module_a.R;
import com.enzo.module_a.model.MAHomeBannerBean;
import com.enzo.module_a.model.MAHomeBaseBean;
import com.enzo.module_a.ui.adapter.MAHomeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MAHomeSubFragment2 extends BaseFragment {

    private PullToRefreshRecyclerView recyclerView;
    private MAHomeAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.ma_fragment_sub_2;
    }

    @Override
    public void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.ma_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadMoreEnabled(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        adapter = new MAHomeAdapter();
        recyclerView.setAdapter(adapter);

        List<MAHomeBaseBean> list = buildData(10, true);
        adapter.setNewData(list);
    }

    @Override
    public void initListener(View rootView) {
        recyclerView.setOnLoadListener(new PullToRefreshRecyclerView.OnLoadListener() {
            @Override
            public void onRecyclerViewRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewData(buildData(10, true));
                        recyclerView.refreshSuccess();
                    }
                }, 3000);
            }

            @Override
            public void onRecyclerViewLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setLoadMoreData(buildData(10, false));
                        recyclerView.loadMoreSuccess();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMoreRetry() {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @NotNull
    private List<MAHomeBaseBean> buildData(int i2, boolean addBanner) {
        List<MAHomeBaseBean> list = new ArrayList<>();
        if (addBanner) {
            list.add(new MAHomeBannerBean(1));
        }
        for (int i = 0; i < i2; i++) {
            list.add(new MAHomeBaseBean(0));
        }
        return list;
    }
}
