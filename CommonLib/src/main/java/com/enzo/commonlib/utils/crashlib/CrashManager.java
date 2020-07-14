package com.enzo.commonlib.utils.crashlib;

import android.app.Application;

import com.enzo.commonlib.utils.crashlib.ui.ExceptionCaughtAdapter;
import com.enzo.commonlib.utils.crashlib.util.CrashHelper;

/**
 * Created by chendx on 2018/5/2
 *
 * @since 1.0
 */

public class CrashManager {
    private static final CrashManager gInstance = new CrashManager();
    private boolean isDebug = true;//是否是debug模式

    public static CrashManager getInstance() {
        return gInstance;
    }


    public boolean isDebug() {
        return isDebug;
    }

    private CrashManager() {
    }

    public void init(Application application, boolean isDebug) {
        this.isDebug = isDebug;
        CrashHelper.init(application);
        Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        ExceptionCaughtAdapter exceptionCaughtAdapter = new ExceptionCaughtAdapter(application, handler);
        Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtAdapter);
    }
}
