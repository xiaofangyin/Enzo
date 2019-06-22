package com.enzo.module_c.model;

import com.enzo.commonlib.widget.barrage.model.BarrageData;

/**
 * 文 件 名: BarrageDataHelper
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-30
 * 邮   箱: xiaofangyin@360.cn
 */
public class BarrageDataHelper {

    public static BarrageData buildBarrageData(int i) {
        if (i % 3 == 0) {
            return new BarrageData("666666666666666", 0, i);
        } else if (i % 3 == 1) {
            return new BarrageData("这波操作不亏，50血极限反杀，我们还有机会！", 1, i);
        } else if (i % 3 == 2) {
            return new BarrageData("无敌的VN送了一辆宝马", 2, i);
        }
        return null;
    }

}
