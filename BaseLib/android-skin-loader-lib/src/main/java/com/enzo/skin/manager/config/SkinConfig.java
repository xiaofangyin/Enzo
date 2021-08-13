package com.enzo.skin.manager.config;

import android.content.Context;

import com.enzo.skin.manager.util.PreferencesUtils;

public class SkinConfig {

    public static final String NAMESPACE = "http://schemas.android.com/android/skin";
    private static final String PREF_CUSTOM_SKIN_PATH = "cn_enzo_skin_custom_path";
    public static final String DEFAULT_SKIN = "cn_enzo_skin_default";
    public static final String ATTR_SKIN_ENABLE = "enable";


    public static String getCustomSkinPath(Context context) {
        return PreferencesUtils.getString(context, PREF_CUSTOM_SKIN_PATH, DEFAULT_SKIN);
    }

    public static void saveSkinPath(Context context, String path) {
        PreferencesUtils.putString(context, PREF_CUSTOM_SKIN_PATH, path);
    }

    public static boolean isDefaultSkin(Context context) {
        return DEFAULT_SKIN.equals(getCustomSkinPath(context));
    }
}
