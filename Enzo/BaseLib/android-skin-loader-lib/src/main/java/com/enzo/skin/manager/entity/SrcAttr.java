package com.enzo.skin.manager.entity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.enzo.skin.manager.loader.SkinManager;

public class SrcAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
                Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
                imageView.setImageDrawable(bg);
            } else if (RES_TYPE_NAME_MIPMAP.equals(attrValueTypeName)) {
                Drawable bg = SkinManager.getInstance().getMipmap(attrValueRefId);
                imageView.setImageDrawable(bg);
            }
        }
    }
}