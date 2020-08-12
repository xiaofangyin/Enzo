package com.enzo.main.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.main.R;
import com.enzo.main.config.SALeftMenuConfig;
import com.enzo.main.model.bean.LeftMenuParentBean;
import com.enzo.main.ui.adapter.SALeftMenuParentAdapter;
import com.enzo.main.util.SPUtils;

import java.util.Iterator;
import java.util.List;

/**
 * 文 件 名: LeftMenuFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SALeftMenuFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SALeftMenuParentAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.main_layout_home_drawer_layout;
    }

    @Override
    public void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.left_menu_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initListener(View rootView) {
        rootView.findViewById(R.id.left_menu_tv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mode = SPUtils.getDisplayMode(getContext());
                if (mode == 0) {
                    SPUtils.setDisplayMode(getContext(), 1);
                } else {
                    SPUtils.setDisplayMode(getContext(), 0);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void lazyLoad() {
        adapter = new SALeftMenuParentAdapter();
        recyclerView.setAdapter(adapter);
        setFunctionList();
    }

    private void setFunctionList() {
        List<LeftMenuParentBean> cacheList = SPUtils.getFunctionList(getContext());
        if (cacheList != null) {
            Iterator<LeftMenuParentBean> it = cacheList.iterator();
            while (it.hasNext()) {
                LeftMenuParentBean bean = it.next();
                if (!bean.isEnable()) {
                    it.remove();
                }
            }
            adapter.setNewData(cacheList);
        } else {
            adapter.setNewData(SALeftMenuConfig.getMenuList());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                setFunctionList();
            }
        }
    }
}
