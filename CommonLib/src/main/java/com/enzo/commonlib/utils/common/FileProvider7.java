package com.enzo.commonlib.utils.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

public class FileProvider7 {

    public static Uri getUriForFile(Context context, String authority, File file) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, authority, file);
            context.grantUriPermission(ApkUtils.getAppName(context), fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    private static Uri getUriForFile24(Context context, String authority, File file) {
        return FileProvider.getUriForFile(context, authority, file);
    }

    public static void setIntentDataAndType(Context context,
                                            Intent intent,
                                            String type,
                                            File file,
                                            String authority,
                                            boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, authority, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }

    public static void setIntentData(Context context,
                                     Intent intent,
                                     File file,
                                     String authority,
                                     boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setData(getUriForFile(context, authority, file));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setData(Uri.fromFile(file));
        }
    }

    public static void grantPermissions(Context context, Intent intent, Uri uri, boolean writeAble) {
        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
        if (writeAble) {
            flag |= Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        }
        intent.addFlags(flag);
        List<ResolveInfo> resInfoList = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, flag);
        }
    }
}
