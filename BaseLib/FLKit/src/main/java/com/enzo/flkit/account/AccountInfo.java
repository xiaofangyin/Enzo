package com.enzo.flkit.account;

import java.io.Serializable;

/**
 * 文 件 名: AccountInfo
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/12
 * 邮   箱: xiaofywork@163.com
 */
public class AccountInfo implements Serializable {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
