package com.ifenglian.module_d.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.activity.MDLoadingLayoutActivity;
import com.ifenglian.module_d.activity.MDPhotosActivity;
import com.ifenglian.module_d.activity.MDSimpleActivity;

/**
 * 文 件 名: MDViewPagerFragment2
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment2 extends BaseFragment implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d2;
    }

    @Override
    public void initView(View rootView) {
        rootView.findViewById(R.id.btn_lottie).setOnClickListener(this);
        rootView.findViewById(R.id.btn_album).setOnClickListener(this);
        rootView.findViewById(R.id.btn_loading_layout).setOnClickListener(this);
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
        if (id == R.id.btn_lottie) {
            Intent intent = new Intent(getContext(), MDSimpleActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_album) {
            Intent intent = new Intent(getContext(), MDPhotosActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_loading_layout) {
            Intent intent = new Intent(getContext(), MDLoadingLayoutActivity.class);
            startActivity(intent);
        }
    }
}
