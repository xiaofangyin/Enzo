package com.enzo.main.plugin;

import com.enzo.flkit.FLPluginFactory;

import java.util.List;

/**
 * 文 件 名: SAFactoryManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
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

    public void init(List<FLPluginFactory> factoryList) {
        this.mFactoryList = factoryList;
        SAHostDelegateManager.getInstance().initFactories(factoryList);
    }

    public List<FLPluginFactory> getFactoryList() {
        return mFactoryList;
    }
}
