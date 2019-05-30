package com.ifenglian.module_d.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.ui.activity.MDBarCodeActivity;
import com.ifenglian.module_d.ui.activity.MDCalendarActivity;
import com.ifenglian.module_d.ui.activity.MDImgMultipleSelectActivity;
import com.ifenglian.module_d.ui.activity.MDImgSingleSelectActivity;
import com.ifenglian.module_d.ui.activity.MDMeiZuBannerActivity;
import com.ifenglian.module_d.ui.activity.MDNotificationActivity;
import com.ifenglian.module_d.ui.activity.MDPickerViewActivity;

/**
 * 文 件 名: MDViewPagerFragment4
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDViewPagerFragment4 extends BaseFragment implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d4;
    }

    @Override
    public void initView(View rootView) {
        rootView.findViewById(R.id.btn_img_single_choose).setOnClickListener(this);
        rootView.findViewById(R.id.btn_img_multiple_choose).setOnClickListener(this);
        rootView.findViewById(R.id.btn_bar_code).setOnClickListener(this);
        rootView.findViewById(R.id.btn_picker_view).setOnClickListener(this);
        rootView.findViewById(R.id.btn_calendar).setOnClickListener(this);
        rootView.findViewById(R.id.btn_meizu_banner).setOnClickListener(this);
        rootView.findViewById(R.id.btn_notification).setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View rootView) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_img_single_choose) {
            Intent intent = new Intent(getContext(), MDImgSingleSelectActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_img_multiple_choose) {
            Intent intent = new Intent(getContext(), MDImgMultipleSelectActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_bar_code) {
            Intent intent = new Intent(getContext(), MDBarCodeActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_picker_view) {
            Intent intent = new Intent(getContext(), MDPickerViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_calendar) {
            Intent intent = new Intent(getContext(), MDCalendarActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_meizu_banner) {
            Intent intent = new Intent(getContext(), MDMeiZuBannerActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_notification) {
            Intent intent = new Intent(getContext(), MDNotificationActivity.class);
            startActivity(intent);
        }
    }
}
