package com.enzo.commonlib.base;

import android.app.Activity;
import android.app.Application;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.enzo.commonlib.BuildConfig;
import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.commonlib.utils.common.ApkUtils;
import com.enzo.commonlib.utils.common.ExternalCacheUtil;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.crashlib.CrashManager;
import com.enzo.skin.manager.loader.SkinManager;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 文 件 名: InitializeService
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/23
 * 邮   箱: xiaofangyinwork@163.com
 * IntentService是继承于Service并处理异步请求的一个类，在IntentService内有一个工作线程来处理耗时操作，
 * 启动IntentService的方式和启动传统Service一样，同时，当任务执行完后，IntentService会自动停止，
 * 而不需要我们去手动控制。另外，可以启动IntentService多次，而每一个耗时操作会以工作队列的方式在
 * IntentService的onHandleIntent回调方法中执行，并且，每次只会执行一个工作线程，执行完第一个再执行第二个，以此类推。
 * 而且，所有请求都在一个单线程中，不会阻塞应用程序的主线程（UI Thread），同一时间只处理一个请求。
 * 那么，用IntentService有什么好处呢？首先，我们省去了在Service中手动开线程的麻烦，第二，当操作完成时，我们不用手动停止Service。
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.enzo.xfy.service.action.INIT";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtil.d("InitializeService onStartCommand...");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogUtil.d("InitializeService onHandleIntent...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "InitializeId";
            String CHANNEL_NAME = "Initialize";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("")
                    .build();
            startForeground(1024, notification);
        }

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit(getApplication());
            }
        }
    }

    private void performInit(Application application) {
        long beforeTime = System.currentTimeMillis();
        if (BuildConfig.DEBUG) {
            Debug.startMethodTracing(ExternalCacheUtil.getTracingDir(application).getAbsolutePath() + "/tracing.trace");
        }

        //收集Activity任务栈
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                LogUtil.d("ActivityCallbacks onActivityCreated..." + activity.getComponentName().getClassName());
                ActivityHelper.getManager().addActivity(activity);
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                LogUtil.d("ActivityCallbacks onActivityDestroyed..." + activity.getComponentName().getClassName());
                ActivityHelper.getManager().finishActivity(activity);
            }
        });
        //主题
        SkinManager.getInstance().init(application);
        SkinManager.getInstance().load();
        //LeakCanary:在注册之前先判断LeakCanary是否已经运行在手机上，
        //比如你同时有多个APP集成了LeakCanary，其他app已经运行了LeakCanary则不需要重新install
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }
        //初始化崩溃捕获
        CrashManager.getInstance().init(application);
        // 初始化Bugly appId:8ac8d8a126   appKey:6552f636-bb34-4146-845e-637f57785e1c
        CrashReport.initCrashReport(application, "8ac8d8a126", true);

        if (BuildConfig.DEBUG) {
            Debug.stopMethodTracing();
        }
        long afterTime = System.currentTimeMillis();
        LogUtil.d("InitializeService use time: " + (afterTime - beforeTime));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("InitializeService onDestroy...");
    }
}
