package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.timeclock.TimePicker;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDTimePickerActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDTimePickerActivity extends BaseActivity {

    private TimePicker picker;
    private TextView tvStartTime;
    private TextView tvEndTime;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_time_picker;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("Time Picker");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        picker = findViewById(R.id.picker);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndTime = findViewById(R.id.tv_end_time);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        picker.setOnTimeChangeListener(new TimePicker.OnTimeChangeListener() {
            @Override
            public void onStartTimeChange(String startTime) {
                tvStartTime.setText(startTime);
            }

            @Override
            public void onEndTimeChange(String endTime) {
                tvEndTime.setText(endTime);
            }
        });
        picker.initTime(6, 25, 15, 35);
    }

    @Override
    public void initListener() {

    }
}
