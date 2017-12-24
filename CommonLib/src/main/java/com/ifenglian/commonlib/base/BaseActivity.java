package com.ifenglian.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 文 件 名: BaseActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/8
 * 邮   箱: xiaofy@ifenglian.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    public static String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
