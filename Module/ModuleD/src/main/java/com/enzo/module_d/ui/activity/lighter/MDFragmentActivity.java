package com.enzo.module_d.ui.activity.lighter;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

public class MDFragmentActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_lighter_fragment;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_widget);
        headWidget.setTitle("引导");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        MDLighterHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_content, new MDLighterFragment())
                .commit();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }
}
