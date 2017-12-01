package com.ifenglian.commonlib.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

/**
 * 文 件 名: BaseApplication
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/28
 * 邮   箱: xiaofy@ifenglian.com
 */
public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d("AAA", "master");
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
