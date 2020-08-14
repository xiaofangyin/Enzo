package com.enzo.module_a.ui.activity;

import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.statusbar.bar.StateAppBar;
import com.enzo.commonlib.utils.statusbar.utils.StatusBarUtils;
import com.enzo.module_a.R;

/**
 * Boss我的界面
 */
public class MABossMineActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.ma_activity_boss_mine;
    }

    @Override
    public void initView() {
        StateAppBar.setStatusBarLightMode(this,
                ContextCompat.getColor(this, R.color.color_yellow));
        StatusBarUtils.StatusBarLightMode(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }
}