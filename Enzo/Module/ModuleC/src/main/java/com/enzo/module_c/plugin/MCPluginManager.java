package com.enzo.module_c.plugin;

import com.enzo.flkit.plugin.FLPluginBaseManagerInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.flkit.services.Services;

import java.util.List;

public class MCPluginManager {

    private static volatile MCPluginManager mInstance;
    private FLPluginBaseManagerInterface baseManagerImpl;

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

    public FLPluginBaseManagerInterface getManager() {
        if (baseManagerImpl == null) {
            List<FLPluginBaseManagerInterface> list = Services.loadList(FLPluginBaseManagerInterface.class);
            for (int i = 0; i < list.size(); i++) {
                if (FLPluginTypeList.FL_DEVICE_TYPE_C == list.get(i).getPluginType()) {
                    baseManagerImpl = list.get(i);
                }
            }
        }
        return baseManagerImpl;
    }
}
