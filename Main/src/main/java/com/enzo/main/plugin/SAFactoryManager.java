package com.enzo.main.plugin;

import android.app.Application;

import com.enzo.flkit.plugin.FLPluginFactory;

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

    public void init(Application application, List<FLPluginFactory> factoryList) {
        this.mFactoryList = factoryList;
        SAHostDelegateManager.getInstance().initFactories(application, factoryList);
    }

    public List<FLPluginFactory> getFactoryList() {
        return mFactoryList;
    }
}
