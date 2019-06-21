package com.ifenglian.module_a.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.widget.autoscrolltextview.AutoScrollTextView;
import com.enzo.commonlib.widget.spiderweb.SpiderWebView;
import com.enzo.commonlib.widget.timeclock.TimePicker;
import com.ifenglian.module_a.R;
import com.ifenglian.module_a.plugin.MAPluginFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MAFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MAFragment extends BaseFragment {

    private SpiderWebView mSpiderWebView;
    private AutoScrollTextView verticalScrollTV;
    private TimePicker picker;
    private TextView tvStartTime;
    private TextView tvEndTime;

    @Override
    public int getLayoutId() {
        return R.layout.ma_fragment_a;
    }

    @Override
    public void initView(View rootView) {
        mSpiderWebView = rootView.findViewById(R.id.spider_web_view);
        verticalScrollTV = rootView.findViewById(R.id.auto_scroll_text_view);
        picker = rootView.findViewById(R.id.picker);
        tvStartTime = rootView.findViewById(R.id.tv_start_time);
        tvEndTime = rootView.findViewById(R.id.tv_end_time);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mSpiderWebView.setPointNum(80);//小点数量
        mSpiderWebView.setPointAcceleration(6);//加速度


        final List<String> list = new ArrayList<>();
        list.add("王者风范");
        list.add("狭路相逢勇者胜");
        list.add("别无出路");
        verticalScrollTV.setText(list.get(0));
        verticalScrollTV.setList(list);
        verticalScrollTV.startScroll();
        verticalScrollTV.setClickListener(new AutoScrollTextView.ItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), list.get(position), Toast.LENGTH_SHORT).show();
            }
        });

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
    public void initListener(View rootView) {
        rootView.findViewById(R.id.ma_add_device_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MAPluginFactory.getInstance().hostDelegate.popToAddDevicesController(getActivity());
            }
        });
    }
}
