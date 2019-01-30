package com.enzo.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.commonlib.utils.statusbar.bar.StateAppBar;

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
        ActivityHelper.getManager().addActivity(this);

        if (getStatusBarColor() != 0) {
            StateAppBar.setStatusBarColor(this, getStatusBarColor());
        }
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        initHeader();
        initView();
        initData(savedInstanceState);
        initListener();
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.color_main_blue);
    }

    @Override
    public void initHeader() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityHelper.getManager().finishActivity(this);
    }
}
