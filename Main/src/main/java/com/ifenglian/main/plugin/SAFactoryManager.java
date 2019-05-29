package com.ifenglian.main.plugin;

import android.content.Context;

import com.enzo.commonlib.base.BaseApplication;
import com.ifenglian.flkit.FLPluginFactory;

import java.util.List;

/**
 * 文 件 名: SAFactoryManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SAFactoryManager {

    private static SAFactoryManager mInstance;
    private List<FLPluginFactory> mFactoryList;

    private SAFactoryManager() {

    }

    public static SAFactoryManager getInstance() {
        if (mInstance == null) {
            synchronized (SAFactoryManager.class) {
                if (mInstance == null) {
                    mInstance = new SAFactoryManager();
                }
            }
        }
        return mInstance;
    }

    public Context getContext() {
        return BaseApplication.getInstance();
    }

    public void init(List<FLPluginFactory> factoryList) {
        this.mFactoryList = factoryList;
        SAHostDelegateManager.getInstance().initFactories(factoryList);
    }

    public List<FLPluginFactory> getFactoryList() {
        return mFactoryList;
    }
}
