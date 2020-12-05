package com.enzo.commonlib.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.enzo.commonlib.utils.common.ApkUtils;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.commonlib.utils.common.PhoneUtils;
import com.enzo.commonlib.utils.toast.ToastUtil;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize(this);
    }

    private void initialize(Application application) {
        // 获取当前进程名
        String processName = ApkUtils.getProcessName(android.os.Process.myPid());
        LogUtil.d("BaseApplication processName..." + processName);

        //ARouter
        if (ApkUtils.isAppDebug(application)) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
        //toast
        ToastUtil.initialize(application);
        //初始化手机参数
        PhoneUtils.getInstance().init(application);

        //可以异步加载的放这里
        if (ApkUtils.isMainProcess(application)) {
            InitializeService.start(application);
        }
    }
}
