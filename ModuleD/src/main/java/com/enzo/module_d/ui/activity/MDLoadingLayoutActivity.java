package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.widget.loadinglayout.LoadingLayout;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDLoadingLayoutActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/2
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDLoadingLayoutActivity extends BaseActivity {

    private LoadingLayout loadingLayout;

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_loading_layout;
    }

    @Override
    public void initView() {
        loadingLayout = findViewById(R.id.loading_layout);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingLayout.showError();
            }
        }, 5000);
    }

    @Override
    public void initListener() {
        loadingLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.showLoading();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingLayout.showError();
                    }
                }, 5000);
            }
        });
    }
}
