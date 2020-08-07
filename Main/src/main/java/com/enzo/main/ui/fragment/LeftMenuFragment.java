package com.enzo.main.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.imageloader.ImageLoader;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.roundimageview.RoundImageView;
import com.enzo.main.R;
import com.enzo.main.config.LeftMenuConfig;
import com.enzo.main.model.LeftMenuParentBean;
import com.enzo.main.ui.activity.SAMainActivity;
import com.enzo.main.ui.adapter.LeftMenuParentAdapter;
import com.enzo.main.util.SPUtils;

import java.util.Iterator;
import java.util.List;

/**
 * 文 件 名: LeftMenuFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class LeftMenuFragment extends BaseFragment {

    private ImageLoader.Builder builder;
    private RoundImageView ivUserIcon;
    private TextView tvUserName;
    private RecyclerView recyclerView;
    private LeftMenuParentAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.main_layout_home_drawer_layout;
    }

    @Override
    public void initView(View rootView) {
        ivUserIcon = rootView.findViewById(R.id.left_menu_user_icon);
        tvUserName = rootView.findViewById(R.id.left_menu_user_name);
        recyclerView = rootView.findViewById(R.id.left_menu_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        builder = new ImageLoader.Builder(getActivity());
        adapter = new LeftMenuParentAdapter();
        recyclerView.setAdapter(adapter);
        setFunctionList();
    }

    @Override
    public void initListener(View rootView) {
        rootView.findViewById(R.id.left_menu_tv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.show("设置");
            }
        });
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
            adapter.setNewData(LeftMenuConfig.getMenuList());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    setFunctionList();
                }
        }
    }

    private void closeDrawer() {
        if (getActivity() != null) {
            ((SAMainActivity) getActivity()).closeDrawer(Gravity.START);
        }
    }
}
