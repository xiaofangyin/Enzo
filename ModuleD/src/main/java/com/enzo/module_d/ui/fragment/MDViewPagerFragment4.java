package com.enzo.module_d.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseFragment;
import com.enzo.module_d.R;
import com.enzo.module_d.ui.activity.MDCalendarActivity;
import com.enzo.module_d.ui.activity.MDColorPickerActivity;
import com.enzo.module_d.ui.activity.MDImgMultipleSelectActivity;
import com.enzo.module_d.ui.activity.MDImgSingleSelectActivity;
import com.enzo.module_d.ui.activity.MDMeiZuBannerActivity;
import com.enzo.module_d.ui.activity.MDNotificationActivity;
import com.enzo.module_d.ui.activity.MDPickerViewActivity;
import com.enzo.module_d.ui.activity.MDScanBarCodeActivity;
import com.enzo.module_d.ui.activity.MDScanQrCodeActivity;
import com.enzo.module_d.ui.activity.MDStructureActivity;
import com.enzo.module_d.ui.activity.SampleActivity;

/**
 * 文 件 名: MDViewPagerFragment4
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/1
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDViewPagerFragment4 extends BaseFragment implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.md_fragment_d4;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View rootView) {
        rootView.findViewById(R.id.btn_structure).setOnClickListener(this);
        rootView.findViewById(R.id.btn_img_single_choose).setOnClickListener(this);
        rootView.findViewById(R.id.btn_img_multiple_choose).setOnClickListener(this);
        rootView.findViewById(R.id.btn_bar_code).setOnClickListener(this);
        rootView.findViewById(R.id.btn_qr_code).setOnClickListener(this);
        rootView.findViewById(R.id.btn_picker_view).setOnClickListener(this);
        rootView.findViewById(R.id.btn_calendar).setOnClickListener(this);
        rootView.findViewById(R.id.btn_meizu_banner).setOnClickListener(this);
        rootView.findViewById(R.id.btn_notification).setOnClickListener(this);
        rootView.findViewById(R.id.btn_color_picker).setOnClickListener(this);
        rootView.findViewById(R.id.btn_zhifu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_structure) {
            Intent intent = new Intent(getContext(), MDStructureActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_img_single_choose) {
            Intent intent = new Intent(getContext(), MDImgSingleSelectActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_img_multiple_choose) {
            Intent intent = new Intent(getContext(), MDImgMultipleSelectActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_bar_code) {
            Intent intent = new Intent(getContext(), MDScanBarCodeActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_qr_code) {
            Intent intent = new Intent(getContext(), MDScanQrCodeActivity.class);
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
        } else if (id == R.id.btn_color_picker) {
            Intent intent = new Intent(getContext(), MDColorPickerActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_zhifu) {
            Intent intent = new Intent(getContext(), SampleActivity.class);
            startActivity(intent);
        }
    }
}
