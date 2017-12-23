package com.ifenglian.module_d.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.activity.MDRoundImageViewActivity;
import com.ifenglian.module_d.activity.MDRulerActivity;
import com.ifenglian.module_d.activity.MDUpdateVersionActivity;

/**
 * 文 件 名: MDViewPagerFragment3
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment3 extends BaseFragment implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d3;
    }

    @Override
    public void initView(View rootView) {
        rootView.findViewById(R.id.btn_round_image_view).setOnClickListener(this);
        rootView.findViewById(R.id.btn_ruler).setOnClickListener(this);
        rootView.findViewById(R.id.btn_update_version).setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View rootView) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_round_image_view) {
            Intent intent = new Intent(getContext(), MDRoundImageViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_ruler) {
            Intent intent = new Intent(getContext(), MDRulerActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_update_version) {
            Intent intent = new Intent(getContext(), MDUpdateVersionActivity.class);
            startActivity(intent);
        }
    }
}
