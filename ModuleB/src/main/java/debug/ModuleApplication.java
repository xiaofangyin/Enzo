package debug;

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

public class ModuleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        login();
        initEnv(this);
    }

    /**
     * 在这里模拟登陆，然后拿到sessionId或者Token
     * 这样就能够在组件请求接口了
     */
    private void login() {

    }

    private static void initEnv(Application application) {
        //ARouter
        if (ApkUtils.isAppDebug(application)) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
        //初始化手机参数
        PhoneUtils.getInstance().init(application);
        //初始化崩溃捕获
        CrashManager.getInstance().init(application);
        //收集Activity任务栈
        application.registerActivityLifecycleCallbacks(new ActivityCallbacks());
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
