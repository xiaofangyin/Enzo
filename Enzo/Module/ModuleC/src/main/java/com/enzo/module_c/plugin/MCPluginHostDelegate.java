package com.enzo.module_c.plugin;

import android.app.Activity;
import android.content.Context;

import com.enzo.flkit.account.UserAccountInfo;
import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.flkit.services.FLServiceLoader;

public class MCPluginHostDelegate implements FLPluginHostDelegate {

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

    @Override
    public Activity getCurrentController() {
        if (hostDelegate != null) {
            return hostDelegate.getCurrentController();
        }
        return null;
    }

    @Override
    public UserAccountInfo getAccountInfo() {
        if (hostDelegate != null) {
            return hostDelegate.getAccountInfo();
        }
        return null;
    }

    @Override
    public void openDrawer(Activity activity, int gravity) {
        if (hostDelegate != null) {
            hostDelegate.openDrawer(activity, gravity);
        }
    }

    @Override
    public void popToHomeController(Context context) {
        if (hostDelegate != null) {
            hostDelegate.popToHomeController(context);
        }
    }

    @Override
    public void releaseResources() {
        if (hostDelegate != null) {
            hostDelegate.releaseResources();
        }
    }

    @Override
    public void logout() {
        if (hostDelegate != null) {
            hostDelegate.logout();
        }
    }
}
