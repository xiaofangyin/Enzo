package com.enzo.module_c.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.enzo.commonlib.utils.common.LogUtil;

/**
 * 文 件 名: IRemoteService
 * 创 建 人: xiaofy
 * 创建日期: 2020/10/15
 * 邮   箱: xiaofywork@163.com
 */
public class RemoteService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private final IBinder iBinder = new ICalInterface.Stub() {

        @Override
        public int add(int a, int b) throws RemoteException {
            LogUtil.d("a + b = " + (a + b));
            return a + b;
        }
    };
}
