package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.boheruler.RulerView;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDRulerActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDRulerActivity extends BaseActivity {

    private TextView tvHeight;
    private RulerView rulerView;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_ruler;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("尺子");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        tvHeight = findViewById(R.id.supply_height_current_height);
        rulerView = findViewById(R.id.supply_height_ruler);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvHeight.setText(String.valueOf(rulerView.getValue()));
        rulerView.setLineColor(ContextCompat.getColor(rulerView.getContext(), R.color.color_yellow));
        rulerView.setTextColor(ContextCompat.getColor(rulerView.getContext(), R.color.color_yellow));
    }

    @Override
    public void initListener() {
        rulerView.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvHeight.setText(String.valueOf(value));
            }
        });
    }

}
