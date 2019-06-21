package com.enzo.commonlib.net.retrofit;

import com.enzo.commonlib.R;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.ToastUtils;

import rx.functions.Action1;

/**
 * 文 件 名: ThrowableAction1
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/7/13
 * 邮   箱: xiaofangyinwork@163.com
 */
public class ThrowableAction1 implements Action1<Throwable> {

    @Override
    public void call(Throwable throwable) {
        LogUtil.e("throwable: " + throwable.getMessage());
        if (!(throwable instanceof Fault)) {
            ToastUtils.showToast(R.string.net_error);
        } else {
            Fault fault = (Fault) throwable;
            ToastUtils.showToast(fault.getMessage());
        }
    }
}
