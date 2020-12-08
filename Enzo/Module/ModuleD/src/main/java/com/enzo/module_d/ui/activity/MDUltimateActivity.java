package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.statusbar.ultimate.UltimateBarX;
import com.enzo.commonlib.utils.statusbar.ultimate.bean.BarConfig;
import com.enzo.module_d.R;

/**
 * https://github.com/Zackratos/UltimateBarX
 */
public class MDUltimateActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_switch;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_transparent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarConfig config = BarConfig.Companion.newInstance()
                        .transparent()
                        .light(true);
                UltimateBarX.with(MDUltimateActivity.this)
                        .config(config)
                        .applyStatusBar();
            }
        });
        findViewById(R.id.btn_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarConfig config = BarConfig.Companion.newInstance()
                        .color(getColor(R.color.color_red))
                        .light(true);
                UltimateBarX.with(MDUltimateActivity.this)
                        .config(config)
                        .applyStatusBar();
            }
        });
        findViewById(R.id.btn_yellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarConfig config = BarConfig.Companion.newInstance()
                        .fitWindow(false)
                        .drawableRes(R.drawable.md_status_bar_bg_gradient)
                        .light(true);
                UltimateBarX.with(MDUltimateActivity.this)
                        .config(config)
                        .applyStatusBar();
            }
        });
        findViewById(R.id.btn_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarConfig config = BarConfig.Companion.newInstance()
                        .color(getColor(R.color.color_dark_black))
                        .light(false);
                UltimateBarX.with(MDUltimateActivity.this)
                        .config(config)
                        .applyStatusBar();
            }
        });
        findViewById(R.id.btn_white).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarConfig config = BarConfig.Companion.newInstance()
                        .color(getColor(R.color.color_white))
                        .light(true);
                UltimateBarX.with(MDUltimateActivity.this)
                        .config(config)
                        .applyStatusBar();
            }
        });
    }
}
