package com.enzo.module_d.plugin;

import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.flkit.services.FLServiceLoader;

import java.util.List;

public class MDPluginManager {

    private static volatile MDPluginManager mInstance;
    private FLPluginInterface pluginImpl;

    private MDPluginManager() {

    }

    public static MDPluginManager getInstance() {
        if (mInstance == null) {
            synchronized (MDPluginManager.class) {
                if (mInstance == null) {
                    mInstance = new MDPluginManager();
                }
            }
        }
        return mInstance;
    }

    public FLPluginInterface getManager() {
        if (pluginImpl == null) {
            List<FLPluginInterface> list = FLServiceLoader.loadList(FLPluginInterface.class);
            for (int i = 0; i < list.size(); i++) {
                if (FLPluginTypeList.FL_DEVICE_TYPE_D == list.get(i).getPluginType()) {
                    pluginImpl = list.get(i);
                }
            }
        }
        return pluginImpl;
    }
}
