package com.ifenglian.module_d.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.activity.MDBleActivity;
import com.ifenglian.module_d.activity.MDJniActivity;
import com.ifenglian.module_d.activity.MDViewPagerIndicatorActivity;

/**
 * 文 件 名: MDViewPagerFragment1
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment1 extends BaseFragment implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d1;
    }

    @Override
    public void initView(View rootView) {

        rootView.findViewById(R.id.btn_ble).setOnClickListener(this);
        rootView.findViewById(R.id.btn_jni).setOnClickListener(this);
        rootView.findViewById(R.id.btn_navigation_view).setOnClickListener(this);
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
        if (id == R.id.btn_ble) {
            Intent intent = new Intent(getContext(), MDBleActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_navigation_view) {
            Intent intent = new Intent(getContext(), MDViewPagerIndicatorActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_jni) {
            Intent intent = new Intent(getContext(), MDJniActivity.class);
            startActivity(intent);
        }
    }
}
