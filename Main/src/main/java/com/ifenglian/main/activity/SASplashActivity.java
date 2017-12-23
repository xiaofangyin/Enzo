package com.ifenglian.main.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import com.ifenglian.commonlib.base.BaseActivity;

/**
 * 文 件 名: SASplashActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SASplashActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        //隐藏信号栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void initData() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SAMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    public void initListener() {

    }
}
