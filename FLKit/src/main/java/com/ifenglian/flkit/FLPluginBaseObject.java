package com.ifenglian.flkit;


import org.json.JSONObject;

public class FLPluginBaseObject {

    public int type;    //设备类型1001,1002,1003,1004
    public String rid = "";     //设备标识
    public FLPluginBaseObjectDelegate delegate;

    //将服务器返回的json数据转换成对应的model
    public FLPluginBaseObject build(JSONObject data) {
        return null;
    }

    //生成相应的item
    public FLPluginBaseCell buildCellWithStyle(FLPluginCellStyle pluginStyle) {
        return null;
    }

    //释放插件
    public void remove() {

    }
}
