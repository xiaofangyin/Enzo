package com.enzo.main.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.enzo.commonlib.utils.common.ActivityHelper;
import com.enzo.flkit.account.UserAccountInfo;
import com.enzo.flkit.plugin.FLHostInterface;
import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.main.model.manager.AccountManager;
import com.enzo.main.ui.activity.SAMainActivity;
import com.google.auto.service.AutoService;

import java.util.List;

@AutoService(FLHostInterface.class)
public class SAHostDelegateImpl implements FLHostInterface {

    @Override
    public Activity getCurrentController() {
        return ActivityHelper.getManager().currentActivity();
    }

    @Override
    public UserAccountInfo getAccountInfo() {
        UserAccountInfo accountInfo = new UserAccountInfo();
        accountInfo.setUid("633980");
        accountInfo.setNickName("吴彦祖666");
        accountInfo.setmAvatarUrl("http://file06.16sucai.com/2016/0425/bbdec65210c15d347dbc17d88c5535be.jpg");
        return accountInfo;
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
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    public void releaseResources() {
        List<FLPluginInterface> factoryList = SAPluginManager.getInstance().getPluginList();
        for (int i = 0; i < factoryList.size(); i++) {
            factoryList.get(i).onReleaseResources();
        }
    }

    @Override
    public void logout() {
        AccountManager.getInstance().logout();
        List<FLPluginInterface> factoryList = SAPluginManager.getInstance().getPluginList();
        for (int i = 0; i < factoryList.size(); i++) {
            factoryList.get(i).onAppLogout();
        }
    }
}
