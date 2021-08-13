package com.enzo.module_b.plugin;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.flkit.plugin.FLPluginBaseCell;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginCellStyle;

/**
 * 文 件 名: MBNormalPluginModel
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class MBNormalPluginModel extends FLPluginBaseObject {

    @Override
    public FLPluginBaseCell buildCellWithStyle(FLPluginCellStyle pluginStyle) {
        if (pluginStyle == FLPluginCellStyle.FLPluginCellStyleNormal) {
            return new MBNormalPluginItem(delegate.getContext());
        }
        return null;
    }

    @Override
    public void release() {
        if (delegate != null) {
            delegate = null;
        }
        LogUtil.d("MBNormalPluginModel release...");
    }
}
