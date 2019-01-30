package com.ifenglian.main.plugin;

import android.annotation.SuppressLint;
import android.content.Context;

import com.ifenglian.flkit.FLPluginFactory;

import java.util.List;

/**
 * 文 件 名: SAFactoryManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SAFactoryManager {

    @SuppressLint("StaticFieldLeak")
    private static SAFactoryManager mInstance;
    private Context mContext;
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
        return mContext;
    }

    public void init(Context context, List<FLPluginFactory> factoryList) {
        this.mContext = context;
        this.mFactoryList = factoryList;
        SAHostDelegateManager.getInstance().initFactories(factoryList);
    }

    public List<FLPluginFactory> getFactoryList() {
        return mFactoryList;
    }
}
