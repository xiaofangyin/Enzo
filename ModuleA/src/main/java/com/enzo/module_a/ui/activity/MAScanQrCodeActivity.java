package com.enzo.module_a.ui.activity;

import android.os.Bundle;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.utils.zxing.activity.CaptureActivity;

public class MAScanQrCodeActivity extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitleText() {
        return "二维码扫描";
    }

    @Override
    public boolean isBarCode() {
        return false;
    }

    @Override
    public String getStatusText() {
        return "将商品条形码对准框内可扫描";
    }

    @Override
    public String getHint() {
        return "请输入商品条形码";
    }

    @Override
    public void onSearchInput(String inputText) {
        reportFetch(inputText);
    }

    @Override
    public void onHandleDecode(String result) {
        reportFetch(result);
    }

    private void reportFetch(String code) {
        LogUtil.d("reportFetch code: " + code);
        ToastUtil.show(String.valueOf(code));
    }
}
