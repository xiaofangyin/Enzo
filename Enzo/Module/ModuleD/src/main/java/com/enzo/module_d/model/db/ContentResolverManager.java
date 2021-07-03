package com.enzo.module_d.model.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.module_d.ui.activity.MDContentProviderTestActivity;

public class ContentResolverManager {

    private static ContentResolverManager mInstance;
    private ContentResolver contentResolver;
    private ContentObserver contentObserver;

    private ContentResolverManager() {

    }

    public static ContentResolverManager getInstance() {
        if (mInstance == null) {
            synchronized (ContentResolverManager.class) {
                if (mInstance == null) {
                    mInstance = new ContentResolverManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        contentResolver = context.getContentResolver();
        contentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {

            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri) {
                super.onChange(selfChange, uri);
                LogUtil.d("uri: " + uri);
            }
        };

        contentResolver.registerContentObserver(Uri.parse(MDContentProviderTestActivity.URI_PATH),
                false, contentObserver);
    }

    public void insert(Uri uri, ContentValues contentValues) {
        if (contentResolver == null) {
            return;
        }
        contentResolver.insert(uri, contentValues);
    }

    public void delete(Uri url, String where, String[] selectionArgs) {
        if (contentResolver == null) {
            return;
        }
        contentResolver.delete(url, where, selectionArgs);
    }

    public void onDestroy() {
        contentResolver.unregisterContentObserver(contentObserver);
    }
}
