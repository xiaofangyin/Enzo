package com.enzo.main.util;

import android.content.Context;
import android.text.TextUtils;

import com.enzo.commonlib.utils.common.GsonHelper;
import com.enzo.commonlib.utils.common.PreferenceUtils;
import com.enzo.main.model.bean.LeftMenuParentBean;

import java.util.List;

/**
 * 文 件 名: SPUtils
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/6/11
 * 邮   箱: xiaofy@ifenglian.com
 */
public class SPUtils {

    //登录帐号信息
    private static final String ACCOUNT_INFO = "account_info";
    //登录帐号
    private static final String USER_ACCOUNT = "user_account";
    //登录密码
    private static final String PASS_WORD = "pass_word";
    //是否查看过app,如果没 显示引导页
    private static final String HAS_LOOK_OVER = "has_look_over";
    private static final String FUNCTION_DISPLAY_MODE = "function_display_mode";
    private static final String FUNCTION_LIST = "function_list";

    ///////////////////////////////////////  侧拉菜单功能  ///////////////////////////////////////

    /**
     * @param mode 0 列表 1 宫格
     */
    public static void setDisplayMode(Context context, int mode) {
        PreferenceUtils.setInt(context, FUNCTION_DISPLAY_MODE, mode);
    }

    public static int getDisplayMode(Context context) {
        return PreferenceUtils.getInt(context, FUNCTION_DISPLAY_MODE, 0);
    }

    public static void setFunctionList(Context context, List<LeftMenuParentBean> list) {
        PreferenceUtils.setString(context, FUNCTION_LIST, GsonHelper.toJson(list));
    }

    public static List<LeftMenuParentBean> getFunctionList(Context context) {
        String cacheList = PreferenceUtils.getString(context, FUNCTION_LIST, "");
        if (!TextUtils.isEmpty(cacheList)) {
            return GsonHelper.toList(cacheList, LeftMenuParentBean.class);
        }
        return null;
    }

    public static void clearFunctionList(Context context) {
        PreferenceUtils.remove(context, FUNCTION_DISPLAY_MODE);
        PreferenceUtils.remove(context, FUNCTION_LIST);
    }
}
