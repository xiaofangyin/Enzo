package com.enzo.skin.manager.listener;

import java.util.List;

import android.view.View;

import com.enzo.skin.manager.entity.DynamicAttr;

public interface IDynamicNewView {
    void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
