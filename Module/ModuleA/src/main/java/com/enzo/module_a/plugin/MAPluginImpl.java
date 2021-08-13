package com.enzo.module_a.plugin;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.enzo.flkit.plugin.FLApplicationState;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginBaseObjectDelegate;
import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.module_a.ui.fragment.MAFragment;
import com.google.auto.service.AutoService;

import org.json.JSONObject;

@AutoService(FLPluginInterface.class)
public class MAPluginImpl implements FLPluginInterface {

    public static final String PLUGIN_NAME = "Module A";

    @Override
    public void init(Context context) {

    }

    @Override
    public int getPluginType() {
        return FLPluginTypeList.FL_DEVICE_TYPE_A;
    }

    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    @Override
    public FLPluginBaseObject buildNormalPluginCellModel(FLPluginBaseObjectDelegate delegate, JSONObject data) {
        if (data.optInt("type") == FLPluginTypeList.FL_DEVICE_TYPE_A) {
            MANormalPluginModel normalPluginModel = new MANormalPluginModel();
            normalPluginModel.delegate = delegate;
            normalPluginModel.type = data.optInt("type");
            normalPluginModel.rid = data.optString("rid");
            normalPluginModel.alias = getPluginName() + " " + data.optString("alias");
            return normalPluginModel;
        }
        return null;
    }

    @Override
    public Fragment buildHomeTabFragment() {
        return new MAFragment();
    }

    @Override
    public boolean openURL(String url) {
        return false;
    }

    @Override
    public boolean onDidReceiveRemoteNotification(JSONObject jMsg, FLApplicationState state) {
        return false;
    }

    @Override
    public void onAppLogout() {

    }

    @Override
    public void onReleaseResources() {

    }
}
