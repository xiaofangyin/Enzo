package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDPickerViewActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyin@360.cn
 */
public class MDPickerViewActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_picker_view;
    }

    @Override
    public void initHeader() {
        HeadWidget headWidget = findViewById(R.id.picker_view_header);
        headWidget.setTitle("日期选择");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        findViewById(R.id.btn_lunar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
