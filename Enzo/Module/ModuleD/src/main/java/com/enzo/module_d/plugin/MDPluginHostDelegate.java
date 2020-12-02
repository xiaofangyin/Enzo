package com.enzo.module_d.plugin;

import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.flkit.services.FLServiceLoader;

public class MDPluginHostDelegate {

    private static volatile MDPluginHostDelegate mInstance;
    private final FLPluginHostDelegate hostDelegate;

    private MDPluginHostDelegate() {
        hostDelegate = FLServiceLoader.load(FLPluginHostDelegate.class);
    }

    public static MDPluginHostDelegate getInstance() {
        if (mInstance == null) {
            synchronized (MDPluginHostDelegate.class) {
                if (mInstance == null) {
                    mInstance = new MDPluginHostDelegate();
                }
            }
        }
        return mInstance;
    }

    public FLPluginHostDelegate getHostDelegate() {
        return hostDelegate;
    }
}
