package com.enzo.commonlib.utils.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于获取手机信息
 */
public class PhoneUtils {

    private static PhoneUtils instance;

    private String combinedID;
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
        this.combinedID = getUniqueId(context);
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
        if (!params.containsKey("deviceid")) {
            params.put("deviceid", "" + combinedID);
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
        if (TextUtils.isEmpty(combinedID)) {
            String combinedDeviceId = getPseudoUId() + getAndroidId(context);
            byte[] idMd5 = SecurityUtil.MD5(combinedDeviceId.getBytes());
            StringBuilder ID = new StringBuilder();
            if (idMd5 != null) {
                for (byte anIdMd5 : idMd5) {
                    int b = (0xFF & anIdMd5);
                    if (b <= 0xF) {
                        ID.append("0");
                    }
                    ID.append(Integer.toHexString(b));
                }
            }
            LogUtil.d("PhoneUtils id: " + ID);
            combinedID = ID.toString();
        }
        return combinedID;
    }

    private static String getPseudoUId() {
        String pseudoUId = "42" + Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits
        LogUtil.d("PhoneUtils getPseudoUId: " + pseudoUId);
        return pseudoUId;
    }

    @SuppressLint("HardwareIds")
    private static String getAndroidId(Context context) {
        String androidId = "";
        if (context != null) {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        LogUtil.d("PhoneUtils getAndroidId: " + androidId);
        return androidId;
    }
}
