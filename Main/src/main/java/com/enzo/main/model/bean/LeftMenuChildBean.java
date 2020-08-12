package com.enzo.main.model.bean;

import com.enzo.commonlib.base.BaseBean;

import java.io.Serializable;

/**
 * 文 件 名: LeftMenuChildBean
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class LeftMenuChildBean extends BaseBean implements Serializable {

    private int id;
    private boolean enable;
    private int iconWhite;
    private int iconBlack;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getIconWhite() {
        return iconWhite;
    }

    public void setIconWhite(int iconWhite) {
        this.iconWhite = iconWhite;
    }

    public int getIconBlack() {
        return iconBlack;
    }

    public void setIconBlack(int iconBlack) {
        this.iconBlack = iconBlack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
