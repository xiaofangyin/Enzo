package com.enzo.commonlib.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.statusbar.stateappbar.bar.StateAppBar;

import com.enzo.skin.manager.base.SkinFragmentActivity;

/**
 * 文 件 名: BaseActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/8
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class BaseActivity extends SkinFragmentActivity implements IBaseActivity {

    public static String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return ContextCompat.getColor(this, R.color.color_dark_black);
    }

    @Override
    public void initHeader() {

    }
}
