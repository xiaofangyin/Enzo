package com.enzo.module_d.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.module_d.R;

/**
 * 文 件 名: MDTouchEventActivity
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/2
 * 邮   箱: xiaofywork@163.com
 */
public class MDTouchEventActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_touch_event;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_view);
        headWidget.setTitle("事件分发");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.d("MDTouchEventActivity dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("MDTouchEventActivity onTouchEvent");
        return super.onTouchEvent(event);
    }
}
