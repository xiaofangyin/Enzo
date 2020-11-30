package com.enzo.flkit.plugin;

import androidx.fragment.app.Fragment;

import com.enzo.flkit.account.UserAccountInfo;

import org.json.JSONObject;

/**
 * 文 件 名: FLPluginBaseManagerInterface
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public interface FLPluginInterface {

    int getPluginType();

    //获取插件名称
    String getPluginName();

    FLPluginBaseObject buildNormalPluginCellModel(JSONObject data);

    Fragment buildHomeTabFragment();

    boolean openURL(String url);

    //推送相关
    boolean didReceiveRemoteNotification(JSONObject jMsg, FLApplicationState state);

    //用户退出后会通知各个插件做相应的处理
    void onAppLogout();

    //释放资源
    void releaseResources();
}
