package com.ifenglian.commonlib.widget.view.recyclerview.viewType;

import com.ifenglian.commonlib.widget.view.recyclerview.factory.BaseViewTypeFactory;

/**
 * 文 件 名: Value3
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class Value3 implements BaseValue {

    public Value3(String age) {
        this.age = age;
    }

    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public int getLayoutId(BaseViewTypeFactory factory) {
        return factory.type(this);
    }
}
