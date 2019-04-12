package com.enzo.commonlib.utils.album.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.enzo.commonlib.utils.album.activity.MultipleSelectorActivity;
import com.enzo.commonlib.utils.album.activity.SingleSelectorActivity;
import com.enzo.commonlib.utils.album.activity.PreviewActivity;
import com.enzo.commonlib.utils.album.constant.SelectImageConstants;
import com.enzo.commonlib.utils.album.bean.AlbumImage;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供给外界相册的调用的工具类
 */
public class SelectImagesUtils {

    public static void images(Activity activity, int requestCode, int maxSelectCount) {
        Intent intent = new Intent(activity, MultipleSelectorActivity.class);
        intent.putExtra(SelectImageConstants.MAX_SELECT_COUNT, maxSelectCount);
        intent.putExtra(SelectImageConstants.IS_SINGLE, false);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void single(Activity activity, int requestCode, boolean needCrop) {
        Intent intent = new Intent(activity, SingleSelectorActivity.class);
        intent.putExtra(SelectImageConstants.MAX_SELECT_COUNT, 1);
        intent.putExtra(SelectImageConstants.IS_SINGLE, true);
        intent.putExtra(SelectImageConstants.NEED_CROP, needCrop);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void toPreview(Activity activity, List<AlbumImage> images,
                                 int position, int maxCount, boolean canSelect) {
        if (images != null && !images.isEmpty()) {
            Intent intent = new Intent(activity, PreviewActivity.class);
            intent.putParcelableArrayListExtra(SelectImageConstants.IMAGES,
                    (ArrayList<? extends Parcelable>) images);
            intent.putExtra(SelectImageConstants.MAX_SELECT_COUNT, maxCount);
            intent.putExtra(SelectImageConstants.POSITION, position);
            intent.putExtra(SelectImageConstants.CAN_SELECT, canSelect);
            activity.startActivityForResult(intent, SelectImageConstants.PREVIEW_REQUEST_CODE);
        }
    }
}
