package com.enzo.module_c.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.boheruler.RulerView;
import com.enzo.module_c.R;

/**
 * 文 件 名: MCFragment_2
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_2 extends BaseFragment {

    private TextView tvHeight;
    private RulerView rulerView;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_2;
    }

    @Override
    public void initView(View rootView) {
        tvHeight = rootView.findViewById(R.id.supply_height_current_height);
        rulerView = rootView.findViewById(R.id.supply_height_ruler);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvHeight.setText(String.valueOf(rulerView.getValue()));
        rulerView.setLineColor(ContextCompat.getColor(rulerView.getContext(), R.color.color_yellow));
        rulerView.setTextColor(ContextCompat.getColor(rulerView.getContext(), R.color.color_yellow));
    }

    @Override
    public void initListener(View rootView) {
        rulerView.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tvHeight.setText(String.valueOf(value));
            }
        });
    }
}
