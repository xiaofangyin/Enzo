package com.ifenglian.module_d.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.widget.view.waveview.WaveView;
import com.ifenglian.module_d.R;

/**
 * 文 件 名: MDViewPagerFragment3
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment3 extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d3;
    }

    @Override
    public void initView(View rootView) {
        final ImageView ivAvatar = rootView.findViewById(R.id.iv_avatar);
        WaveView waveView = rootView.findViewById(R.id.wave_view);
        final FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ivAvatar.getLayoutParams();
        waveView.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0, 0, 0, (int) y + 2);
                ivAvatar.setLayoutParams(lp);
            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View rootView) {

    }

}
