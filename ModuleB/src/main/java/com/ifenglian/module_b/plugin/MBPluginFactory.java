package com.ifenglian.module_b.plugin;

import android.support.v4.app.Fragment;

import com.ifenglian.flkit.FLPluginBaseObject;
import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.flkit.FLPluginHostDelegate;
import com.ifenglian.flkit.FLPluginTypeList;
import com.ifenglian.module_b.ui.fragment.MBFragment;

import org.json.JSONObject;

/**
 * 文 件 名: MBPluginFactory
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MBPluginFactory extends FLPluginFactory {

    private static MBPluginFactory mInstance;

    private MBPluginFactory() {

    }

    public static MBPluginFactory getInstance() {
        if (mInstance == null) {
            synchronized (MBPluginFactory.class) {
                if (mInstance == null) {
                    mInstance = new MBPluginFactory();
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
        if (data.optInt("type") == FLPluginTypeList.FL_DEVICE_TYPE_B) {
            MBNormalPluginModel normalPluginModel = new MBNormalPluginModel();
            normalPluginModel.type = data.optInt("type");
            normalPluginModel.rid = data.optString("rid");
            normalPluginModel.alias = data.optString("alias");
            return normalPluginModel;
        }
        return null;
    }

    @Override
    public Fragment buildHomeTabFragment() {
        return new MBFragment();
    }

    @Override
    public void releaseResources() {

    }
}
