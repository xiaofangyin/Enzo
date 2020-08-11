package com.enzo.module_a.ui.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.base.BaseFragment;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.autoscrolltextview.AutoScrollTextView;
import com.enzo.commonlib.widget.timeclock.TimePicker;
import com.enzo.flkit.router.ModuleARouterPath;
import com.enzo.module_a.R;
import com.enzo.module_a.plugin.MAPluginFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MAFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
@Route(path = ModuleARouterPath.MODULE_A_FRAGMENT)
public class MAFragment extends BaseFragment {

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
        verticalScrollTV = rootView.findViewById(R.id.auto_scroll_text_view);
        picker = rootView.findViewById(R.id.picker);
        tvStartTime = rootView.findViewById(R.id.tv_start_time);
        tvEndTime = rootView.findViewById(R.id.tv_end_time);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        final List<String> list = new ArrayList<>();
        list.add("王者风范");
        list.add("狭路相逢");
        list.add("别无出路");
        verticalScrollTV.setText(list.get(0));
        verticalScrollTV.setList(list);
        verticalScrollTV.startScroll();
        verticalScrollTV.setClickListener(new AutoScrollTextView.ItemClickListener() {
            @Override
            public void onClick(int position) {
                ToastUtil.show(list.get(position), Toast.LENGTH_SHORT);
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
        rootView.findViewById(R.id.ma_open_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MAPluginFactory.getInstance().hostDelegate.openDrawer(getActivity(), Gravity.START);
            }
        });
    }
}
