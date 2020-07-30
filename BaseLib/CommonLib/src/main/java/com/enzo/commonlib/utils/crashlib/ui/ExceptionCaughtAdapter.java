package com.enzo.commonlib.utils.crashlib.ui;

import android.app.Application;

import com.enzo.commonlib.utils.crashlib.CrashManager;
import com.enzo.commonlib.utils.crashlib.util.CrashHelper;

import java.lang.Thread.UncaughtExceptionHandler;


public class ExceptionCaughtAdapter implements UncaughtExceptionHandler {

    private Application application;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public ExceptionCaughtAdapter(Application application,Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.application = application;
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String crashInfo = CrashHelper.buildCrashInfo(ex);
        CrashHelper.saveCrashLogToLocal(application,crashInfo);
        if (CrashManager.getInstance().isDebug()) {//debug模式下才显性的展示崩溃信息
            ShowExceptionActivity.showException(application,crashInfo);
        }
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, ex);
        }
    }
}
