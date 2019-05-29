package com.ifenglian.module_b.plugin;

import android.support.v4.app.Fragment;

import com.ifenglian.flkit.FLPluginBaseObject;
import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.flkit.FLPluginHostDelegate;
import com.ifenglian.module_b.fragment.MBFragment;

import org.json.JSONObject;

/**
 * 文 件 名: MBPluginFactory
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
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
        return new MBNormalPluginModel().build(data);
    }

    @Override
    public Fragment getFragment() {
        return new MBFragment();
    }
}
