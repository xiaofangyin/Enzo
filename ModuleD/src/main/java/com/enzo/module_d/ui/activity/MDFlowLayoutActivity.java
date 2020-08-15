package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.flowlayout.FlowLayoutAdapter;
import com.enzo.commonlib.widget.flowlayout.FlowLayoutScrollView;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDFlowLayoutActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDFlowLayoutActivity extends BaseActivity {

    private FlowLayoutScrollView flowLayout;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_flow_layout;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("流式布局");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        flowLayout = findViewById(R.id.mc_flow_layout);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<String> flowList = buildData();
        flowLayout.setAdapter(new FlowLayoutAdapter<String>(flowList) {
            @Override
            public void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, String bean) {
                holder.setText(R.id.tv, bean);
            }

            @Override
            public void onItemClick(int position, String bean) {
                if (position == 0) {
                    remove(position);
                }
                ToastUtil.show(bean);
            }

            @Override
            public void onItemLongClick(int position, String bean) {

            }

            @Override
            public int getItemLayoutID(int position, String bean) {
                if (position % 2 == 0) {
                    return R.layout.md_item_flow_layout2;
                }
                return R.layout.md_item_flow_layout;
            }
        });
    }

    @Override
    public void initListener() {

    }

    private List<String> buildData() {
        ArrayList<String> flowList = new ArrayList();
        flowList.add("11111");
        flowList.add("22222222");
        flowList.add("333333");
        flowList.add("444444444444444");
        flowList.add("5555555555");
        flowList.add("66666");
        flowList.add("7777777777777");
        flowList.add("88888888888888");
        flowList.add("9999999999999999");
        flowList.add("66666");
        flowList.add("33333333");
        flowList.add("11111");
        flowList.add("22222222");
        flowList.add("333333");
        flowList.add("444444444444444");
        flowList.add("55555555555555");
        flowList.add("66666666666");
        flowList.add("77777777");
        flowList.add("88888888888");
        flowList.add("9999999999999");
        flowList.add("6666666");
        flowList.add("33333333");
        flowList.add("11111");
        flowList.add("22222222");
        flowList.add("333333");
        flowList.add("4444444444444444");
        flowList.add("5555555");
        flowList.add("66666");
        flowList.add("77777777");
        flowList.add("8888888888888888");
        flowList.add("9999999999999");
        flowList.add("66666");
        flowList.add("33333333");
        flowList.add("6666666");
        flowList.add("33333333");
        flowList.add("11111");
        flowList.add("22222222");
        flowList.add("333333");
        flowList.add("4444444444444444");
        flowList.add("5555555");
        flowList.add("66666");
        flowList.add("77777777");
        flowList.add("8888888888888888");
        flowList.add("9999999999999");
        flowList.add("66666");
        flowList.add("33333333");
        flowList.add("1111111111111111");
        flowList.add("22222222");
        flowList.add("333333");
        flowList.add("444444444444444");
        flowList.add("5555555555");
        flowList.add("66666");
        flowList.add("77777777");
        flowList.add("88888888888");
        flowList.add("9999999999999");
        flowList.add("66666");
        flowList.add("3333333333333333333");
        return flowList;
    }
}
