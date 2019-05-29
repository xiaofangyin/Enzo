package com.ifenglian.flkit;

import android.content.Context;
import android.widget.FrameLayout;

public class FLPluginBaseCell extends FrameLayout {

    //插件的上下文
    public FLPluginCellDelegate cellDelegate;

    public FLPluginBaseCell(Context context) {
        super(context);
    }

    //根据model生成对应的view
    public void layoutWithModel(FLPluginBaseObject model) {

    }

    //cell的点击事件
    public void cellPressed() {

    }

}
