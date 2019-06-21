package com.enzo.commonlib.utils.album.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;

import com.yalantis.ucrop.UCrop;

import java.io.File;

/**
 * 文 件 名: PhotoCropConfig
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/4
 * 邮   箱: xiaofangyinwork@163.com
 */
public class PhotoCropConfig {

    public static UCrop.Options getOptions() {
        UCrop.Options options = new UCrop.Options();
        options.withAspectRatio(1, 1);
        options.setToolbarTitle("裁剪");//设置标题栏文字
        options.setCropGridStrokeWidth(2);//设置裁剪网格线的宽度(我这网格设置不显示，所以没效果)
        options.setCropFrameStrokeWidth(2);//设置裁剪框的宽度
        //options.setMaxScaleMultiplier(3);//设置最大缩放比例
        options.setHideBottomControls(true);//隐藏下边控制栏
        options.setShowCropGrid(true);  //设置是否显示裁剪网格
        options.setShowCropFrame(true); //设置是否显示裁剪边框
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));//标题字的颜色以及按钮颜色
        options.setToolbarColor(Color.parseColor("#000000")); // 设置标题栏颜色
        options.setStatusBarColor(Color.parseColor("#000000"));//设置状态栏颜色
        return options;
    }

    public static File getAvatarCroppedFile(Context context) {
        return new File(getDiskCacheDir(context), "avatar.jpeg");
    }

    private static String getDiskCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
