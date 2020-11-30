package com.enzo.module_b.plugin;

import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.flkit.services.FLServiceLoader;

import java.util.List;

public class MBPluginManager {

    private static volatile MBPluginManager mInstance;
    private FLPluginInterface pluginImpl;

    private MBPluginManager() {

    }

    public static MBPluginManager getInstance() {
        if (mInstance == null) {
            synchronized (MBPluginManager.class) {
                if (mInstance == null) {
                    mInstance = new MBPluginManager();
                }
            }
        }
        return mInstance;
    }

    public FLPluginInterface getManager() {
        if (pluginImpl == null) {
            List<FLPluginInterface> list = FLServiceLoader.loadList(FLPluginInterface.class);
            for (int i = 0; i < list.size(); i++) {
                if (FLPluginTypeList.FL_DEVICE_TYPE_B == list.get(i).getPluginType()) {
                    pluginImpl = list.get(i);
                }
            }
        }
        return pluginImpl;
    }
}
