package com.enzo.flkit.account;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 文 件 名: AccountInfo
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/12
 * 邮   箱: xiaofywork@163.com
 */
public class UserAccountInfo implements Serializable {

    private String mAvatarUrl;
    private String nickName;
    private String uid;
    private String token;
    private JSONObject orgInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getmAvatarUrl() {
        return mAvatarUrl;
    }

    public void setmAvatarUrl(String mAvatarUrl) {
        this.mAvatarUrl = mAvatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public JSONObject getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(JSONObject orgInfo) {
        this.orgInfo = orgInfo;
    }
}
