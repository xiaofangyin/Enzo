package com.enzo.commonlib.utils.appupgrade.bean;

import java.io.Serializable;

/**
 * 文 件 名: AndroidBean
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/5
 * 邮   箱: xiaofywork@163.com
 */
public class AndroidBean implements Serializable {
    /**
     * intro : 安卓简介安卓简介安卓简介安卓简介安卓简介安卓简介
     * update : 2
     * upgrade_url : http://aaa.com
     * version : 1
     */

    private String intro;
    private String update;
    private String upgrade_url;
    private String version;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getUpgrade_url() {
        return upgrade_url;
    }

    public void setUpgrade_url(String upgrade_url) {
        this.upgrade_url = upgrade_url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
