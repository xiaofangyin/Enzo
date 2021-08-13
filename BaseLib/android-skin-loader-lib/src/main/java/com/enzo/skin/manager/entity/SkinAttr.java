package com.enzo.skin.manager.entity;

import android.view.View;

public abstract class SkinAttr {

    public static final String RES_TYPE_NAME_COLOR = "color";
    public static final String RES_TYPE_NAME_DRAWABLE = "drawable";
    public static final String RES_TYPE_NAME_MIPMAP = "mipmap";

    /**
     * name of the attr, ex: background or textSize or textColor
     */
    public String attrName;

    /**
     * id of the attr value refered to, normally is [2130745655]
     */
    public int attrValueRefId;

    /**
     * entry name of the value , such as [app_exit_btn_background]
     */
    public String attrValueRefName;

    /**
     * type of the value , such as color or drawable
     */
    public String attrValueTypeName;

    /**
     * Use to apply view with new TypedValue
     */
    public abstract void apply(View view);

    @Override
    public String toString() {
        return "SkinAttr \n[\nattrName=" + attrName + ", \n"
                + "attrValueRefId=" + attrValueRefId + ", \n"
                + "attrValueRefName=" + attrValueRefName + ", \n"
                + "attrValueTypeName=" + attrValueTypeName
                + "\n]";
    }
}
