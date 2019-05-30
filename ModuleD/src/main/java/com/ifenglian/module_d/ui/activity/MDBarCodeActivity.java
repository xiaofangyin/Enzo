package com.ifenglian.module_d.ui.activity;

import android.os.Bundle;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.ToastUtils;
import com.enzo.commonlib.utils.zbar.CaptureActivity;

import cn.bingoogolapple.qrcode.core.BarcodeType;

/**
 * 文 件 名: CheckReportActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/30
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDBarCodeActivity extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mZBarView.getScanBoxView().setRectWidth(DensityUtil.dip2px(240));
//        mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
//        mZBarView.setType(BarcodeType.ONE_DIMENSION, null); // 只识别一维条码
//        mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别

        mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        mZBarView.setType(BarcodeType.ALL, null); // 识别所有类型的码
        mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
    }

    @Override
    public String getTitleText() {
        return "扫码查食品";
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
