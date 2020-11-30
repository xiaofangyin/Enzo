package com.enzo.module_a.plugin;

import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.flkit.services.FLServiceLoader;

import java.util.List;

public class MAPluginManager {

    private static volatile MAPluginManager mInstance;
    private FLPluginInterface pluginImpl;

    private MAPluginManager() {

    }

    public static MAPluginManager getInstance() {
        if (mInstance == null) {
            synchronized (MAPluginManager.class) {
                if (mInstance == null) {
                    mInstance = new MAPluginManager();
                }
            }
        }
        return mInstance;
    }

    public FLPluginInterface getManager() {
        if (pluginImpl == null) {
            List<FLPluginInterface> list = FLServiceLoader.loadList(FLPluginInterface.class);
            for (int i = 0; i < list.size(); i++) {
                if (FLPluginTypeList.FL_DEVICE_TYPE_A == list.get(i).getPluginType()) {
                    pluginImpl = list.get(i);
                }
            }
        }
        return pluginImpl;
    }
}
