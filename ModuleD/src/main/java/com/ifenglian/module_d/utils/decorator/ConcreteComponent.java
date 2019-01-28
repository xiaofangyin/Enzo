package com.ifenglian.module_d.utils.decorator;

import com.enzo.commonlib.utils.common.LogUtil;

/**
 * 文 件 名: ConcreteComponent
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/2/2
 * 邮   箱: xiaofy@ifenglian.com
 */
public class ConcreteComponent extends Component {

    @Override
    public void operation() {
        //重写方法 做基本的操作
        LogUtil.e("ConcreteComponent operation");
    }
}
