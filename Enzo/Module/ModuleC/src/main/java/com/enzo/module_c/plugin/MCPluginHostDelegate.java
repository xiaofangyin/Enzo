package com.enzo.module_c.plugin;

import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.flkit.services.FLServiceLoader;

public class MCPluginHostDelegate {

    private static volatile MCPluginHostDelegate mInstance;
    private final FLPluginHostDelegate hostDelegate;

    private MCPluginHostDelegate() {
        hostDelegate = FLServiceLoader.load(FLPluginHostDelegate.class);
    }

    public static MCPluginHostDelegate getInstance() {
        if (mInstance == null) {
            synchronized (MCPluginHostDelegate.class) {
                if (mInstance == null) {
                    mInstance = new MCPluginHostDelegate();
                }
            }
        }
        return mInstance;
    }

    public FLPluginHostDelegate getHostDelegate() {
        return hostDelegate;
    }
}
