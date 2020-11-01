package com.enzo.module_d.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.utils.zxing.activity.CaptureActivity;
import com.enzo.flkit.router.ModuleDRouterPath;

/**
 * 文 件 名: MDScanBarCodeActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/30
 * 邮   箱: xiaofangyinwork@163.com
 */
@Route(path = ModuleDRouterPath.MODULE_D_BAR_CODE)
public class MDScanBarCodeActivity extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitleText() {
        return "条形码扫描";
    }

    @Override
    public boolean isBarCode() {
        return true;
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
