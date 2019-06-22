package com.enzo.flkit;

/**
 * 文 件 名: FLPluginBaseObject
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class FLPluginBaseObject {

    public int type;    //设备类型1001,1002,1003,1004
    public String rid = "";     //设备标识
    public String alias = "";     //设备名称
    public FLPluginBaseObjectDelegate delegate;

    //生成相应的item
    public abstract FLPluginBaseCell buildCellWithStyle(FLPluginCellStyle pluginStyle);

    //释放插件
    public abstract void release();
}
