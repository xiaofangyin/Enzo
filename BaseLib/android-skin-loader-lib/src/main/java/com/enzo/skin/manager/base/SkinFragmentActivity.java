package com.enzo.skin.manager.base;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

import com.enzo.skin.manager.entity.DynamicAttr;
import com.enzo.skin.manager.listener.IDynamicNewView;
import com.enzo.skin.manager.listener.ISkinUpdate;
import com.enzo.skin.manager.loader.SkinInflaterFactory;
import com.enzo.skin.manager.loader.SkinManager;

public class SkinFragmentActivity extends FragmentActivity implements ISkinUpdate, IDynamicNewView {

    /**
     * Whether response to skin changing after create
     */
    private boolean isResponseOnSkinChanging = true;

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSkinInflaterFactory = new SkinInflaterFactory();
        getLayoutInflater().setFactory(mSkinInflaterFactory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        SkinManager.getInstance().detach(this);
        super.onDestroy();
    }

    protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    final protected void enableResponseOnSkinChanging(boolean enable) {
        isResponseOnSkinChanging = enable;
    }

    @Override
    public void onThemeUpdate() {
        if (!isResponseOnSkinChanging) return;
        if (mSkinInflaterFactory != null)
            mSkinInflaterFactory.applySkin();
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        if (mSkinInflaterFactory != null)
            mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }
}
