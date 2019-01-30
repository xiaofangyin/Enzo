package com.enzo.commonlib.utils.permission;

import android.Manifest;

/**
 * 文 件 名: PermissionsConfig
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/5/13
 * 邮   箱: xiaofy@ifenglian.com
 */
public class PermissionsConfig {

    //sd卡
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //二维码
    public static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA
    };

    //拍照
    public static String[] PERMISSIONS_TAKE_PHOTO = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //定位
    public static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

}
