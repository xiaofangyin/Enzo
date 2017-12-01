package com.ifenglian.enzo;

import com.ifenglian.commonlib.base.BaseApplication;
import com.ifenglian.flkit.FLPluginFactory;
import com.ifenglian.main.plugin.SAFactoryManager;
import com.ifenglian.module_a.plugin.MAFactoryManager;
import com.ifenglian.module_b.plugin.MBFactoryManager;
import com.ifenglian.module_c.plugin.MCFactoryManager;
import com.ifenglian.module_d.plugin.MDFactoryManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: App
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofy@ifenglian.com
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initFactory();
    }

    private void initFactory() {
        List<FLPluginFactory> factoryList = new ArrayList<>();
        factoryList.add(MAFactoryManager.getInstance());
        factoryList.add(MBFactoryManager.getInstance());
        factoryList.add(MCFactoryManager.getInstance());
        factoryList.add(MDFactoryManager.getInstance());
        SAFactoryManager.getInstance().init(App.this,factoryList);
    }
}
