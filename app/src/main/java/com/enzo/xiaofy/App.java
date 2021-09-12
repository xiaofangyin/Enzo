package com.enzo.xiaofy;

import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.enzo.commonlib.base.BaseApplication;
import com.enzo.commonlib.env.EnvConstants;
import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.services.FLServiceLoader;
import com.enzo.main.model.manager.AccountManager;
import com.enzo.main.plugin.SAPluginManager;

import java.util.List;

/**
 * ***********************************************
 * **                  _oo0oo_                  **
 * **                 o8888888o                 **
 * **                 88" . "88                 **
 * **                 (| -_- |)                 **
 * **                 0\  =  /0                 **
 * **               ___/'---'\___               **
 * **            .' \\\|     |// '.             **
 * **           / \\\|||  :  |||// \\           **
 * **          / _ ||||| -:- |||||- \\          **
 * **          | |  \\\\  -  /// |   |          **
 * **          | \_|  ''\---/''  |_/ |          **
 * **          \  .-\__  '-'  __/-.  /          **
 * **        ___'. .'  /--.--\  '. .'___        **
 * **     ."" '<  '.___\_<|>_/___.' >'  "".     **
 * **    | | : '-  \'.;'\ _ /';.'/ - ' : | |    **
 * **    \  \ '_.   \_ __\ /__ _/   .-' /  /    **
 * **====='-.____'.___ \_____/___.-'____.-'=====**
 * **                  '=---='                  **
 * ***********************************************
 *                 佛祖保佑 镇类之宝
 * ***********************************************
 */
public class App extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initEnv();
        initPlugins();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initEnv() {
        //登录信息初始化
        AccountManager.getInstance().init(this);
        //初始化配置参数
        EnvConstants.getInstance().init(BuildConfig.PROD_ENV, BuildConfig.LOG_OPEN);
    }

    private void initPlugins() {
        List<FLPluginInterface> pluginList = FLServiceLoader.loadList(FLPluginInterface.class);
        SAPluginManager.getInstance().init(this, pluginList);
    }
}
