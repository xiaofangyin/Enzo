package com.enzo.main.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.loadinglayout.LoadingLayout;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginBaseObjectDelegate;
import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.flkit.router.MainRouterPath;
import com.enzo.main.R;
import com.enzo.main.plugin.SAPluginManager;
import com.enzo.main.ui.adapter.SAAddDeviceAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: SAAddDeviceActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAAddDeviceActivity extends BaseActivity implements FLPluginBaseObjectDelegate {

    private LoadingLayout loadingLayout;
    private PullToRefreshRecyclerView recyclerView;
    private SAAddDeviceAdapter adapter;
    private HeadWidget headWidget;

    @Override
    public int getLayoutId() {
        return R.layout.main_activity_add_device;
    }

    @Override
    public void initHeader() {
        headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("添加设备");
        headWidget.setRightImage(R.mipmap.main_add_icon);
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        headWidget.setRightImageVisible(false);
        headWidget.setRightImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<JSONObject> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    JSONObject object4 = new JSONObject();
                    try {
                        object4.put("type", FLPluginTypeList.FL_DEVICE_TYPE_D);
                        object4.put("alias", "扩展器 " + i);
                        object4.put("rid", "400000000" + i);
                        list.add(object4);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (adapter != null) {
                    adapter.getData().addAll(1, getObjectList(list));
                    adapter.notifyItemRangeInserted(1, list.size());
                }
            }
        });
    }

    @Override
    public void initView() {
        loadingLayout = findViewById(R.id.add_device_loading_layout);
        recyclerView = findViewById(R.id.add_device_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SAAddDeviceActivity.this));
//        recyclerView.setLayoutManager(new GridLayoutManager(SAAddDeviceActivity.this, 3));

        DividerItemDecoration mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadMoreEnabled(true);

//        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                layoutManager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
//            }
//        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        adapter = new SAAddDeviceAdapter();
        recyclerView.setAdapter(adapter);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                headWidget.setRightImageVisible(true);
                loadingLayout.showContent();
                adapter.setNewData(getObjectList(buildData()));
            }
        }, 4000);
    }

    @Override
    public void initListener() {
        loadingLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        headWidget.setRightImageVisible(true);
                        loadingLayout.showContent();
                        adapter.setNewData(getObjectList(buildData()));
                    }
                }, 4000);
            }
        });
        recyclerView.setOnMultiPurposeListener(new PullToRefreshRecyclerView.SimpleMultiPurposeListener() {
            @Override
            public void onRecyclerViewRefresh() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewData(getObjectList(buildData()));
                        recyclerView.refreshSuccess();
                    }
                }, 4000);
            }

            @Override
            public void onRecyclerViewLoadMore() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setLoadMoreData(getObjectList(buildData()));
                        recyclerView.loadMoreSuccess();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMoreRetry() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setLoadMoreData(getObjectList(buildData()));
                        recyclerView.loadMoreSuccess();
                    }
                }, 1000);
            }
        });
    }

    private List<FLPluginBaseObject> getObjectList(List<JSONObject> dataList) {
        List<FLPluginInterface> factories = SAPluginManager.getInstance().getPluginList();
        List<FLPluginBaseObject> baseObjects = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            for (int j = 0; j < factories.size(); j++) {
                FLPluginBaseObject baseObject = factories.get(j).buildNormalPluginCellModel(this, dataList.get(i));
                if (baseObject != null) {
                    baseObjects.add(baseObject);
                    break;
                }
            }
        }
        return baseObjects;
    }

    private List<JSONObject> buildData() {
        List<JSONObject> list = new ArrayList<>();
        try {
            for (int i = 0; i < 12; i++) {
                if (i % 4 == 0) {
                    JSONObject object1 = new JSONObject();
                    object1.put("type", FLPluginTypeList.FL_DEVICE_TYPE_A);
                    object1.put("alias", "路由器 " + i);
                    object1.put("rid", "100000000" + i);
                    list.add(object1);
                } else if (i % 4 == 1) {
                    JSONObject object2 = new JSONObject();
                    object2.put("type", FLPluginTypeList.FL_DEVICE_TYPE_B);
                    object2.put("alias", "智能夜灯 " + i);
                    object2.put("rid", "200000000" + i);
                    list.add(object2);
                } else if (i % 4 == 2) {
                    JSONObject object3 = new JSONObject();
                    object3.put("type", FLPluginTypeList.FL_DEVICE_TYPE_C);
                    object3.put("alias", "空气盒子 " + i);
                    object3.put("rid", "300000000" + i);
                    list.add(object3);
                } else if (i % 4 == 3) {
                    JSONObject object4 = new JSONObject();
                    object4.put("type", FLPluginTypeList.FL_DEVICE_TYPE_D);
                    object4.put("alias", "扩展器 " + i);
                    object4.put("rid", "400000000" + i);
                    list.add(object4);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Context getContext() {
        return SAAddDeviceActivity.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recyclerView != null) {
            recyclerView.destroy();
        }
        if (adapter != null) {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                adapter.getData().get(i).release();
            }
        }
    }
}
