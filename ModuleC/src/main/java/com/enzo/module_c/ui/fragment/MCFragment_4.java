package com.enzo.module_c.ui.fragment;

import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.module_c.R;

/**
 * 文 件 名: MCFragment_4
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_4 extends BaseFragment {

    private LottieAnimationView animationView;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_4;
    }

    @Override
    public void initView(View rootView) {
        animationView = rootView.findViewById(R.id.lottieAnimationView);
    }

    @Override
    public void initListener(View rootView) {

    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
        animationView.playAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        animationView.pauseAnimation();
    }
}
