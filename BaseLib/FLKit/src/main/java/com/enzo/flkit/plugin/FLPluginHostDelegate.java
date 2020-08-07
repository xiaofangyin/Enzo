package com.enzo.flkit.plugin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.List;

/**
 * 文 件 名: FLPluginHostDelegate
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public interface FLPluginHostDelegate {

    void initFactories(Application application, List<FLPluginFactory> factoryList);

    //获取Application Context
    Context getApplicationContext();

    //返回当前显示的activity
    Activity getCurrentController();

    void openDrawer(Activity activity,int gravity);

    //跳转到宿主的家庭页
    boolean popToHomeController(Context context);

    //释放资源
    void releaseResources();
}