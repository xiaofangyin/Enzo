package com.enzo.module_b.plugin;

import androidx.fragment.app.Fragment;

import com.enzo.flkit.account.UserAccountInfo;
import com.enzo.flkit.plugin.FLApplicationState;
import com.enzo.flkit.plugin.FLPluginBaseManager;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.module_b.ui.fragment.MBFragment;

import org.json.JSONObject;

/**
 * 文 件 名: MBPluginManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MBPluginManager extends FLPluginBaseManager {

    private static volatile MBPluginManager mInstance;

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

    @Override
    public String getPluginName() {
        return "Module B";
    }

    @Override
    public void setHostDelegate(FLPluginHostDelegate delegate) {
        hostDelegate = delegate;
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
    public void onAppLogout() {

    }

    @Override
    public void releaseResources() {

    }
}
