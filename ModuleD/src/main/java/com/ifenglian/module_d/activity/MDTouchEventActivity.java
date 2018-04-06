package com.ifenglian.module_d.activity;

import android.util.Log;
import android.view.MotionEvent;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.module_d.R;

/**
 * 文 件 名: MDTouchEventActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/4/6
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDTouchEventActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_touch_event;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("AAA", "MDTouchEventActivity dispatchTouchEvent..." + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("AAA", "MDTouchEventActivity onTouchEvent..." + event.getAction());
        return super.onTouchEvent(event);
    }
}
