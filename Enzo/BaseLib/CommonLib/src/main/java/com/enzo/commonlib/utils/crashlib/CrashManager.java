package com.enzo.commonlib.utils.crashlib;

import android.app.Application;

import com.enzo.commonlib.utils.crashlib.ui.ExceptionCaughtHandler;
import com.enzo.commonlib.utils.crashlib.util.CrashHelper;

public class CrashManager {

    private static final CrashManager gInstance = new CrashManager();

    public static CrashManager getInstance() {
        return gInstance;
    }

    private CrashManager() {

    }

    public void init(Application application) {
        CrashHelper.init(application);
        Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        ExceptionCaughtHandler exceptionCaughtHandler = new ExceptionCaughtHandler(application, handler);
        Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtHandler);
    }
}
