package com.enzo.skin.manager.loader;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

import androidx.annotation.NonNull;

import com.enzo.skin.manager.config.SkinConfig;
import com.enzo.skin.manager.entity.AttrFactory;
import com.enzo.skin.manager.entity.DynamicAttr;
import com.enzo.skin.manager.entity.SkinAttr;
import com.enzo.skin.manager.entity.SkinItem;
import com.enzo.skin.manager.util.L;
import com.enzo.skin.manager.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class SkinInflaterFactory implements Factory {

    private final List<SkinItem> mSkinItems = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, AttributeSet attrs) {
        // if this is NOT enable to be skined , simplly skip it
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable) {
            return null;
        }

        View view = createView(context, name, attrs);

        if (view == null) {
            return null;
        }

        parseSkinAttr(context, attrs, view);

        return view;
    }

    private View createView(Context context, String name, AttributeSet attrs) {
        View view = null;
        try {
            if (-1 == name.indexOf('.')) {
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                }
            } else {
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }

            L.i("about to create " + name);

        } catch (Exception e) {
            L.e("error while create 【" + name + "】 : " + e.getMessage());
            view = null;
        }
        return view;
    }

    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        List<SkinAttr> viewAttrs = new ArrayList<>();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            //attrName: background...attrValue: @2131034189
            L.e("parseSkinAttr attrName: " + attrName + "...attrValue: " + attrValue);
            if (!AttrFactory.isSupportedAttr(attrName)) {
                continue;
            }

            if (attrValue.startsWith("@")) {
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    //entryName: color_major_c1...typeName: color
                    L.e("parseSkinAttr entryName: " + entryName + "...typeName: " + typeName);
                    SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
                    if (mSkinAttr != null) {
                        viewAttrs.add(mSkinAttr);
                    }
                } catch (NumberFormatException | NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!ListUtils.isEmpty(viewAttrs)) {
            SkinItem skinItem = new SkinItem();
            skinItem.view = view;
            skinItem.attrs = viewAttrs;

            mSkinItems.add(skinItem);

            if (SkinManager.getInstance().isExternalSkin()) {
                skinItem.apply();
            }
        }
    }

    public void applySkin() {
        if (ListUtils.isEmpty(mSkinItems)) {
            return;
        }

        try {
            for (SkinItem si : mSkinItems) {
                if (si.view == null) {
                    continue;
                }
                si.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dynamicAddSkinEnableView(Context context, View view, List<DynamicAttr> pDAttrs) {
        List<SkinAttr> viewAttrs = new ArrayList<>();
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;

        for (DynamicAttr dAttr : pDAttrs) {
            int id = dAttr.refResId;
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            SkinAttr mSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mSkinAttr);
        }

        skinItem.attrs = viewAttrs;
        addSkinView(skinItem);
    }

    public void dynamicAddSkinEnableView(Context context, View view, String attrName, int attrValueResId) {
        String entryName = context.getResources().getResourceEntryName(attrValueResId);
        String typeName = context.getResources().getResourceTypeName(attrValueResId);
        SkinAttr mSkinAttr = AttrFactory.get(attrName, attrValueResId, entryName, typeName);
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;
        List<SkinAttr> viewAttrs = new ArrayList<>();
        viewAttrs.add(mSkinAttr);
        skinItem.attrs = viewAttrs;
        addSkinView(skinItem);
    }

    public void addSkinView(SkinItem item) {
        mSkinItems.add(item);
    }

    public void clean() {
        if (ListUtils.isEmpty(mSkinItems)) {
            return;
        }

        for (SkinItem si : mSkinItems) {
            if (si.view == null) {
                continue;
            }
            si.clean();
        }
    }
}
