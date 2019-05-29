package com.ifenglian.flkit;

import android.content.Context;

import java.util.List;

public interface FLPluginHostDelegate {

    void initFactories(List<FLPluginFactory> factoryList);

    Context getKeyWindow();

    //插件调用会跳转到宿主的家庭页
    boolean popToHomeControllerWithAnimated(Context context, boolean animated);

    //插件调用会跳转到宿主的添加设备页
    boolean popToAddDevicesControllerWithAnimated(Context context, boolean animated);
}