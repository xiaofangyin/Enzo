package com.enzo.main.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.flkit.account.UserAccountInfo;
import com.enzo.flkit.plugin.FLPluginBaseManagerInterface;
import com.enzo.flkit.plugin.FLPluginHostDelegate;
import com.enzo.main.model.manager.AccountManager;
import com.enzo.main.ui.activity.SAMainActivity;
import com.google.auto.service.AutoService;

import java.util.List;

@AutoService(FLPluginHostDelegate.class)
public class SAHostDelegateManagerImpl implements FLPluginHostDelegate {

    @Override
    public Activity getCurrentController() {
        return ActivityHelper.getManager().currentActivity();
    }

    @Override
    public UserAccountInfo getAccountInfo() {
        return new UserAccountInfo();
    }

    @Override
    public void openDrawer(Activity activity, int gravity) {
        if (activity instanceof SAMainActivity) {
            ((SAMainActivity) activity).openDrawer(gravity);
        }
    }

    @Override
    public void popToHomeController(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SAMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void releaseResources() {
        List<FLPluginBaseManagerInterface> factoryList = SAPluginManager.getInstance().getFactoryList();
        for (int i = 0; i < factoryList.size(); i++) {
            factoryList.get(i).releaseResources();
        }
    }

    @Override
    public void logout() {
        AccountManager.getInstance().logout();
    }
}
