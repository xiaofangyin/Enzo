package com.ifenglian.module_d.utils.decorator;

/**
 * 文 件 名: Decorator
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public abstract class Decorator extends Component {

    /**
     * 内部持有的组件接口对象
     */
    protected Component component;

    /**
     * 依赖注入
     * @param component 依赖的对象
     */
    public Decorator(Component component) {
        this.component = component;
    }

    /**
     * 转发请求给组件对象 这里可以做一些附加操作
     */
    @Override
    public void operation() {
        component.operation();
    }
}
