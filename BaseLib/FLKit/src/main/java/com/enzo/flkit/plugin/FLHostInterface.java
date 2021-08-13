package com.enzo.flkit.plugin;

import android.app.Activity;
import android.content.Context;

import com.enzo.flkit.account.UserAccountInfo;

/**
 * 文 件 名: FLPluginHostDelegate
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 * 由主工程实现，供各个组件调用
 */
public interface FLHostInterface {

    //返回当前显示的activity
    Activity getCurrentController();

    UserAccountInfo getAccountInfo();

    void openDrawer(Activity activity, int gravity);

    //跳转到宿主的家庭页
    void popToHomeController(Context context);

    //释放资源
    void releaseResources();

    void logout();
}