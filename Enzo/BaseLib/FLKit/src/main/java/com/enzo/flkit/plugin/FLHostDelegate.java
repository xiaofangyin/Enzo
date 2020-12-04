package com.enzo.flkit.plugin;

import com.enzo.flkit.services.FLServiceLoader;

public class FLHostDelegate {

    private static volatile FLHostDelegate mInstance;
    private final FLHostInterface hostDelegate;

    private FLHostDelegate() {
        hostDelegate = FLServiceLoader.load(FLHostInterface.class);
    }

    public static FLHostDelegate getInstance() {
        if (mInstance == null) {
            synchronized (FLHostDelegate.class) {
                if (mInstance == null) {
                    mInstance = new FLHostDelegate();
                }
            }
        }
        return mInstance;
    }

    public FLHostInterface getHostDelegate() {
        return hostDelegate;
    }
}
