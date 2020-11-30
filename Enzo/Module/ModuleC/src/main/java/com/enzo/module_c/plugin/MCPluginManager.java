package com.enzo.module_c.plugin;

import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.flkit.services.FLServiceLoader;

import java.util.List;

public class MCPluginManager {

    private static volatile MCPluginManager mInstance;
    private FLPluginInterface pluginImpl;

    private MCPluginManager() {

    }

    public static MCPluginManager getInstance() {
        if (mInstance == null) {
            synchronized (MCPluginManager.class) {
                if (mInstance == null) {
                    mInstance = new MCPluginManager();
                }
            }
        }
        return mInstance;
    }

    public FLPluginInterface getManager() {
        if (pluginImpl == null) {
            List<FLPluginInterface> list = FLServiceLoader.loadList(FLPluginInterface.class);
            for (int i = 0; i < list.size(); i++) {
                if (FLPluginTypeList.FL_DEVICE_TYPE_C == list.get(i).getPluginType()) {
                    pluginImpl = list.get(i);
                }
            }
        }
        return pluginImpl;
    }
}
