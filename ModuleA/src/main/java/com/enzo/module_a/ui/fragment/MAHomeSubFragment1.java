package com.enzo.module_a.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.module_a.R;

public class MAHomeSubFragment1 extends BaseFragment {

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("MAHomeSubFragment1 onPause...");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("MAHomeSubFragment1 onResume...");
    }

    @Override
    public int getLayoutId() {
        return R.layout.ma_fragment_sub_1;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View rootView) {

    }
}
