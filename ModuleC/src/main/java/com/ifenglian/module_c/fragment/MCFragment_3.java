package com.ifenglian.module_c.fragment;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.flowlayout.FlowLayout;
import com.enzo.commonlib.widget.flowlayout.FlowLayoutAdapter;
import com.ifenglian.module_c.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MCFragment_3
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_3 extends BaseFragment {

    private FlowLayout flowLayout;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_3;
    }

    @Override
    public void initView(View rootView) {
        flowLayout = rootView.findViewById(R.id.mc_flow_layout);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<String> flowList = new ArrayList();
        flowList.add("11111");
        flowList.add("22222222");
        flowList.add("333333");
        flowList.add("444444444444444");
        flowList.add("5555555555");
        flowList.add("66666");
        flowList.add("77777777");
        flowList.add("88888888888");
        flowList.add("9999999999999");
        flowList.add("66666");
        flowList.add("33333333");
        FlowLayoutAdapter flowLayoutAdapter = new FlowLayoutAdapter<String>(flowList) {
            @Override
            public void bindDataToView(ViewHolder holder, int position, String bean) {
                holder.setText(R.id.tv, bean);
            }

            @Override
            public void onItemClick(int position, String bean) {

            }

            @Override
            public int getItemLayoutID(int position, String bean) {
                return R.layout.mc_item_flow_layout;
            }
        };
        flowLayout.setAdapter(flowLayoutAdapter);
    }

    @Override
    public void initListener(View rootView) {

    }
}
