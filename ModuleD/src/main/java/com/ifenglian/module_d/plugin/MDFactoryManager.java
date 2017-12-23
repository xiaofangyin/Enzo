package com.ifenglian.module_d.plugin;

import android.support.v4.app.Fragment;

import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.flkit.FLPluginHostDelegate;
import com.ifenglian.module_d.fragment.MDFragment2;

/**
 * 文 件 名: MDFactoryManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDFactoryManager extends FLPluginFactory {

    private static MDFactoryManager mInstance;

    private MDFactoryManager() {

    }

    public static MDFactoryManager getInstance() {
        if (mInstance == null) {
            synchronized (MDFactoryManager.class) {
                if (mInstance == null) {
                    mInstance = new MDFactoryManager();
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
        return new MDFragment2();
    }
}
