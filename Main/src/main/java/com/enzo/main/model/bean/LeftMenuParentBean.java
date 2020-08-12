package com.enzo.main.model.bean;

import com.enzo.commonlib.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 文 件 名: LeftMenuParentBean
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class LeftMenuParentBean extends BaseBean implements Serializable {

    private boolean enable;
    private int iconWhiteId;
    private int iconBlueId;
    private String name;
    private List<LeftMenuChildBean> childList;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getIconWhiteId() {
        return iconWhiteId;
    }

    public void setIconWhite(int iconId) {
        this.iconWhiteId = iconId;
    }

    public int getIconBlueId() {
        return iconBlueId;
    }

    public void setIconBlue(int iconBlueId) {
        this.iconBlueId = iconBlueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LeftMenuChildBean> getChildList() {
        return childList;
    }

    public void setChildList(List<LeftMenuChildBean> childList) {
        this.childList = childList;
    }
}
