package com.enzo.module_a.plugin;

import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.flkit.services.FLServiceLoader;

public class MAPluginHostDelegate {

    private static volatile MAPluginHostDelegate mInstance;
    private final FLPluginHostDelegate hostDelegate;

    private MAPluginHostDelegate() {
        hostDelegate = FLServiceLoader.load(FLPluginHostDelegate.class);
    }

    public static MAPluginHostDelegate getInstance() {
        if (mInstance == null) {
            synchronized (MAPluginHostDelegate.class) {
                if (mInstance == null) {
                    mInstance = new MAPluginHostDelegate();
                }
            }
        }
        return mInstance;
    }

    public FLPluginHostDelegate getHostDelegate() {
        return hostDelegate;
    }
}
