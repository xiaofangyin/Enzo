package com.enzo.flkit.plugin;

import com.enzo.flkit.services.FLServiceLoader;

public class FLHostDelegateManager {

    private static volatile FLHostDelegateManager mInstance;
    private final FLHostInterface hostDelegate;

    private FLHostDelegateManager() {
        hostDelegate = FLServiceLoader.load(FLHostInterface.class);
    }

    public static FLHostDelegateManager getInstance() {
        if (mInstance == null) {
            synchronized (FLHostDelegateManager.class) {
                if (mInstance == null) {
                    mInstance = new FLHostDelegateManager();
                }
            }
        }
        return mInstance;
    }

    public FLHostInterface getHostDelegate() {
        return hostDelegate;
    }
}
