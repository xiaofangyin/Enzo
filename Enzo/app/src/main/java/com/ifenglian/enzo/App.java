package com.ifenglian.enzo;

import com.enzo.commonlib.base.BaseApplication;
import com.enzo.commonlib.env.EnvConstants;
import com.enzo.commonlib.utils.crashlib.CrashManager;
import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.main.plugin.SAFactoryManager;
import com.ifenglian.module_a.plugin.MAFactoryManager;
import com.ifenglian.module_b.plugin.MBFactoryManager;
import com.ifenglian.module_c.plugin.MCFactoryManager;
import com.ifenglian.module_d.plugin.MDFactoryManager;

import java.util.ArrayList;
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
 * **              佛祖保佑  镇类之宝              **
 * ***********************************************
 * <p>
 * 文 件 名: App
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initEnv();
        initFactory();
    }

    private void initEnv() {
        EnvConstants.getInstance().init(BuildConfig.PROD_ENV, BuildConfig.LOG_OPEN, "");

        CrashManager.getInstance().init(this, BuildConfig.DEBUG);
    }

    private void initFactory() {
        List<FLPluginFactory> factoryList = new ArrayList<>();
        factoryList.add(MAFactoryManager.getInstance());
        factoryList.add(MBFactoryManager.getInstance());
        factoryList.add(MCFactoryManager.getInstance());
        factoryList.add(MDFactoryManager.getInstance());
        SAFactoryManager.getInstance().init(App.this, factoryList);
    }
}
