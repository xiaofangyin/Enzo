package com.enzo.module_d.plugin;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.flkit.plugin.FLPluginBaseCell;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginCellStyle;

/**
 * 文 件 名: MDNormalPluginModel
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MDNormalPluginModel extends FLPluginBaseObject {

    @Override
    public FLPluginBaseCell buildCellWithStyle(FLPluginCellStyle pluginStyle) {
        if (pluginStyle == FLPluginCellStyle.FLPluginCellStyleNormal) {
            return new MDNormalPluginItem(delegate.getContext());
        }
        return null;
    }

    @Override
    public void release() {
        LogUtil.d("MDNormalPluginModel release...");
        if (delegate != null) {
            delegate = null;
        }
    }
}
