package com.enzo.module_d.plugin;

import com.enzo.flkit.plugin.FLPluginBaseManagerInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.flkit.services.Services;

import java.util.List;

public class MDPluginManager {

    private static volatile MDPluginManager mInstance;
    private FLPluginBaseManagerInterface baseManagerImpl;

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

    public FLPluginBaseManagerInterface getManager() {
        if (baseManagerImpl == null) {
            List<FLPluginBaseManagerInterface> list = Services.loadList(FLPluginBaseManagerInterface.class);
            for (int i = 0; i < list.size(); i++) {
                if (FLPluginTypeList.FL_DEVICE_TYPE_D == list.get(i).getPluginType()) {
                    baseManagerImpl = list.get(i);
                }
            }
        }
        return baseManagerImpl;
    }
}
