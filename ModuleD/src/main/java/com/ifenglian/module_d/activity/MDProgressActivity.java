package com.ifenglian.module_d.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.utils.toast.ToastUtils;
import com.ifenglian.commonlib.widget.view.progress.CircularProgressBar;
import com.ifenglian.commonlib.widget.view.progress.CircularProgressBarWithRate;
import com.ifenglian.commonlib.widget.view.progress.HorizontalProgressBar;
import com.ifenglian.commonlib.widget.view.progress.SGLSeekBar;
import com.ifenglian.module_d.R;

/**
 * 文 件 名: MDProgressActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/9/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDProgressActivity extends BaseActivity {

    private int progress = 0;
    private SGLSeekBar seekBar;
    private HorizontalProgressBar mCustomProgressBar;
    private CircularProgressBar mCircularProgressBar;
    private CircularProgressBarWithRate mRateTextCircularProgressBar;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_progress;
    }

    @Override
    public void initView() {
        seekBar = findViewById(R.id.seek_bar);
        mCircularProgressBar = findViewById(R.id.circular_progress_bar);
        mCircularProgressBar.setMax(100);

        mRateTextCircularProgressBar = findViewById(R.id.rate_progress_bar);
        mRateTextCircularProgressBar.setMax(100);
        mRateTextCircularProgressBar.setCircleWidth(dip2px(10));

        mCustomProgressBar = findViewById(R.id.web_view_progress_bar);
    }

    @Override
    public void initData() {
        mHandler.sendEmptyMessageDelayed(progress++, 30);
    }

    @Override
    public void initListener() {
        seekBar.setOnSeekChangedListener(new SGLSeekBar.OnSeekBarChangedListener() {
            @Override
            public void onProgressChanged(SGLSeekBar seekBar, int percent) {
                Log.d("AAA", "percent: " + percent);
                ToastUtils.showShortToast(String.valueOf(percent));
            }

            @Override
            public void onStartTrackingTouch(SGLSeekBar seekBar, int percent) {

            }

            @Override
            public void onStopTrackingTouch(SGLSeekBar seekBar, int percent) {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
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

    private float dip2px(float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
