package com.enzo.flkit;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * 文 件 名: FLPluginBaseCell
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public abstract class FLPluginBaseCell extends FrameLayout {

    public FLPluginBaseCell(Context context) {
        super(context);
    }

    //返回设备类型
    public abstract int getType();

    //根据model生成对应的view
    public abstract void layoutWithModel(FLPluginBaseObject model);

}
