package com.enzo.main.model.manager;

import android.app.Application;
import android.text.TextUtils;

import com.enzo.commonlib.utils.common.GsonHelper;
import com.enzo.commonlib.utils.common.PreferenceUtils;
import com.enzo.commonlib.utils.common.SecurityUtil;
import com.enzo.flkit.account.UserAccountInfo;

/**
 * 文 件 名: AccountManager
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/5/30
 * 邮   箱: xiaofangyinwork@163.com
 */
public class AccountManager {

    //加密登录信息KEY
    private static final String ACCOUNT_ENC_KEY = "enzo";
    //用户信息
    private static final String ACCOUNT_INFO = "account_info";

    private static AccountManager mInstance;
    private Application application;
    private UserAccountInfo mUserAccountInfo;

    private AccountManager() {

    }

    public static AccountManager getInstance() {
        if (mInstance == null) {
            synchronized (AccountManager.class) {
                if (mInstance == null) {
                    mInstance = new AccountManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Application context) {
        try {
            application = context;
            getAccountInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否是登录状态
     */
    public boolean isLogin() {
        return getAccountInfo() != null;
    }

    /**
     * 登录成功后缓存帐号信息
     */
    public void setAccountInfo(UserAccountInfo info) {
        try {
            mUserAccountInfo = info;
            String encrypt = SecurityUtil.DES_encrypt(GsonHelper.toJson(info), ACCOUNT_ENC_KEY);
            PreferenceUtils.setString(application, ACCOUNT_INFO, encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserAccountInfo getAccountInfo() {
        if (mUserAccountInfo == null) {
            try {
                String info = PreferenceUtils.getString(application, ACCOUNT_INFO, "");
                if (!TextUtils.isEmpty(info)) {
                    String decrypt = SecurityUtil.DES_decrypt(info, ACCOUNT_ENC_KEY);
                    mUserAccountInfo = GsonHelper.toType(decrypt, UserAccountInfo.class);
                    if (mUserAccountInfo == null) {
                        logout();
                    }
                } else {
                    logout();
                }
            } catch (Exception e) {
                e.printStackTrace();
                logout();
            }
        }
        return mUserAccountInfo;
    }

    public String getToken() {
        if (getAccountInfo() != null) {
            return getAccountInfo().getToken();
        }
        return "";
    }

    /**
     * 退出登录
     */
    public void logout() {
        if (mUserAccountInfo != null) {
            mUserAccountInfo = null;
        }
        //清除账户信息
        PreferenceUtils.remove(application, ACCOUNT_INFO);
    }

}
