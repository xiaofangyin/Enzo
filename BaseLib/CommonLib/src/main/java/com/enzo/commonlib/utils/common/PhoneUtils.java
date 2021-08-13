package com.enzo.commonlib.utils.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用于获取手机信息
 */
public class PhoneUtils {

    private static PhoneUtils instance;

    private String combinedID;//device token
    private String versionname;//apk版本名称
    private String versioncode;//apk版本号
    private String ostype;////获得当前手机系统
    private String brand;//获得当前手机品牌
    private String model;//获得当前手机型号
    private int osversion;//获得当前系统版本号
    private int screenwidth;//获得当前屏幕的X宽度
    private int screenheight;//获得当前屏幕的Y高度
    private int density;//获得当前屏幕的密度

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

    public void init(Context context) {
        this.ostype = "android";
        this.versionname = ApkUtils.getVersionName(context);
        this.versioncode = String.valueOf(ApkUtils.getVersionCode(context));
        this.osversion = android.os.Build.VERSION.SDK_INT;//获得当前系统版本号
        this.brand = android.os.Build.BRAND;//获得当前手机品牌
        this.model = android.os.Build.MODEL;//获得当前手机型号
        this.screenwidth = DensityUtil.getScreenWidth(context);//获得当前屏幕的X宽度
        this.screenheight = DensityUtil.getScreenHeight(context);//获得当前屏幕的Y高度
        this.density = DensityUtil.getScreenDensity(context);//获得当前屏幕的密度
        this.combinedID = getUniqueId(context);//计算出一个唯一识别ID
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
        if (!params.containsKey("brand")) {
            params.put("brand", "" + brand);
        }
        if (!params.containsKey("model")) {
            params.put("model", "" + model);
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
     * 通过获取手机硬件各参数计算出一个唯一识别ID
     *
     * @param context application的context
     */
    public String getUniqueId(Context context) {
        String combinedDeviceId = getUUID() + getAndroidId(context);
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
        combinedID = ID.toString();
        return combinedID;
    }

    @SuppressLint("HardwareIds")
    private static String getUUID() {
        String serial;
        String m_szDevIDShort = "42" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = android.os.Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            exception.printStackTrace();
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    @SuppressLint("HardwareIds")
    private static String getAndroidId(Context context) {
        String androidId = "";
        if (context != null) {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return androidId;
    }

}
