package com.ifenglian.module_c.plugin;

import android.support.v4.app.Fragment;

import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.flkit.FLPluginHostDelegate;
import com.ifenglian.module_c.fragment.MCFragment;

/**
 * 文 件 名: MCFactoryManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MCFactoryManager extends FLPluginFactory {

    private static MCFactoryManager mInstance;

    private MCFactoryManager() {

    }

    public static MCFactoryManager getInstance() {
        if (mInstance == null) {
            synchronized (MCFactoryManager.class) {
                if (mInstance == null) {
                    mInstance = new MCFactoryManager();
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
    public Fragment getFragment() {
        return new MCFragment();
    }
}
