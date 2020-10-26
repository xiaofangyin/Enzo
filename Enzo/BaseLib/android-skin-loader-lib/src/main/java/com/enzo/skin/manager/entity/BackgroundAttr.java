package com.enzo.skin.manager.entity;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.enzo.skin.manager.loader.SkinManager;
import com.enzo.skin.manager.util.L;

public class BackgroundAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
            view.setBackgroundColor(SkinManager.getInstance().getColor(attrValueRefId));
        } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
            Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
            view.setBackground(bg);
            L.i(this.attrValueRefName + " 是否可变换状态? : " + bg.isStateful());
        } else if (RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName)) {
            Drawable bg = SkinManager.getInstance().getMipmap(attrValueRefId);
            view.setBackground(bg);
        }
    }
}
