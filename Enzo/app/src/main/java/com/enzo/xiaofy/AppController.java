package com.enzo.xiaofy;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.enzo.commonlib.env.EnvConstants;
import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.commonlib.utils.common.PhoneUtils;
import com.enzo.commonlib.utils.crashlib.CrashManager;
import com.enzo.flkit.FLPluginFactory;
import com.enzo.main.plugin.SAFactoryManager;
import com.enzo.module_a.plugin.MAPluginFactory;
import com.enzo.module_b.plugin.MBPluginFactory;
import com.enzo.module_c.plugin.MCPluginFactory;
import com.enzo.module_d.plugin.MDPluginFactory;

import java.util.ArrayList;
import java.util.List;


public class AppController {

    public static void attachBaseContext(Application application) {

    }

    public static void onCreate(Application application) {
        initEnv(application);
        initFactory(application);
    }

    public static void onConfigurationChanged(Application application, Configuration newConfig) {

    }

    private static void initEnv(Application application) {
        PhoneUtils.getInstance().init(application);

        MultiDex.install(application);

        EnvConstants.getInstance().init(BuildConfig.PROD_ENV, BuildConfig.LOG_OPEN, "");

        CrashManager.getInstance().init(application);

        application.registerActivityLifecycleCallbacks(new ActivityCallbacks());
    }

    private static void initFactory(Application application) {
        List<FLPluginFactory> factoryList = new ArrayList<>();
        factoryList.add(MAPluginFactory.getInstance());
        factoryList.add(MBPluginFactory.getInstance());
        factoryList.add(MCPluginFactory.getInstance());
        factoryList.add(MDPluginFactory.getInstance());
        SAFactoryManager.getInstance().init(application, factoryList);
    }


    private static class ActivityCallbacks implements Application.ActivityLifecycleCallbacks {

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
