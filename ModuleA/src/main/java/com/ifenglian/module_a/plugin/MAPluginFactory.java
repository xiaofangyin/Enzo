package com.ifenglian.module_a.plugin;

import android.support.v4.app.Fragment;

import com.ifenglian.flkit.FLPluginBaseObject;
import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.flkit.FLPluginHostDelegate;
import com.ifenglian.flkit.FLPluginTypeList;
import com.ifenglian.module_a.ui.fragment.MAFragment;

import org.json.JSONObject;

/**
 * 文 件 名: MAPluginFactory
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MAPluginFactory extends FLPluginFactory {

    private static MAPluginFactory mInstance;

    private MAPluginFactory() {

    }

    public static MAPluginFactory getInstance() {
        if (mInstance == null) {
            synchronized (MAPluginFactory.class) {
                if (mInstance == null) {
                    mInstance = new MAPluginFactory();
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
        if (data.optInt("type") == FLPluginTypeList.FL_DEVICE_TYPE_A) {
            MANormalPluginModel normalPluginModel = new MANormalPluginModel();
            normalPluginModel.type = data.optInt("type");
            normalPluginModel.rid = data.optString("rid");
            normalPluginModel.alias = data.optString("alias");
            return normalPluginModel;
        }
        return null;
    }

    @Override
    public Fragment buildHomeTabFragment() {
        return new MAFragment();
    }

    @Override
    public void releaseResources() {

    }
}
