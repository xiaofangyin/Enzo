package com.ifenglian.flkit;

import android.content.Context;

import java.util.List;

/**
 * Created by clg on 2016/6/16.
 */
public interface FLPluginHostDelegate {

    Context getKeyWindow();

    void initFactories(List<FLPluginFactory> factoryList);

    //插件调用会跳转到宿主的家庭页
    boolean popToHomeControllerWithAnimated(Context context, boolean animated);

}