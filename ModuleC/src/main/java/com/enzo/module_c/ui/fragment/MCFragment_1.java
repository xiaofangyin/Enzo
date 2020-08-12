package com.enzo.module_c.ui.fragment;

import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.progress.FLCSeekBar;
import com.enzo.commonlib.widget.progress.WaterWaveView;
import com.enzo.module_c.R;

/**
 * 文 件 名: MCFragment_1
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCFragment_1 extends BaseFragment {

    private WaterWaveView waterWaveView;
    private FLCSeekBar seekBarProgress;
    private FLCSeekBar seekBarSize;
    private FLCSeekBar seekBarAmplitude;

    @Override
    public int getLayoutId() {
        return R.layout.mc_fragment_c_child_1;
    }

    @Override
    public void initView(View rootView) {
        waterWaveView = rootView.findViewById(R.id.water_wave_view);
        seekBarProgress = rootView.findViewById(R.id.seek_bar_progress);
        seekBarSize = rootView.findViewById(R.id.seek_bar_size);
        seekBarAmplitude = rootView.findViewById(R.id.seek_bar_amplitude);
    }

    @Override
    public void initListener(View rootView) {
        seekBarProgress.setOnSeekChangedListener(new FLCSeekBar.OnSeekBarChangedListener() {
            @Override
            public void onProgressChanged(FLCSeekBar seekBar, int progress) {
                waterWaveView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(FLCSeekBar seekBar, int progress) {

            }

            @Override
            public void onStopTrackingTouch(FLCSeekBar seekBar, int progress) {

            }
        });
        seekBarSize.setOnSeekChangedListener(new FLCSeekBar.OnSeekBarChangedListener() {
            @Override
            public void onProgressChanged(FLCSeekBar seekBar, int progress) {
                waterWaveView.setBorderWidth(progress / 100f * waterWaveView.getWidth() / 2);
            }

            @Override
            public void onStartTrackingTouch(FLCSeekBar seekBar, int progress) {

            }

            @Override
            public void onStopTrackingTouch(FLCSeekBar seekBar, int progress) {

            }
        });
        seekBarAmplitude.setOnSeekChangedListener(new FLCSeekBar.OnSeekBarChangedListener() {
            @Override
            public void onProgressChanged(FLCSeekBar seekBar, int progress) {
                waterWaveView.setAmplitudeRatio((float) progress / 1000);
            }

            @Override
            public void onStartTrackingTouch(FLCSeekBar seekBar, int progress) {

            }

            @Override
            public void onStopTrackingTouch(FLCSeekBar seekBar, int progress) {

            }
        });
    }

    @Override
    public void lazyLoad() {
        waterWaveView.setProgress(50, 1000);
        seekBarProgress.setProgress(50);
    }
}
