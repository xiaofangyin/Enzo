package com.enzo.flkit.plugin;

import com.enzo.flkit.services.FLServiceLoader;

public class FLPluginHostDelegateImpl {

    private static volatile FLPluginHostDelegateImpl mInstance;
    private final FLPluginHostDelegate hostDelegate;

    private FLPluginHostDelegateImpl() {
        hostDelegate = FLServiceLoader.load(FLPluginHostDelegate.class);
    }

    public static FLPluginHostDelegateImpl getInstance() {
        if (mInstance == null) {
            synchronized (FLPluginHostDelegateImpl.class) {
                if (mInstance == null) {
                    mInstance = new FLPluginHostDelegateImpl();
                }
            }
        }
        return mInstance;
    }

    public FLPluginHostDelegate getHostDelegate() {
        return hostDelegate;
    }
}
