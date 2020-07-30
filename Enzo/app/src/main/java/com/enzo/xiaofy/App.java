package com.enzo.xiaofy;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.enzo.commonlib.env.EnvConstants;
import com.enzo.commonlib.utils.common.PhoneUtils;
import com.enzo.commonlib.utils.crashlib.CrashManager;
import com.enzo.flkit.FLPluginFactory;
import com.enzo.main.plugin.SAFactoryManager;
import com.enzo.module_a.plugin.MAPluginFactory;
import com.enzo.module_b.plugin.MBPluginFactory;
import com.enzo.module_c.plugin.MCPluginFactory;
import com.enzo.module_d.plugin.MDPluginFactory;

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
 * **              佛祖保佑  镇类之宝            **
 * ***********************************************
 * <p>
 * 文 件 名: App
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initEnv();
        initFactory();
    }

    private void initEnv() {
        PhoneUtils.getInstance().init(this);

        MultiDex.install(this);

        EnvConstants.getInstance().init(BuildConfig.PROD_ENV, BuildConfig.LOG_OPEN, "");

        CrashManager.getInstance().init(this, BuildConfig.DEBUG);
    }

    private void initFactory() {
        List<FLPluginFactory> factoryList = new ArrayList<>();
        factoryList.add(MAPluginFactory.getInstance());
        factoryList.add(MBPluginFactory.getInstance());
        factoryList.add(MCPluginFactory.getInstance());
        factoryList.add(MDPluginFactory.getInstance());
        SAFactoryManager.getInstance().init(this, factoryList);
    }
}
