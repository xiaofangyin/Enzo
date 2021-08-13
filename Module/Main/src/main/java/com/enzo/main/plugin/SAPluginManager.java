package com.enzo.main.plugin;

import android.content.Context;

import com.enzo.flkit.plugin.FLPluginInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: SAPluginManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAPluginManager {

    private static SAPluginManager mInstance;
    private final List<FLPluginInterface> mPluginList = new ArrayList<>();

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

    public void init(Context context, List<FLPluginInterface> list) {
        mPluginList.clear();
        if (list != null) {
            for (FLPluginInterface pluginInterface : list) {
                pluginInterface.init(context);
            }
            mPluginList.addAll(list);
        }
    }

    public List<FLPluginInterface> getPluginList() {
        return mPluginList;
    }
}
