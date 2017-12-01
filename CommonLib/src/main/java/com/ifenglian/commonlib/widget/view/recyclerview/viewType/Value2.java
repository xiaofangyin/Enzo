package com.ifenglian.commonlib.widget.view.recyclerview.viewType;

import com.ifenglian.commonlib.widget.view.recyclerview.factory.BaseViewTypeFactory;

/**
 * 文 件 名: Value2
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class Value2 implements BaseValue {

    private String address;

    public Value2(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int getLayoutId(BaseViewTypeFactory factory) {
        return factory.type(this);
    }
}
