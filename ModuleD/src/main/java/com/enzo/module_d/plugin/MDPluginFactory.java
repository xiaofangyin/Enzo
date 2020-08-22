package com.enzo.module_d.plugin;

import androidx.fragment.app.Fragment;

import com.enzo.flkit.account.AccountInfo;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginFactory;
import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.flkit.plugin.FLPluginTypeList;
import com.enzo.module_d.ui.fragment.MDFragment;

import org.json.JSONObject;

/**
 * 文 件 名: MDPluginFactory
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDPluginFactory extends FLPluginFactory {

    private static MDPluginFactory mInstance;

    private MDPluginFactory() {

    }

    public static MDPluginFactory getInstance() {
        if (mInstance == null) {
            synchronized (MDPluginFactory.class) {
                if (mInstance == null) {
                    mInstance = new MDPluginFactory();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void setHostDelegate(FLPluginHostDelegate delegate) {
        hostDelegate = delegate;
    }

    @Override
    public FLPluginBaseObject buildNormalPluginCellModel(JSONObject data) {
        if (data.optInt("type") == FLPluginTypeList.FL_DEVICE_TYPE_D) {
            MDNormalPluginModel normalPluginModel = new MDNormalPluginModel();
            normalPluginModel.type = data.optInt("type");
            normalPluginModel.rid = data.optString("rid");
            normalPluginModel.alias = data.optString("alias");
            return normalPluginModel;
        }
        return null;
    }

    @Override
    public Fragment buildHomeTabFragment() {
        return new MDFragment();
    }

    @Override
    public AccountInfo getAccountInfo() {
        if (hostDelegate != null) {
            return hostDelegate.getAccountInfo();
        }
        return null;
    }

    @Override
    public void releaseResources() {

    }
}
