package com.enzo.module_a.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.net.okhttp.BaseExecutor;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;
import com.enzo.module_a.R;
import com.enzo.module_a.model.bean.MAHomeBannerBean;
import com.enzo.module_a.model.bean.MAHomeBaseBean;
import com.enzo.module_a.model.bean.MAHomeGoodsBean;
import com.enzo.module_a.model.exetutor.MAPhotoListExecutor;
import com.enzo.module_a.ui.adapter.MAHomeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
            }
        });

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadMoreEnabled(true);
    }

    @Override
    public void initListener(View rootView) {
        recyclerView.setOnMultiPurposeListener(new PullToRefreshRecyclerView.SimpleMultiPurposeListener() {
            @Override
            public void onRecyclerViewRefresh() {
                LogUtil.d("1 get photo list...");
                getPhotoList(true);
            }

            @Override
            public void onRecyclerViewLoadMore() {
                LogUtil.d("2 get photo list...");
                getPhotoList(false);
            }

            @Override
            public void onLoadMoreRetry() {
                LogUtil.d("3 get photo list...");
                getPhotoList(false);
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

    @Override
    public void lazyLoad() {
        adapter = new MAHomeAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.autoRefresh();
    }

    private void getPhotoList(final boolean pullRefresh) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(new Random().nextInt(100)));
        params.put("limit", "20");

        new MAPhotoListExecutor()
                .params(params)
                .callback(new BaseExecutor.JsonCallback<List<MAHomeGoodsBean>>() {
                    @Override
                    public void onSuccess(List<MAHomeGoodsBean> response) {
                        if (pullRefresh) {
                            List<MAHomeBaseBean> list = new ArrayList<>();
                            list.add(new MAHomeBannerBean(1));
                            list.addAll(new ArrayList<>(response));
                            adapter.setNewData(list);
                            recyclerView.refreshSuccess();
                        } else {
                            adapter.setLoadMoreData(new ArrayList<MAHomeBaseBean>(response));
                            recyclerView.loadMoreSuccess();
                        }
                    }

                    @Override
                    public void onFailed(Exception e) {
                        if (pullRefresh) {
                            recyclerView.refreshFailed();
                        } else {
                            recyclerView.loadMoreFailed();
                        }
                    }
                })
                .execute();
    }
}
