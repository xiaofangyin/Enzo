package com.enzo.commonlib.base;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

/**
 * 文 件 名: BaseApplication
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/28
 * 邮   箱: xiaofangyinwork@163.com
 */
public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
