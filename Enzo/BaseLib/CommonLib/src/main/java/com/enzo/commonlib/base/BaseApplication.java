package com.enzo.commonlib.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.commonlib.utils.common.ApkUtils;
import com.enzo.commonlib.utils.common.PhoneUtils;
import com.enzo.commonlib.utils.crashlib.CrashManager;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.squareup.leakcanary.LeakCanary;

import com.enzo.skin.manager.loader.SkinManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initEnv(this);
    }

    private void initEnv(Application application) {
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
    }

    private static class ActivityCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
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
            ActivityHelper.getManager().finishActivity(activity);
        }
    }
}
