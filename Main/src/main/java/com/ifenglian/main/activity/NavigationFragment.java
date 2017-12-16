package com.ifenglian.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.main.R;

/**
 * 文 件 名: NavigationFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class NavigationFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.md_navigation_fragment;
    }

    @Override
    public void initView(View rootView) {
        Bundle bundle = getArguments();
        TextView tvContent = rootView.findViewById(R.id.tv_content);
        tvContent.setText(String.valueOf(bundle.getInt("number")));
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View rootView) {

    }
}
