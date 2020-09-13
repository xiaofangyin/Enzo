package com.enzo.main.model.manager;

import android.content.Context;

/**
 * 内存泄漏测试类
 */
public class TestManager {

    private static TestManager mInstance;
    private Context mContext;

    private TestManager(Context context) {
        mContext = context;
    }

    public static TestManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (TestManager.class) {
                if (mInstance == null) {
                    mInstance = new TestManager(context);
                }
            }
        }
        return mInstance;
    }
}

