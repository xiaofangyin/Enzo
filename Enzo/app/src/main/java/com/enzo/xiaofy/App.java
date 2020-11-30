package com.enzo.xiaofy;

import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.enzo.commonlib.env.EnvConstants;
import com.enzo.flkit.plugin.FLPluginInterface;
import com.enzo.flkit.services.FLServiceLoader;
import com.enzo.main.app.MainApplication;
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
 * **             佛祖保佑  镇类之宝              **
 * ***********************************************
 */
public class App extends MainApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initEnv();
        initFactory();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initEnv() {
        //初始化配置参数
        EnvConstants.getInstance().init(BuildConfig.PROD_ENV, BuildConfig.LOG_OPEN, "");
    }

    private void initFactory() {
        List<FLPluginInterface> factoryList = FLServiceLoader.loadList(FLPluginInterface.class);
        SAPluginManager.getInstance().init(factoryList);
    }
}
