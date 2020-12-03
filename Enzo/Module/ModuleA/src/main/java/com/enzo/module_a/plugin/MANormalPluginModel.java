package com.enzo.module_a.plugin;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.flkit.plugin.FLPluginBaseCell;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginCellStyle;

/**
 * 文 件 名: MANormalPluginModel
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MANormalPluginModel extends FLPluginBaseObject {

    @Override
    public FLPluginBaseCell buildCellWithStyle(FLPluginCellStyle pluginStyle) {
        if (pluginStyle == FLPluginCellStyle.FLPluginCellStyleNormal) {
            return new MANormalPluginItem(delegate.getContext());
        }
        return null;
    }

    @Override
    public void release() {
        LogUtil.d("MANormalPluginModel release...");
        if (delegate != null) {
            delegate = null;
        }
    }
}
