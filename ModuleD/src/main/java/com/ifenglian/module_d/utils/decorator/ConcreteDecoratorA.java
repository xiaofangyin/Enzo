package com.ifenglian.module_d.utils.decorator;

import com.enzo.commonlib.utils.common.LogUtil;

/**
 * 文 件 名: ConcreteDecoratorA
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class ConcreteDecoratorA extends Decorator {

    /**
     * 依赖注入
     *
     * @param component 依赖的对象
     */
    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    public int operationA() {
        LogUtil.e("ConcreteDecoratorA operationA");
        return 0;
    }

    @Override
    public void operation() {
        operationA();
        super.operation();
    }
}
