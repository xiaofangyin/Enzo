package com.enzo.module_d.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.waveview.WaveView;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDViewPagerFragment3
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofangyinwork@163.com
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
