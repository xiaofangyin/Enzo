package com.enzo.module_a.model.bean;

/**
 * 文 件 名: MAHomeBaseBean
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/9
 * 邮   箱: xiaofywork@163.com
 */
public class MAHomeBaseBean {

    private int viewType;

    public MAHomeBaseBean(int type){
        viewType = type;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }



}
