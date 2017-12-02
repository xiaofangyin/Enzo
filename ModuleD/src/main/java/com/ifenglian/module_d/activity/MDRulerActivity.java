package com.ifenglian.module_d.activity;

import android.util.Log;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.view.boheruler.Ruler;
import com.ifenglian.commonlib.widget.view.boheruler.KgNumberLayout;
import com.ifenglian.commonlib.widget.view.boheruler.Ruler2;
import com.ifenglian.commonlib.widget.view.boheruler.RulerCallback;
import com.ifenglian.module_d.R;

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
        Ruler mBooheeRuler = findViewById(R.id.br);
        KgNumberLayout mKgNumberLayout = findViewById(R.id.knl);
        mKgNumberLayout.bindRuler(mBooheeRuler);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        Ruler2 ruler2 = findViewById(R.id.ruler2);
        ruler2.setRulerCallback(new RulerCallback() {
            @Override
            public void onScaleChanging(float scale) {
                Log.e("AAA", "onScaleChanging scale: " + scale);
            }

            @Override
            public void afterScaleChanged(float scale) {
                Log.e("AAA", "afterScaleChanged scale: " + scale);
            }
        });
    }
}
