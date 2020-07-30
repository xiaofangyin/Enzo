package com.enzo.flkit;

import androidx.fragment.app.Fragment;

import org.json.JSONObject;

/**
 * 文 件 名: FLPluginFactory
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class FLPluginFactory {

    public FLPluginHostDelegate hostDelegate;

    //给插件的factory传上下文
    public abstract void setHostDelegate(FLPluginHostDelegate delegate);

    public abstract FLPluginBaseObject buildNormalPluginCellModel(JSONObject data);

    public abstract Fragment buildHomeTabFragment();

    //释放资源
    public abstract void releaseResources();
}
