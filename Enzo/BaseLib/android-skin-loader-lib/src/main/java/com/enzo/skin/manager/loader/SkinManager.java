package com.enzo.skin.manager.loader;

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

import com.enzo.skin.manager.config.SkinConfig;
import com.enzo.skin.manager.entity.SkinAttr;
import com.enzo.skin.manager.listener.ILoaderListener;
import com.enzo.skin.manager.listener.ISkinLoader;
import com.enzo.skin.manager.listener.ISkinUpdate;
import com.enzo.skin.manager.util.L;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SkinManager implements ISkinLoader {

    private static final Object synchronizedLock = new Object();
    private static SkinManager instance;

    private List<ISkinUpdate> skinObservers;
    private Application context;
    private volatile String skinPackageName;
    private volatile Resources mResources;
    private volatile String skinPath;
    private volatile boolean isDefaultSkin = false;

    private SkinManager() {
    }

    public void init(Application ctx) {
        context = ctx;
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

    public boolean isExternalSkin() {
        return !isDefaultSkin && mResources != null;
    }

    public String getSkinPath() {
        return skinPath;
    }

    public Resources getResources() {
        return mResources;
    }

    public void restoreDefaultTheme() {
        if (context != null) {
            SkinConfig.saveSkinPath(context, SkinConfig.DEFAULT_SKIN);
            isDefaultSkin = true;
            mResources = context.getResources();
            notifySkinUpdate();
        }
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
            int trueResId = mResources.getIdentifier(resName, SkinAttr.RES_TYPE_NAME_COLOR, skinPackageName);
            int trueColor;
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

    public Drawable getDrawable(int resId) {
        L.e("getDrawable skinPackageName: " + skinPackageName);
        Drawable originDrawable = context.getResources().getDrawable(resId, context.getTheme());
        if (mResources == null || isDefaultSkin) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId);
        L.e("getDrawable resName: " + resName);
        int trueResId = mResources.getIdentifier(resName, SkinAttr.RES_TYPE_NAME_DRAWABLE, skinPackageName);
        Drawable trueDrawable;
        try {
            trueDrawable = mResources.getDrawable(trueResId, null);
        } catch (NotFoundException e) {
            e.printStackTrace();
            trueDrawable = originDrawable;
        }
        return trueDrawable;
    }

    public Drawable getMipmap(int resId) {
        L.e("getMipmap skinPackageName: " + skinPackageName);
        Drawable originDrawable = context.getResources().getDrawable(resId, context.getTheme());
        if (mResources == null || isDefaultSkin) {
            return originDrawable;
        }
        String resName = context.getResources().getResourceEntryName(resId);
        L.e("getMipmap resName: " + resName);
        int trueResId = mResources.getIdentifier(resName, SkinAttr.RES_TYPE_NAME_MIPMAP, skinPackageName);
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
     */
    public ColorStateList convertToColorStateList(int resId) {
        L.e("convertToColorStateList");
        boolean isExtendSkin = true;
        if (mResources == null || isDefaultSkin) {
            isExtendSkin = false;
        }
        String resName = context.getResources().getResourceEntryName(resId);
        L.e("resName = " + resName);
        if (isExtendSkin) {
            int trueResId = mResources.getIdentifier(resName, SkinAttr.RES_TYPE_NAME_COLOR, skinPackageName);
            if (trueResId == 0) { // 如果皮肤包没有复写该资源，但是需要判断是否是ColorStateList
                try {
                    return context.getResources().getColorStateList(resId, null);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    L.e("resName = " + resName + " NotFoundException : " + e.getMessage());
                }
            } else {
                try {
                    return mResources.getColorStateList(trueResId, null);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                    L.w("resName = " + resName + " NotFoundException :" + e.getMessage());
                }
            }
        } else {
            try {
                return context.getResources().getColorStateList(resId, null);
            } catch (NotFoundException e) {
                e.printStackTrace();
                L.w("resName = " + resName + " NotFoundException :" + e.getMessage());
            }
        }

        int[][] states = new int[1][1];
        return new ColorStateList(states, new int[]{context.getResources().getColor(resId, null)});
    }
}