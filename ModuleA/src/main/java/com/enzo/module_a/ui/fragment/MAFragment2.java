package com.enzo.module_a.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.statusbar.utils.StatusBarUtils;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;
import com.enzo.flkit.router.ModuleARouterPath;
import com.enzo.module_a.R;
import com.enzo.module_a.model.MAHomeBannerBean;
import com.enzo.module_a.model.MAHomeBaseBean;
import com.enzo.module_a.ui.activity.MAScanQrCodeActivity;
import com.enzo.module_a.ui.adapter.MAHomeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MAFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
@Route(path = ModuleARouterPath.MODULE_A_FRAGMENT2)
public class MAFragment2 extends BaseFragment {

    private TextView tvSearch;
    private PullToRefreshRecyclerView recyclerView;
    private MAHomeAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.ma_fragment_a2;
    }

    @Override
    public void initView(View rootView) {
        View view = new View(rootView.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                StatusBarUtils.getStatusBarHeight(rootView.getContext()));
        view.setLayoutParams(layoutParams);
        ((ViewGroup) rootView).addView(view, 0);

        tvSearch = rootView.findViewById(R.id.ma_search);
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
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvSearch.getLayoutParams();
                layoutParams.leftMargin = layoutParams.leftMargin - dy / 3;
                if (layoutParams.leftMargin >= DensityUtil.dip2px(recyclerView.getContext(), 12)) {
                    tvSearch.setLayoutParams(layoutParams);
                }
            }
        });
        rootView.findViewById(R.id.ma_qr_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MAScanQrCodeActivity.class);
                startActivity(intent);
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
