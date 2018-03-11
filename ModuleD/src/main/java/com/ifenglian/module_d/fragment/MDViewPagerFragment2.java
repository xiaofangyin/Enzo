package com.ifenglian.module_d.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.utils.common.DensityUtil;
import com.ifenglian.commonlib.utils.common.LogUtil;
import com.ifenglian.commonlib.utils.toast.ToastUtils;
import com.ifenglian.commonlib.widget.boheruler.Ruler;
import com.ifenglian.commonlib.widget.boheruler.RulerCallback;
import com.ifenglian.commonlib.widget.progress.CircularProgressBar;
import com.ifenglian.commonlib.widget.progress.CircularProgressBarWithRate;
import com.ifenglian.commonlib.widget.progress.FLCSeekBar;
import com.ifenglian.commonlib.widget.progress.HorizontalProgressBar;
import com.ifenglian.commonlib.widget.progress.SRDiskCapacityProgressBar;
import com.ifenglian.module_d.R;

/**
 * 文 件 名: MDViewPagerFragment2
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment2 extends BaseFragment implements View.OnClickListener {

    private int progress = 0;
    private FLCSeekBar seekBar;
    private HorizontalProgressBar mCustomProgressBar;
    private SRDiskCapacityProgressBar mDiskProgressBar;
    private CircularProgressBar mCircularProgressBar;
    private CircularProgressBarWithRate mRateTextCircularProgressBar;
    private Ruler ruler;
    private TextView tvValue;

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d2;
    }

    @Override
    public void initView(View rootView) {
        seekBar = rootView.findViewById(R.id.seek_bar1);
        mCircularProgressBar = rootView.findViewById(R.id.circular_progress_bar);
        mCircularProgressBar.setMax(100);

        mRateTextCircularProgressBar = rootView.findViewById(R.id.rate_progress_bar);
        mRateTextCircularProgressBar.setMax(100);
        mRateTextCircularProgressBar.setCircleWidth(DensityUtil.dip2px(getActivity(), 10));

        mCustomProgressBar = rootView.findViewById(R.id.web_view_progress_bar);
        mDiskProgressBar = rootView.findViewById(R.id.disk_capacity_progress);
        ruler = rootView.findViewById(R.id.ruler2);
        tvValue = rootView.findViewById(R.id.tv_value);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mHandler.sendEmptyMessageDelayed(progress++, 30);
        mDiskProgressBar.setProgress(40772, 90772);
    }

    @Override
    public void initListener(View rootView) {
        seekBar.setOnSeekChangedListener(new FLCSeekBar.OnSeekBarChangedListener() {
            @Override
            public void onProgressChanged(FLCSeekBar seekBar, int percent) {
                LogUtil.d("percent: " + percent);
                ToastUtils.showToast(String.valueOf(percent));
            }

            @Override
            public void onStartTrackingTouch(FLCSeekBar seekBar, int percent) {

            }

            @Override
            public void onStopTrackingTouch(FLCSeekBar seekBar, int percent) {

            }
        });

        ruler.setRulerCallback(new RulerCallback() {
            @Override
            public void onScaleChanging(float scale) {
                tvValue.setText(String.valueOf(scale));
            }

            @Override
            public void afterScaleChanged(float scale) {
                ToastUtils.showToast(String.valueOf(scale));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mCircularProgressBar.setProgress(msg.what);
            mRateTextCircularProgressBar.setProgress(msg.what);
            mCustomProgressBar.setProgress(msg.what);
            if (progress >= 100) {
                progress = 0;
            }
            mHandler.sendEmptyMessageDelayed(progress++, 100);
        }
    };
}
