package com.enzo.commonlib.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.commonlib.utils.statusbar.bar.StateAppBar;
import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * 文 件 名: BaseActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/8
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    public static String TAG = BaseActivity.class.getSimpleName();
    protected RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelper.getManager().addActivity(this);
        rxPermissions = new RxPermissions(BaseActivity.this);

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
        return getResources().getColor(R.color.color_main_black);
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
