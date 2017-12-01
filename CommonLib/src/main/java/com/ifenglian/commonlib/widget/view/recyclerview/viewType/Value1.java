package com.ifenglian.commonlib.widget.view.recyclerview.viewType;

import com.ifenglian.commonlib.widget.view.recyclerview.factory.BaseViewTypeFactory;

/**
 * 文 件 名: Value1
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class Value1 implements BaseValue {

    private String name;

    public Value1(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLayoutId(BaseViewTypeFactory factory) {
        return factory.type(this);
    }
}
