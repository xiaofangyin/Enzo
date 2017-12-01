package com.ifenglian.commonlib.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

/**
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/6/26
 * 功能说明：
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class Permission {

    public static PermissionResult mPermissionResult;

    public static void checkPermission(final Context context, String[] permissions, PermissionResult permissionResult) {
        Log.e("AAA", "Permission checkPermission...");
        mPermissionResult = permissionResult;

        PermissionUtil.checkPermission((Activity) context, permissions, new PermissionUtil.permissionInterface() {
            @Override
            public void success() {
                if (Permission.mPermissionResult != null) {
                    Permission.mPermissionResult.success();
                }
            }

            @Override
            public void fail(final List<String> permission) {
                Intent intent = new Intent(context, PermissionActivity.class);
                intent.putExtra("permission", permission.toArray(new String[permission.size()]));
                context.startActivity(intent);
            }
        });
    }
}
