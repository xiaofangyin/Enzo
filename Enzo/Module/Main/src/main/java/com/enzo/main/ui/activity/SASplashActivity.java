package com.enzo.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.statusbar.stateappbar.bar.StateAppBar;
import com.enzo.commonlib.utils.statusbar.stateappbar.utils.StatusBarUtils;

/**
 * 文 件 名: SASplashActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/23
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SASplashActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        //隐藏信号栏
        StateAppBar.translucentStatusBar(this, true);
        StatusBarUtils.StatusBarLightMode(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SAAdvertActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    public void initListener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK;
    }
}
