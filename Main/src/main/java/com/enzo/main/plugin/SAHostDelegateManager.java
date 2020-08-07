package com.enzo.main.plugin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.flkit.plugin.FLPluginFactory;
import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.main.ui.activity.SAAddDeviceActivity;
import com.enzo.main.ui.activity.SAMainActivity;

import java.util.List;

/**
 * 文 件 名: SAHostDelegateManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAHostDelegateManager implements FLPluginHostDelegate {

    private Application application;
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
        return application;
    }

    @Override
    public Activity getCurrentController() {
        return ActivityHelper.getManager().currentActivity();
    }

    @Override
    public void openDrawer(Activity activity, int gravity) {
        if (activity instanceof SAMainActivity) {
            ((SAMainActivity) activity).openDrawer(gravity);
        }
    }

    @Override
    public void initFactories(Application application, List<FLPluginFactory> factoryList) {
        this.application = application;
        for (int i = 0; i < factoryList.size(); i++) {
            factoryList.get(i).setHostDelegate(this);
        }
    }

    @Override
    public boolean popToHomeController(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SAMainActivity.class);
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
