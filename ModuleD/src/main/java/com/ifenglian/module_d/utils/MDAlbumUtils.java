package com.ifenglian.module_d.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.ifenglian.module_d.bean.MDImageItem;

import java.util.ArrayList;

/**
 * 文 件 名: MDAlbumUtils
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/26
 * 邮   箱: xiaofy@ifenglian.com
 */
public class MDAlbumUtils {

    public static ArrayList<MDImageItem> getLstAlbums(Context context) {
        ArrayList<MDImageItem> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    MDImageItem item = new MDImageItem();
                    item.setImageId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                    item.setImagePath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                    item.setDate(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
                    lst.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lst;
    }
}
