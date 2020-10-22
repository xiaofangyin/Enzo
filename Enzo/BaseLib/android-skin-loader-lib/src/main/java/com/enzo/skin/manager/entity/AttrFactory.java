package com.enzo.skin.manager.entity;


import com.enzo.skin.manager.util.L;

public class AttrFactory {

    public static final String BACKGROUND = "background";
    public static final String TEXT_COLOR = "textColor";
    public static final String LIST_SELECTOR = "listSelector";
    public static final String DIVIDER = "divider";
    public static final String SRC = "src";

    public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName) {
        L.e("AttrFactory get attrName: " + attrName + "...attrValueRefName: " + attrValueRefName + "...typeName: " + typeName);
        SkinAttr mSkinAttr;

        if (BACKGROUND.equals(attrName)) {
            mSkinAttr = new BackgroundAttr();
        } else if (TEXT_COLOR.equals(attrName)) {
            mSkinAttr = new TextColorAttr();
        } else if (LIST_SELECTOR.equals(attrName)) {
            mSkinAttr = new ListSelectorAttr();
        } else if (DIVIDER.equals(attrName)) {
            mSkinAttr = new DividerAttr();
        } else if (SRC.equals(attrName)) {
            mSkinAttr = new SrcAttr();
        } else {
            return null;
        }

        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValueRefId = attrValueRefId;
        mSkinAttr.attrValueRefName = attrValueRefName;
        mSkinAttr.attrValueTypeName = typeName;
        return mSkinAttr;
    }

    public static boolean isSupportedAttr(String attrName) {
        if (BACKGROUND.equals(attrName)) {
            return true;
        }
        if (TEXT_COLOR.equals(attrName)) {
            return true;
        }
        if (LIST_SELECTOR.equals(attrName)) {
            return true;
        }
        if (DIVIDER.equals(attrName)) {
            return true;
        }
        if (SRC.equals(attrName)) {
            return true;
        }
        return false;
    }
}
