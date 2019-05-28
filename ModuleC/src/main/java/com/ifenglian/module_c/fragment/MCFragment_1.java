package com.ifenglian.module_c.fragment;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.progress.FLCSeekBar;
import com.enzo.commonlib.widget.progress.WaterWaveView;
import com.ifenglian.module_c.R;

/**
 * 文 件 名: MCFragment_1
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_1 extends BaseFragment {

    private WaterWaveView waterWaveView;
    private FLCSeekBar seekBar;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_1;
    }

    @Override
    public void initView(View rootView) {
        waterWaveView = rootView.findViewById(R.id.water_wave_view);
        seekBar = rootView.findViewById(R.id.seek_bar);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        waterWaveView.setProgress(50, 1000);
        seekBar.setProgress(50);
    }

    @Override
    public void initListener(View rootView) {
        seekBar.setOnSeekChangedListener(new FLCSeekBar.OnSeekBarChangedListener() {
            @Override
            public void onProgressChanged(FLCSeekBar seekBar, int percent) {
                waterWaveView.setProgress(percent);
            }

            @Override
            public void onStartTrackingTouch(FLCSeekBar seekBar, int percent) {

            }

            @Override
            public void onStopTrackingTouch(FLCSeekBar seekBar, int percent) {

            }
        });
    }
}
