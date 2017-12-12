package com.ifenglian.module_d.activity;

import android.view.View;

import com.ifenglian.commonlib.base.BaseActivity;
import com.ifenglian.commonlib.utils.toast.ToastUtils;
import com.ifenglian.module_d.R;
import com.ifenglian.module_d.jni.DataProvider;

/**
 * 文 件 名: MDJniActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDJniActivity extends BaseActivity{

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_jni;
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

    public void btn_add(View v){
        DataProvider provider = new DataProvider();
        int result = provider.add(9, 8);
        ToastUtils.showToast(String.valueOf(result));
    }

    public void btn_hello(View v){
        DataProvider provider = new DataProvider();
        String result = provider.helloFromC("heiheihei");
        ToastUtils.showToast(result);
    }
}
