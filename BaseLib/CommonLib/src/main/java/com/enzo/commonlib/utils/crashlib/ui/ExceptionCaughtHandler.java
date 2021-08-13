package com.enzo.commonlib.utils.crashlib.ui;

import android.app.Application;

import androidx.annotation.NonNull;

import com.enzo.commonlib.utils.common.ApkUtils;
import com.enzo.commonlib.utils.crashlib.util.CrashHelper;

import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionCaughtHandler implements UncaughtExceptionHandler {

    private final Application application;
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public ExceptionCaughtHandler(Application application, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.application = application;
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable ex) {
        String crashInfo = CrashHelper.buildCrashInfo(ex);
        CrashHelper.saveCrashLogToLocal(application, crashInfo);
        if (ApkUtils.isAppDebug(application)) {//debug模式下才显性的展示崩溃信息
            ShowExceptionActivity.showException(application, crashInfo);
        }
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, ex);
        }
    }
}
