package com.enzo.module_d.utils.hotfix;

import android.app.Application;
import android.content.Context;

import java.io.File;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //执行热修复。 插入补丁dex
        // /data/data/xxx/files/xxxx.dex
        // /sdcard/xxx.dex
        Hotfix.installPatch(this, new File("/sdcard/patch.dex"));
    }
}
