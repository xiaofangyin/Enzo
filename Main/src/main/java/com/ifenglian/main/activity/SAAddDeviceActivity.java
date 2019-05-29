package com.ifenglian.main.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.ifenglian.main.R;

/**
 * 文 件 名: SAAddDeviceActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAAddDeviceActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.sa_activity_add_device;
    }

    @Override
    public void initHeader() {
        HeadWidget headWidget = findViewById(R.id.add_device_header);
        headWidget.setTitle("添加设备");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }
}
