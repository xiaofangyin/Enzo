package com.ifenglian.module_a.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ifenglian.commonlib.base.BaseFragment;
import com.ifenglian.commonlib.widget.view.autoscrolltextview.AutoScrollTextView;
import com.ifenglian.commonlib.widget.view.timeclock.SHTimePicker;
import com.ifenglian.module_a.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MAFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MAFragment extends BaseFragment {

    private AutoScrollTextView verticalScrollTV;
    private SHTimePicker picker;
    private TextView tvStartTime;
    private TextView tvEndTime;

    @Override
    public int getLayoutId() {
        return R.layout.ma_fragment_a;
    }

    @Override
    public void initView(View rootView) {
        verticalScrollTV = rootView.findViewById(R.id.auto_scroll_text_view);
        picker = rootView.findViewById(R.id.picker);
        tvStartTime = rootView.findViewById(R.id.tv_start_time);
        tvEndTime = rootView.findViewById(R.id.tv_end_time);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        final List<String> list = new ArrayList<>();
        list.add("王者风范");
        list.add("狭路相逢勇者胜");
        list.add("别无出路");
        verticalScrollTV.setText(list.get(0));
        verticalScrollTV.setList(list);
        verticalScrollTV.startScroll();
        verticalScrollTV.setClickLisener(new AutoScrollTextView.ItemClickLisener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), list.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        picker.setOnTimeChangeListener(new SHTimePicker.OnTimeChangeListener() {
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
    public void initListener(View rootView) {

    }
}
