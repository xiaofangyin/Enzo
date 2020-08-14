package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.DensityUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.progress.CircularProgressBar;
import com.enzo.commonlib.widget.progress.CircularProgressBarWithRate;
import com.enzo.commonlib.widget.progress.FLCSeekBar;
import com.enzo.commonlib.widget.progress.HorizontalProgressBar;
import com.enzo.commonlib.widget.progress.SRDiskCapacityProgressBar;
import com.enzo.commonlib.widget.progress.WaterWaveView;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDProgressActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDProgressActivity extends BaseActivity {

    private int progress = 0;
    private FLCSeekBar seekBar;
    private HorizontalProgressBar mCustomProgressBar;
    private SRDiskCapacityProgressBar mDiskProgressBar;
    private CircularProgressBar mCircularProgressBar;
    private CircularProgressBarWithRate mRateTextCircularProgressBar;
    private WaterWaveView waterWaveView;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_progress;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("进度");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        seekBar = findViewById(R.id.seek_bar1);
        mCircularProgressBar = findViewById(R.id.circular_progress_bar);
        mCircularProgressBar.setMax(100);

        mRateTextCircularProgressBar = findViewById(R.id.rate_progress_bar);
        mRateTextCircularProgressBar.setMax(100);
        mRateTextCircularProgressBar.setCircleWidth(DensityUtil.dip2px(this, 10));

        mCustomProgressBar = findViewById(R.id.web_view_progress_bar);
        mDiskProgressBar = findViewById(R.id.disk_capacity_progress);
        waterWaveView = findViewById(R.id.water_wave_view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mHandler.sendEmptyMessageDelayed(progress++, 30);
        mDiskProgressBar.setProgress(40772, 90772);
    }

    @Override
    public void initListener() {
        seekBar.setOnSeekChangedListener(new FLCSeekBar.OnSeekBarChangedListener() {
            @Override
            public void onProgressChanged(FLCSeekBar seekBar, int progress) {
                LogUtil.d("percent: " + progress);
                ToastUtil.show(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(FLCSeekBar seekBar, int progress) {

            }

            @Override
            public void onStopTrackingTouch(FLCSeekBar seekBar, int progress) {

            }
        });
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mCircularProgressBar.setProgress(msg.what);
            mRateTextCircularProgressBar.setProgress(msg.what);
            mCustomProgressBar.setProgress(msg.what);
            waterWaveView.setProgress(msg.what, 1000);
            if (progress >= 100) {
                progress = 0;
            }
            mHandler.sendEmptyMessageDelayed(progress++, 100);
        }
    };
}
