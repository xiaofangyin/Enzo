package com.enzo.commonlib.base;

import android.app.Activity;
import android.app.Application;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.commonlib.utils.common.ApkUtils;
import com.enzo.commonlib.utils.common.ExternalCacheUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.PhoneUtils;
import com.enzo.commonlib.utils.crashlib.CrashManager;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.skin.manager.loader.SkinManager;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.enzo.xfy.service.action.INIT";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e("xfy", "InitializeService onStartCommand...");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("xfy", "InitializeService onHandleIntent...");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit(getApplication());
            }
        }
    }

    private void performInit(Application application) {
        Log.e("xfy", "InitializeService performInit...");
        long beforeTime = System.currentTimeMillis();
        Log.e("xfy", "2 initEnv is Main Process time: " + beforeTime);
        Debug.startMethodTracing(ExternalCacheUtil.getTracingDir(application).getAbsolutePath() + "/tracing.trace");
        //LeakCanary:在注册之前先判断LeakCanary是否已经运行在手机上，
        //比如你同时有多个APP集成了LeakCanary，其他app已经运行了LeakCanary则不需要重新install
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }
        //ARouter
        if (ApkUtils.isAppDebug(application)) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
        //主题
        SkinManager.getInstance().init(application);
        SkinManager.getInstance().load();
        //toast
        ToastUtil.initialize(application);
        //初始化手机参数
        PhoneUtils.getInstance().init(application);
        //初始化崩溃捕获
        CrashManager.getInstance().init(application);
        //收集Activity任务栈
        application.registerActivityLifecycleCallbacks(new ActivityCallbacks());

        // 初始化Bugly appId:8ac8d8a126   appKey:6552f636-bb34-4146-845e-637f57785e1c
        CrashReport.initCrashReport(application, "8ac8d8a126", true);
        Debug.stopMethodTracing();
        long afterTime = System.currentTimeMillis();
        Log.e("xfy", "after: " + afterTime);
        Log.e("xfy", "use time: " + (afterTime - beforeTime));
    }

    private static class ActivityCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            LogUtil.d("ActivityCallbacks onActivityCreated...");
            ActivityHelper.getManager().addActivity(activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {

        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            LogUtil.d("ActivityCallbacks onActivityDestroyed...");
            ActivityHelper.getManager().finishActivity(activity);
        }
    }
}
