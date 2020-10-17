package com.enzo.flkit.plugin;

import androidx.fragment.app.Fragment;

import com.enzo.flkit.account.UserAccountInfo;

import org.json.JSONObject;

/**
 * 文 件 名: FLPluginFactory
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class FLPluginFactory {

    public FLPluginHostDelegate hostDelegate;

    //获取插件名称
    public abstract String getPluginName();

    //给插件的factory传上下文
    public abstract void setHostDelegate(FLPluginHostDelegate delegate);

    public abstract FLPluginBaseObject buildNormalPluginCellModel(JSONObject data);

    public abstract Fragment buildHomeTabFragment();

    public abstract UserAccountInfo getAccountInfo();

    //推送相关
    public abstract boolean didReceiveRemoteNotification(JSONObject jMsg, FLApplicationState state);

    //用户退出后会通知各个插件做相应的处理
    public abstract void appLogout();

    //释放资源
    public abstract void releaseResources();
}
