package com.enzo.commonlib.base;

import android.app.Application;

import com.enzo.commonlib.utils.common.PhoneUtils;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialize(this);
    }

    private void initialize(Application application) {
        //判断是否是主进程
        if (PhoneUtils.isMainProcess(application)) {
            InitializeService.start(application);
        }
    }
}
