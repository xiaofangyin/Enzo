package com.enzo.main.plugin;

import com.enzo.flkit.plugin.FLPluginInterface;

import java.util.List;

/**
 * 文 件 名: SAPluginManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAPluginManager {

    private static SAPluginManager mInstance;
    private List<FLPluginInterface> mFactoryList;

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

    public void init( List<FLPluginInterface> factoryList) {
        this.mFactoryList = factoryList;
    }

    public List<FLPluginInterface> getFactoryList() {
        return mFactoryList;
    }
}
