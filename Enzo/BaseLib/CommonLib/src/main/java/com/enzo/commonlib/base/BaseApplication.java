package com.enzo.commonlib.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.utils.common.ApkUtils;
import com.enzo.skin.manager.loader.SkinManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize(this);
    }

    private void initialize(Application application) {
        //判断是否是主进程
        if (ApkUtils.isMainProcess(application)) {
            //ARouter
            if (ApkUtils.isAppDebug(application)) {
                ARouter.openLog();
                ARouter.openDebug();
            }
            ARouter.init(application);

            //主题
            SkinManager.getInstance().init(application);
            SkinManager.getInstance().load();

            //可以延迟加载的放这里
            InitializeService.start(application);
        }
    }
}
