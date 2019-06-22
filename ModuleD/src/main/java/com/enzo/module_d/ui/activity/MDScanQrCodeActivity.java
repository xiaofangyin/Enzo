package com.enzo.module_d.ui.activity;

import android.os.Bundle;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.utils.zxing.activity.CaptureActivity;

/**
 * 文 件 名: MDScanQrCodeActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/30
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDScanQrCodeActivity extends CaptureActivity {

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
        ToastUtils.showToast(String.valueOf(code));
    }
}
