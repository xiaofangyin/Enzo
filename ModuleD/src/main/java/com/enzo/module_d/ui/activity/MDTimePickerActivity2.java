package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.picker.picker.TimePickerView;
import com.enzo.commonlib.widget.picker.picker.listener.OnTimeSelectedListener;
import com.enzo.module_d.R;

public class MDTimePickerActivity2 extends BaseActivity {

    private TimePickerView timePickerView;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_time_picker2;
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
        timePickerView = findViewById(R.id.time_picker_24);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        timePickerView.setOnTimeSelectedListener(new OnTimeSelectedListener() {
            @Override
            public void onTimeSelected(boolean is24Hour, int hour, int minute, int second, boolean isAm) {
                LogUtil.d("hour: " + hour + "...minute: " + minute + "...second: " + second + "...isAm: " + isAm);
            }
        });
    }
}
