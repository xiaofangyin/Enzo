package com.ifenglian.module_a.plugin;

import android.support.v4.app.Fragment;

import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.flkit.FLPluginHostDelegate;
import com.ifenglian.module_a.fragment.MAFragment;

/**
 * 文 件 名: MAFactoryManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MAFactoryManager extends FLPluginFactory {

    private static MAFactoryManager mInstance;

    private MAFactoryManager() {

    }

    public static MAFactoryManager getInstance() {
        if (mInstance == null) {
            synchronized (MAFactoryManager.class) {
                if (mInstance == null) {
                    mInstance = new MAFactoryManager();
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
        return new MAFragment();
    }
}
