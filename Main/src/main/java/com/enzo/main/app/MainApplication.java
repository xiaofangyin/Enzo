package com.enzo.main.app;

import com.enzo.commonlib.base.BaseApplication;
import com.enzo.main.model.manager.AccountManager;

/**
 * 文 件 名: MainApplication
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/12
 * 邮   箱: xiaofywork@163.com
 */
public class MainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //登录信息初始化
        AccountManager.getInstance().init(this);
    }
}
