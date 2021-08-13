package com.enzo.commonlib.widget.indicator.scroll;

import java.io.Serializable;

/**
 * 文 件 名: IndicatorBean
 * 创 建 人: xiaofangyin
 * 创建日期: 2019/1/5
 * 邮   箱: xiaofangyinwork@163.com
 */
public class IndicatorBean implements Serializable {

    private int id;
    private String title;

    public IndicatorBean() {

    }

    public IndicatorBean(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
