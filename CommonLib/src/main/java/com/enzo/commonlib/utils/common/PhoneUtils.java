package com.enzo.commonlib.utils.common;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于获取手机信息
 */
public class PhoneUtils {

    private static PhoneUtils instance;

    private String versionname;
    private String versioncode;
    private String ostype;
    private String phonetype;
    private int osversion;
    private int screenwidth;
    private int screenheight;
    private int density;

    private PhoneUtils() {
    }

    public static PhoneUtils getInstance() {
        if (instance == null) {
            synchronized (PhoneUtils.class) {
                if (instance == null) {
                    instance = new PhoneUtils();
                }
            }
        }
        return instance;
    }

    public void initParam(Context context) {
        this.ostype = "Android";
        this.versionname = ApkUtils.getVersionName(context);
        this.versioncode = String.valueOf(ApkUtils.getVersionCode(context));
        this.osversion = getCurrentSystemVersion();
        this.phonetype = getCurrentPhoneType();
        this.screenwidth = getSystemWidth(context);
        this.screenheight = getSystemHeight(context);
        this.density = getSystemDensityDPI(context);
    }

    public Map<String, String> getDefaultParams() {
        Map<String, String> params = new HashMap<>();
        if (!params.containsKey("versionname")) {
            params.put("versionname", "" + versionname);
        }
        if (!params.containsKey("versioncode")) {
            params.put("versioncode", "" + versioncode);
        }
        if (!params.containsKey("ostype")) {
            params.put("ostype", "" + ostype);
        }
        if (!params.containsKey("osversion")) {
            params.put("osversion", "" + osversion);
        }
        if (!params.containsKey("phonetype")) {
            params.put("phonetype", "" + phonetype);
        }
        if (!params.containsKey("screenwidth")) {
            params.put("screenwidth", "" + screenwidth);
        }
        if (!params.containsKey("screenheight")) {
            params.put("screenheight", "" + screenheight);
        }
        if (!params.containsKey("density")) {
            params.put("density", "" + density);
        }
        return params;
    }

    /**
     * 获得当前系统版本号
     */
    private int getCurrentSystemVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获得当前屏幕的密度
     */
    private int getSystemDensityDPI(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * 获得当前屏幕的X宽度
     */
    private int getSystemWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获得当前屏幕的Y高度
     */
    private int getSystemHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获得当前手机型号
     */
    private String getCurrentPhoneType() {
        return android.os.Build.MODEL;
    }

    /**
     * 通过获取手机硬件各参数计算出一个唯一识别ID
     *
     * @param context application的context
     */
    public String getUniqueId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return SecurityUtil.getMD5(id);
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        }
    }
}
