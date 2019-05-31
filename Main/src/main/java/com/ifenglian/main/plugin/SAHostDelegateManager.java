package com.ifenglian.main.plugin;

import android.content.Context;
import android.content.Intent;

import com.enzo.commonlib.base.BaseApplication;
import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.flkit.FLPluginHostDelegate;
import com.ifenglian.main.ui.activity.SAAddDeviceActivity;
import com.ifenglian.main.ui.activity.SAMainActivity;

import java.util.List;

/**
 * 文 件 名: SAHostDelegateManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SAHostDelegateManager implements FLPluginHostDelegate {

    private static SAHostDelegateManager mInstance;

    private SAHostDelegateManager() {

    }

    public static SAHostDelegateManager getInstance() {
        if (mInstance == null) {
            synchronized (SAHostDelegateManager.class) {
                if (mInstance == null) {
                    mInstance = new SAHostDelegateManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public Context getApplicationContext() {
        return BaseApplication.getInstance();
    }

    @Override
    public void initFactories(List<FLPluginFactory> factoryList) {
        for (int i = 0; i < factoryList.size(); i++) {
            factoryList.get(i).setHostDelegate(this);
        }
    }

    @Override
    public boolean popToHomeControllerWithAnimated(Context context, boolean animated) {
        Intent intent = new Intent();
        intent.setClass(context, SAMainActivity.class);
        context.startActivity(intent);
        return true;
    }

    @Override
    public boolean popToAddDevicesControllerWithAnimated(Context context, boolean animated) {
        Intent intent = new Intent();
        intent.setClass(context, SAAddDeviceActivity.class);
        context.startActivity(intent);
        return true;
    }

    @Override
    public void releaseResources() {
        List<FLPluginFactory> factoryList = SAFactoryManager.getInstance().getFactoryList();
        for (int i = 0; i < factoryList.size(); i++) {
            factoryList.get(i).releaseResources();
        }
    }
}
