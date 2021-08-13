package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.statusbar.ultimate.UltimateBarX;
import com.enzo.commonlib.utils.statusbar.ultimate.bean.BarConfig;
import com.enzo.module_d.R;

/**
 * https://github.com/Zackratos/UltimateBarX
 *
 * val config = BarConfig.newInstance()          // 创建配置对象
 *     .fitWindow(true)                          // 布局是否侵入状态栏（true 不侵入，false 侵入）
 *     .color(Color.RED)                         // 状态栏背景颜色（色值）
 *     .colorRes(R.color.deepSkyBlue)            // 状态栏背景颜色（资源id）
 *     .drawableRes(R.drawable.bg_gradient)      // 状态栏背景 drawable
 *     .light(false)                             // light模式
 *                                               // 状态栏字体 true: 灰色，false: 白色 Android 6.0+
 *                                               // 导航栏按钮 true: 灰色，false: 白色 Android 8.0+
 *
 * UltimateBarX.with(this)                       // 对当前 Activity 或 Fragment 生效
 *     .config(config)                           // 使用配置
 *     .applyStatusBar()                         // 应用到状态栏
 *
 * UltimateBarX.with(this)                       // 对当前 Activity 或 Fragment 生效
 *     .config(config)                           // 使用配置
 *     .applyNavigationBar()                     // 应用到导航栏
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
