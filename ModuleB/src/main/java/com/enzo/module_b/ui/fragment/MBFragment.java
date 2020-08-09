package com.enzo.module_b.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.statusbar.utils.StatusBarUtils;
import com.enzo.commonlib.widget.spiderweb.SpiderWebView;
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

    private SpiderWebView mSpiderWebView;

    @Override
    public int getLayoutId() {
        return R.layout.mb_fragment_b;
    }

    @Override
    public void initView(View rootView) {
        View view = new View(rootView.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                StatusBarUtils.getStatusBarHeight(rootView.getContext()));
        view.setLayoutParams(layoutParams);
        ((ViewGroup)rootView).addView(view,0);

        mSpiderWebView = rootView.findViewById(R.id.spider_web_view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mSpiderWebView.setPointNum(80);//小点数量
        mSpiderWebView.setPointAcceleration(6);//加速度
    }

    @Override
    public void initListener(View rootView) {

    }
}
