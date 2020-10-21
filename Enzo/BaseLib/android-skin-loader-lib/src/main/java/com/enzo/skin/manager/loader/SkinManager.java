package com.enzo.skin.manager.loader;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.enzo.skin.manager.config.SkinConfig;
import com.enzo.skin.manager.listener.ILoaderListener;
import com.enzo.skin.manager.listener.ISkinLoader;
import com.enzo.skin.manager.listener.ISkinUpdate;
import com.enzo.skin.manager.util.L;

public class SkinManager implements ISkinLoader {

    private static final Object synchronizedLock = new Object();
    private static SkinManager instance;

    private List<ISkinUpdate> skinObservers;
    private Application context;
    private String skinPackageName;
    private Resources mResources;
    private String skinPath;
    private boolean isDefaultSkin = false;

    public boolean isExternalSkin() {
        return !isDefaultSkin && mResources != null;
    }

    public String getSkinPath() {
        return skinPath;
    }

    public static SkinManager getInstance() {
        if (instance == null) {
            synchronized (synchronizedLock) {
                if (instance == null) {
                    instance = new SkinManager();
                }
            }
        }
        return instance;
    }

    public String getSkinPackageName() {
        return skinPackageName;
    }

    public Resources getResources() {
        return mResources;
    }

    private SkinManager() {
    }

    public void init(Application ctx) {
        context = ctx;
    }

    public void restoreDefaultTheme() {
        SkinConfig.saveSkinPath(context, SkinConfig.DEFAULT_SKIN);
        isDefaultSkin = true;
        mResources = context.getResources();
        notifySkinUpdate();
    }

    public void load() {
        String skin = SkinConfig.getCustomSkinPath(context);
        load(skin, null);
    }

    public void load(ILoaderListener callback) {
        String skin = SkinConfig.getCustomSkinPath(context);
        if (SkinConfig.isDefaultSkin(context)) {
            return;
        }
        load(skin, callback);
    }

    public void load(String skinPackagePath, final ILoaderListener callback) {
        new MyLoaderTask(context, callback).execute(skinPackagePath);
    }

    private static class MyLoaderTask extends AsyncTask<String, Void, Resources> {

        private final Application mContext;
        private final ILoaderListener loaderListener;

        public MyLoaderTask(Application context, ILoaderListener listener) {
            mContext = context;
            loaderListener = listener;
        }

        @Override
        protected void onPreExecute() {
            if (loaderListener != null) {
                loaderListener.onStart();
            }
        }

        @Override
        protected Resources doInBackground(String... params) {
            try {
                if (params.length == 1) {
                    String skinPkgPath = params[0];

                    File file = new File(skinPkgPath);
                    if (!file.exists()) {
                        return null;
                    }

                    PackageManager mPm = mContext.getPackageManager();
                    PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                    assert mInfo != null;
                    SkinManager.getInstance().skinPackageName = mInfo.packageName;

                    AssetManager assetManager = AssetManager.class.newInstance();
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    addAssetPath.invoke(assetManager, skinPkgPath);

                    Resources superRes = mContext.getResources();
                    Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

                    SkinConfig.saveSkinPath(mContext, skinPkgPath);

                    SkinManager.getInstance().skinPath = skinPkgPath;
                    SkinManager.getInstance().isDefaultSkin = false;
                    return skinResource;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Resources result) {
            SkinManager.getInstance().mResources = result;

            if (SkinManager.getInstance().mResources != null) {
                if (loaderListener != null) loaderListener.onSuccess();
                SkinManager.getInstance().notifySkinUpdate();
            } else {
                SkinManager.getInstance().isDefaultSkin = true;
                if (loaderListener != null) loaderListener.onFailed();
            }
        }
    }

    @Override
    public void attach(ISkinUpdate observer) {
        if (skinObservers == null) {
            skinObservers = new ArrayList<>();
        }
        if (!skinObservers.contains(observer)) {
            skinObservers.add(observer);
        }
    }

    @Override
    public void detach(ISkinUpdate observer) {
        if (skinObservers == null) return;
        if (skinObservers.contains(observer)) {
            skinObservers.remove(observer);
        }
    }

    @Override
    public void notifySkinUpdate() {
        if (skinObservers == null) return;
        for (ISkinUpdate observer : skinObservers) {
            observer.onThemeUpdate();
        }
    }

    public int getColor(int resId) {
        if (context != null) {
            int originColor = context.getResources().getColor(resId, context.getTheme());
            if (mResources == null || isDefaultSkin) {
                return originColor;
            }

            String resName = context.getResources().getResourceEntryName(resId);

            int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
            int trueColor = 0;

            try {
                trueColor = mResources.getColor(trueResId, context.getTheme());
            } catch (Exception e) {
                e.printStackTrace();
                trueColor = originColor;
            }

            return trueColor;
        } else {
            return Color.parseColor("#ffffff");
        }
    }

    @SuppressLint("NewApi")
    public Drawable getDrawable(int resId, String attrValueTypeName) {
        Log.e("xfy", "getDrawable name: " + attrValueTypeName);
        Drawable originDrawable = context.getResources().getDrawable(resId, context.getTheme());
        if (mResources == null || isDefaultSkin) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId);

        int trueResId = mResources.getIdentifier(resName, attrValueTypeName, skinPackageName);

        Drawable trueDrawable;
        try {
            trueDrawable = mResources.getDrawable(trueResId, null);
        } catch (NotFoundException e) {
            e.printStackTrace();
            trueDrawable = originDrawable;
        }

        return trueDrawable;
    }

    /**
     * 加载指定资源颜色drawable,转化为ColorStateList，保证selector类型的Color也能被转换。</br>
     * 无皮肤包资源返回默认主题颜色
     *
     * @param resId
     * @return
     * @author pinotao
     */
    public ColorStateList convertToColorStateList(int resId) {
        L.e("attr1", "convertToColorStateList");

        boolean isExtendSkin = true;

        if (mResources == null || isDefaultSkin) {
            isExtendSkin = false;
        }

        String resName = context.getResources().getResourceEntryName(resId);
        L.e("attr1", "resName = " + resName);
        if (isExtendSkin) {
            L.e("attr1", "isExtendSkin");
            int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
            L.e("attr1", "trueResId = " + trueResId);
            ColorStateList trueColorList = null;
            if (trueResId == 0) { // 如果皮肤包没有复写该资源，但是需要判断是否是ColorStateList
                try {
                    ColorStateList originColorList = context.getResources().getColorStateList(resId);
                    return originColorList;
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    L.e("resName = " + resName + " NotFoundException : " + e.getMessage());
                }
            } else {
                try {
                    trueColorList = mResources.getColorStateList(trueResId);
                    L.e("attr1", "trueColorList = " + trueColorList);
                    return trueColorList;
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    L.w("resName = " + resName + " NotFoundException :" + e.getMessage());
                }
            }
        } else {
            try {
                ColorStateList originColorList = context.getResources().getColorStateList(resId);
                return originColorList;
            } catch (NotFoundException e) {
                e.printStackTrace();
                L.w("resName = " + resName + " NotFoundException :" + e.getMessage());
            }

        }

        int[][] states = new int[1][1];
        return new ColorStateList(states, new int[]{context.getResources().getColor(resId)});
    }
}