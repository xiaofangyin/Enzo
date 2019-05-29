package com.ifenglian.module_d.plugin;

import com.ifenglian.flkit.FLPluginBaseCell;
import com.ifenglian.flkit.FLPluginBaseObject;
import com.ifenglian.flkit.FLPluginCellStyle;
import com.ifenglian.flkit.FLPluginTypeList;

import org.json.JSONObject;

/**
 * 文 件 名: MDNormalPluginModel
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDNormalPluginModel extends FLPluginBaseObject {

    @Override
    public FLPluginBaseObject build(JSONObject data) {
        if (data.optInt("type") == FLPluginTypeList.FL_DEVICE_TYPE_360_REPEATER) {
            this.rid = data.optString("rid");
            this.alias = data.optString("alias");
            return this;
        }
        return null;
    }

    @Override
    public FLPluginBaseCell buildCellWithStyle(FLPluginCellStyle pluginStyle) {
        if (pluginStyle == FLPluginCellStyle.FLPluginCellStyleNormal) {
            return new MDNormalPluginItem(delegate.getContext());
        }
        return null;
    }
}
