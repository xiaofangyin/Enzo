package com.enzo.main.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.loadinglayout.LoadingLayout;
import com.enzo.commonlib.widget.pulltorefresh.recyclerview.PullToRefreshRecyclerView;
import com.enzo.flkit.FLPluginBaseObject;
import com.enzo.flkit.FLPluginBaseObjectDelegate;
import com.enzo.flkit.FLPluginFactory;
import com.enzo.flkit.FLPluginTypeList;
import com.enzo.main.R;
import com.enzo.main.plugin.SAFactoryManager;
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

    @Override
    public int getLayoutId() {
        return R.layout.sa_activity_add_device;
    }

    @Override
    public void initHeader() {
        HeadWidget headWidget = findViewById(R.id.add_device_header);
        headWidget.setTitle("添加设备");
        headWidget.setRightImage(R.mipmap.main_add_icon);
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        headWidget.setRightImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<JSONObject> list = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
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
                adapter.getData().addAll(1, getObjectList(list));
                adapter.notifyItemRangeInserted(1, list.size());
//                adapter.getData().get(3).alias = "123456";
//                adapter.notifyItemChanged(3);
            }
        });
    }

    @Override
    public void initView() {
        loadingLayout = findViewById(R.id.add_device_loading_layout);
        recyclerView = findViewById(R.id.add_device_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SAAddDeviceActivity.this));
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadMoreEnabled(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                loadingLayout.showContent();
                adapter = new SAAddDeviceAdapter();
                recyclerView.setAdapter(adapter);
                adapter.setNewData(getObjectList(buildData()));
            }
        }, 2000);
    }

    @Override
    public void initListener() {
        recyclerView.setOnLoadListener(new PullToRefreshRecyclerView.OnLoadListener() {
            @Override
            public void onRecyclerViewRefresh() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewData(getObjectList(buildData()));
                        recyclerView.refreshSuccess();
                    }
                }, 3000);
            }

            @Override
            public void onRecyclerViewLoadMore() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setLoadMoreData(getObjectList(buildData()));
                        recyclerView.loadMoreSuccess();
                    }
                }, 2500);
            }

            @Override
            public void onLoadMoreRetry() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setLoadMoreData(getObjectList(buildData()));
                        recyclerView.loadMoreSuccess();
                    }
                }, 2500);
            }
        });
    }

    private List<FLPluginBaseObject> getObjectList(List<JSONObject> dataList) {
        List<FLPluginFactory> factories = SAFactoryManager.getInstance().getFactoryList();
        List<FLPluginBaseObject> baseObjects = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            for (int j = 0; j < factories.size(); j++) {
                FLPluginBaseObject baseObject = factories.get(j).buildNormalPluginCellModel(dataList.get(i));
                if (baseObject != null) {
                    baseObject.delegate = this;
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
            for (int i = 0; i < 20; i++) {
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
    public void finish() {
        super.finish();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            adapter.getData().get(i).release();
        }
    }
}
