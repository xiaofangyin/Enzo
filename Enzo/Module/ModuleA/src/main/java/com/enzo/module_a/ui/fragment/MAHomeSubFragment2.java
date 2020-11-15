package com.enzo.module_a.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.loadinglayout.LoadingLayout;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;
import com.enzo.module_a.R;
import com.enzo.module_a.model.bean.MAHomeBannerBean;
import com.enzo.module_a.model.bean.MAHomeBaseBean;
import com.enzo.module_a.model.bean.MAHomeGoodsBean;
import com.enzo.module_a.model.loader.PhotosLoader;
import com.enzo.module_a.ui.adapter.MAHomeAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.functions.Action1;

public class MAHomeSubFragment2 extends BaseFragment {

    private LoadingLayout loadingLayout;
    private PullToRefreshRecyclerView recyclerView;
    private MAHomeAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.ma_fragment_sub_2;
    }

    @Override
    public void initView(View rootView) {
        loadingLayout = rootView.findViewById(R.id.loading_layout);
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

        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadMoreEnabled(true);
    }

    @Override
    public void initListener(View rootView) {
        loadingLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhotoList();
            }
        });
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

        getPhotoList();
    }

    private void getPhotoList() {
        PhotosLoader photosLoader = new PhotosLoader();
        photosLoader.getPhotos(new Random().nextInt(100), 20)
                .subscribe(new Action1<List<MAHomeGoodsBean>>() {
                    @Override
                    public void call(List<MAHomeGoodsBean> response) {
                        List<MAHomeBaseBean> list = new ArrayList<>();
                        list.add(new MAHomeBannerBean(MAHomeAdapter.TYPE_BANNER));
                        list.addAll(new ArrayList<>(response));
                        adapter.setNewData(list);
                        loadingLayout.showContent();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void getPhotoList(final boolean pullRefresh) {
        PhotosLoader photosLoader = new PhotosLoader();
        photosLoader.getPhotos(new Random().nextInt(100), 20)
                .subscribe(new Action1<List<MAHomeGoodsBean>>() {
                    @Override
                    public void call(List<MAHomeGoodsBean> response) {
                        if (pullRefresh) {
                            List<MAHomeBaseBean> list = new ArrayList<>();
                            list.add(new MAHomeBannerBean(MAHomeAdapter.TYPE_BANNER));
                            list.addAll(new ArrayList<>(response));
                            adapter.setNewData(list);
                            recyclerView.refreshSuccess();
                        } else {
                            adapter.setLoadMoreData(new ArrayList<MAHomeBaseBean>(response));
                            recyclerView.loadMoreSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (pullRefresh) {
                            recyclerView.refreshFailed();
                        } else {
                            recyclerView.loadMoreFailed();
                        }
                    }
                });
    }
}
