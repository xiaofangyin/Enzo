package com.ifenglian.module_d.activity;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.widget.view.boheruler.Ruler;
import com.ifenglian.commonlib.widget.view.boheruler.KgNumberLayout;
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

    }
}
