package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;
import com.enzo.module_d.model.bean.Star;
import com.enzo.module_d.ui.adapter.MDStarAdapter;
import com.enzo.module_d.ui.decoration.MDStarDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MDGroupItemDecorationActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/10/15
 * 邮   箱: xiaofywork@163.com
 */
public class MDGroupItemDecorationActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("分组");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.md_activity_group_item;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new MDStarDecoration(this));

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<Star> starList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 20; j++) {
                if (i % 2 == 0) {
                    starList.add(new Star("何炅" + j, "快乐家族" + i));
                } else {
                    starList.add(new Star("汪涵" + j, "天天兄弟" + i));
                }
            }
        }
        recyclerView.setAdapter(new MDStarAdapter(starList));
    }

    @Override
    public void initListener() {

    }

}
