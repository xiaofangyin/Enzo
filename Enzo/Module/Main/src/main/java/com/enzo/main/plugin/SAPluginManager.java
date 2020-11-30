package com.enzo.main.plugin;

import com.enzo.flkit.plugin.FLPluginBaseManagerInterface;

import java.util.List;

/**
 * 文 件 名: SAPluginManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAPluginManager {

    private static SAPluginManager mInstance;
    private List<FLPluginBaseManagerInterface> mFactoryList;

    private SAPluginManager() {

    }

    public static SAPluginManager getInstance() {
        if (mInstance == null) {
            synchronized (SAPluginManager.class) {
                if (mInstance == null) {
                    mInstance = new SAPluginManager();
                }
            }
        }
        return mInstance;
    }

    public void init( List<FLPluginBaseManagerInterface> factoryList) {
        this.mFactoryList = factoryList;
    }

    public List<FLPluginBaseManagerInterface> getFactoryList() {
        return mFactoryList;
    }
}
