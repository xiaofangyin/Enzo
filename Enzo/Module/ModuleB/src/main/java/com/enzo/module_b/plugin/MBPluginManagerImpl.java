package com.enzo.module_b.plugin;

import androidx.fragment.app.Fragment;

import com.enzo.flkit.account.UserAccountInfo;
import com.enzo.flkit.plugin.FLApplicationState;
import com.enzo.flkit.plugin.FLPluginBaseManagerInterface;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.module_b.ui.fragment.MBFragment;
import com.google.auto.service.AutoService;

import org.json.JSONObject;

@AutoService(FLPluginBaseManagerInterface.class)
public class MBPluginManagerImpl implements FLPluginBaseManagerInterface {

    public static final String PLUGIN_NAME = "Module B";

    @Override
    public int getPluginType() {
        return FLPluginTypeList.FL_DEVICE_TYPE_B;
    }

    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    @Override
    public FLPluginBaseObject buildNormalPluginCellModel(JSONObject data) {
        if (data.optInt("type") == FLPluginTypeList.FL_DEVICE_TYPE_B) {
            MBNormalPluginModel normalPluginModel = new MBNormalPluginModel();
            normalPluginModel.type = data.optInt("type");
            normalPluginModel.rid = data.optString("rid");
            normalPluginModel.alias = getPluginName() + " " + data.optString("alias");
            return normalPluginModel;
        }
        return null;
    }

    @Override
    public Fragment buildHomeTabFragment() {
        return new MBFragment();
    }

    @Override
    public UserAccountInfo getAccountInfo() {
        return null;
    }

    @Override
    public boolean openURL(String url) {
        return false;
    }

    @Override
    public boolean didReceiveRemoteNotification(JSONObject jMsg, FLApplicationState state) {
        return false;
    }

    @Override
    public void onAppLogout() {

    }

    @Override
    public void releaseResources() {

    }
}
