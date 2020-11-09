package com.enzo.module_c.plugin;

import androidx.fragment.app.Fragment;

import com.enzo.flkit.account.UserAccountInfo;
import com.enzo.flkit.plugin.FLApplicationState;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginBaseManager;
import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.module_c.ui.fragment.MCFragment;

import org.json.JSONObject;

/**
 * 文 件 名: MCPluginManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MCPluginManager extends FLPluginBaseManager {

    private static MCPluginManager mInstance;

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

    @Override
    public String getPluginName() {
        return "Module C";
    }

    @Override
    public void setHostDelegate(FLPluginHostDelegate delegate) {
        hostDelegate = delegate;
    }

    @Override
    public FLPluginBaseObject buildNormalPluginCellModel(JSONObject data) {
        if (data.optInt("type") == FLPluginTypeList.FL_DEVICE_TYPE_C) {
            MCNormalPluginModel normalPluginModel = new MCNormalPluginModel();
            normalPluginModel.type = data.optInt("type");
            normalPluginModel.rid = data.optString("rid");
            normalPluginModel.alias = getPluginName() + " " + data.optString("alias");
            return normalPluginModel;
        }
        return null;
    }

    @Override
    public Fragment buildHomeTabFragment() {
        return new MCFragment();
    }

    @Override
    public UserAccountInfo getAccountInfo() {
        if (hostDelegate != null) {
            return hostDelegate.getAccountInfo();
        }
        return null;
    }

    @Override
    public boolean didReceiveRemoteNotification(JSONObject jMsg, FLApplicationState state) {
        return false;
    }

    @Override
    public void appLogout() {

    }

    @Override
    public void releaseResources() {

    }
}
