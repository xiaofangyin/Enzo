package com.ifenglian.module_d.activity;

import android.util.Log;
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
public class MDJniActivity extends BaseActivity {

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

    public void getInt(View v) {
        DataProvider provider = new DataProvider();
        int result = provider.javaCallCGetInt(9, 8);
        ToastUtils.showToast("9 + 8 = " + result);
    }

    public void getString(View v) {
        DataProvider provider = new DataProvider();
        String result = provider.javaCallCGetString("heiheihei");
        ToastUtils.showToast(result);
    }

    public void getIntArray(View v) {
        DataProvider provider = new DataProvider();
        int[] array = new int[]{6, 8, 4, 5, 9};
        int[] result = provider.javaCallCGetIntArray(array);
        for (int anArray : result) {
            Log.d("AAA", String.valueOf(anArray));
        }
    }

    public void c_call_java1(View v) {
        DataProvider provider = new DataProvider();
        provider.method1();
    }

    public void c_call_java2(View v) {
        DataProvider provider = new DataProvider();
        provider.method2();
    }

    public void c_call_java3(View v) {
        DataProvider provider = new DataProvider();
        provider.method3();
    }

    public void c_call_java_static(View v) {
        DataProvider provider = new DataProvider();
        provider.method4();
    }
}
