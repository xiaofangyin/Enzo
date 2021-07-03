package com.enzo.module_d.plugin;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.enzo.flkit.plugin.FLApplicationState;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginBaseObjectDelegate;
import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.module_d.model.db.ContentResolverManager;
import com.enzo.module_d.ui.fragment.MDFragment;
import com.google.auto.service.AutoService;

import org.json.JSONObject;

@AutoService(FLPluginInterface.class)
public class MDPluginImpl implements FLPluginInterface {

    public static final String PLUGIN_NAME = "Module D";

    @Override
    public void init(Context context) {
        ContentResolverManager.getInstance().init(context);
    }

    @Override
    public int getPluginType() {
        return FLPluginTypeList.FL_DEVICE_TYPE_D;
    }

    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    @Override
    public FLPluginBaseObject buildNormalPluginCellModel(FLPluginBaseObjectDelegate delegate, JSONObject data) {
        if (data.optInt("type") == FLPluginTypeList.FL_DEVICE_TYPE_D) {
            MDNormalPluginModel normalPluginModel = new MDNormalPluginModel();
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
        return new MDFragment();
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
        ContentResolverManager.getInstance().onDestroy();
    }
}
