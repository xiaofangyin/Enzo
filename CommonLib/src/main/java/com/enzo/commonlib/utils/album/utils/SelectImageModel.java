package com.enzo.commonlib.utils.album.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.enzo.commonlib.utils.album.bean.AlbumFolder;
import com.enzo.commonlib.utils.album.bean.AlbumImage;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: SelectImageModel
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/3
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SelectImageModel {

    public static void getAllAlbums(final Context context, final ImagesCallBack callback) {
        new AsyncTask<Void, Void, List<AlbumImage>>() {
            @Override
            protected List<AlbumImage> doInBackground(Void... voids) {
                List<AlbumImage> lst = new ArrayList<>();
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            null, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
                    AlbumImage item;
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            item = new AlbumImage();
                            item.setImageId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                            item.setImagePath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                            item.setDate(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
                            item.setBucket(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
                            lst.add(item);
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                return lst;
            }

            @Override
            protected void onPostExecute(List<AlbumImage> images) {
                super.onPostExecute(images);
                callback.onSuccess(images);
            }
        }.execute();
    }

    /**
     * 获取指定相册所有照片
     */
    public static void getAlbumsByBucket(final Context context, final String bucket, final ImagesCallBack callback) {
        new AsyncTask<Void, Void, List<AlbumImage>>() {
            @Override
            protected List<AlbumImage> doInBackground(Void... voids) {
                List<AlbumImage> lst = new ArrayList<>();
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            null, MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?",
                            new String[]{bucket}, MediaStore.Images.Media.DATE_ADDED + " DESC");
                    AlbumImage item;
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            item = new AlbumImage();
                            item.setImageId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                            item.setImagePath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                            item.setDate(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
                            item.setBucket(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
                            lst.add(item);
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                return lst;
            }

            @Override
            protected void onPostExecute(List<AlbumImage> images) {
                super.onPostExecute(images);
                callback.onSuccess(images);
            }
        }.execute();
    }

    /**
     * 获取相册集合
     */
    public static void getFolderList(final Context context, final FolderCallback callback) {
        getAllAlbums(context, new ImagesCallBack() {
            @Override
            public void onSuccess(List<AlbumImage> imageList) {
                List<AlbumFolder> folderList = new ArrayList<>();
                for (int i = 0; i < imageList.size(); i++) {
                    String bucket = imageList.get(i).getBucket();

                    AlbumFolder temp = null;
                    for (AlbumFolder folder : folderList) {
                        if (folder.getName().equals(bucket)) {
                            temp = folder;
                        }
                    }
                    if (temp == null) {
                        temp = new AlbumFolder(bucket);
                        temp.setName(imageList.get(i).getBucket());
                        folderList.add(temp);
                    }
                    temp.getImages().add(imageList.get(i));
                }
                callback.onSuccess(folderList);
            }
        });
    }

    public interface ImagesCallBack {
        void onSuccess(List<AlbumImage> images);
    }

    public interface FolderCallback {
        void onSuccess(List<AlbumFolder> folders);
    }
}
