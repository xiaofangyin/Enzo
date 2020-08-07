package com.enzo.module_b.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.flkit.router.ModuleARouterPath;
import com.enzo.flkit.router.ModuleBRouterPath;
import com.enzo.module_b.R;

/**
 * 文 件 名: MBFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
@Route(path = ModuleBRouterPath.MODULE_B_FRAGMENT)
public class MBFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.mb_fragment_b;
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
