package com.enzo.module_b.plugin;

import com.enzo.flkit.plugin.FLPluginBaseManagerInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.flkit.services.Services;

import java.util.List;

public class MBPluginManager {

    private static volatile MBPluginManager mInstance;
    private FLPluginBaseManagerInterface baseManagerImpl;

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

    public FLPluginBaseManagerInterface getManager() {
        if (baseManagerImpl == null) {
            List<FLPluginBaseManagerInterface> list = Services.loadList(FLPluginBaseManagerInterface.class);
            for (int i = 0; i < list.size(); i++) {
                if (FLPluginTypeList.FL_DEVICE_TYPE_B == list.get(i).getPluginType()) {
                    baseManagerImpl = list.get(i);
                }
            }
        }
        return baseManagerImpl;
    }
}
