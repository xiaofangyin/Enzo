package com.enzo.module_b.plugin;

import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.flkit.services.FLServiceLoader;

public class MBPluginHostDelegate {

    private static volatile MBPluginHostDelegate mInstance;
    private final FLPluginHostDelegate hostDelegate;

    private MBPluginHostDelegate() {
        hostDelegate = FLServiceLoader.load(FLPluginHostDelegate.class);
    }

    public static MBPluginHostDelegate getInstance() {
        if (mInstance == null) {
            synchronized (MBPluginHostDelegate.class) {
                if (mInstance == null) {
                    mInstance = new MBPluginHostDelegate();
                }
            }
        }
        return mInstance;
    }

    public FLPluginHostDelegate getHostDelegate() {
        return hostDelegate;
    }
}
