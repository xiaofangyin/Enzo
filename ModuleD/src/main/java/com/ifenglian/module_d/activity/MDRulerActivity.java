package com.ifenglian.module_d.activity;

import android.widget.TextView;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.utils.toast.ToastUtils;
import com.ifenglian.commonlib.widget.view.boheruler.Ruler;
import com.ifenglian.commonlib.widget.view.boheruler.RulerCallback;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.jni.DataProvider;

/**
 * 文 件 名: MDRulerActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/8
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDRulerActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_bohe;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        DataProvider provider = new DataProvider();
        int result = provider.add(9, 8);
        ToastUtils.showToast(String.valueOf(result));
    }

    @Override
    public void initListener() {
        Ruler ruler2 = findViewById(R.id.ruler2);
        final TextView tvValue = findViewById(R.id.tv_value);
        ruler2.setRulerCallback(new RulerCallback() {
            @Override
            public void onScaleChanging(float scale) {
                tvValue.setText(String.valueOf(scale));
            }

            @Override
            public void afterScaleChanged(float scale) {
                ToastUtils.showToast(String.valueOf(scale));
            }
        });
    }
}
