package com.enzo.module_d.model.impl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.module_d.ICalculateAidlInterface;

/**
 * 文 件 名: ICalculateAidlImpl
 * 创 建 人: xiaofy
 * 创建日期: 2020/12/5
 * 邮   箱: xiaofywork@163.com
 */
public class ICalculateAidlImpl extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ICalculateAidlInterface.Stub() {
            @Override
            public int add(int a, int b) throws RemoteException {
                LogUtil.d("ICalculateAidlImpl a + b = " + (a + b));
                return a + b;
            }
        };
    }
}
