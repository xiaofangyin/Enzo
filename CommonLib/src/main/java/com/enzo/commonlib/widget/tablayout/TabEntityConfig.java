package com.enzo.commonlib.widget.tablayout;

import com.enzo.commonlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: TabEntityConfig
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/5/25
 * 邮   箱: xiaofangyinwork@163.com
 */
public class TabEntityConfig {

    public static List<TabEntity> getEntities() {
        List<TabEntity> list = new ArrayList<>();
        TabEntity entity1 = new TabEntity();
        entity1.setTitle("首页");
        entity1.setNormalColor(0xFFAAAAAA);
        entity1.setSelectedColor(0xFF04d1aa);
        entity1.setNormalImage(R.mipmap.icon_home_tab_1_normal);
        entity1.setSelectedImage(R.mipmap.icon_home_tab_1_selected);

        TabEntity entity2 = new TabEntity();
        entity2.setTitle("关注");
        entity2.setNormalColor(0xFFAAAAAA);
        entity2.setSelectedColor(0xFF04d1aa);
        entity2.setNormalImage(R.mipmap.icon_home_tab_2_normal);
        entity2.setSelectedImage(R.mipmap.icon_home_tab_2_selected);

        TabEntity entity3 = new TabEntity();
        entity3.setTitle("消息");
        entity3.setNormalColor(0xFFAAAAAA);
        entity3.setSelectedColor(0xFF04d1aa);
        entity3.setNormalImage(R.mipmap.icon_home_tab_4_normal);
        entity3.setSelectedImage(R.mipmap.icon_home_tab_4_selected);

        TabEntity entity4 = new TabEntity();
        entity4.setTitle("我");
        entity4.setNormalColor(0xFFAAAAAA);
        entity4.setSelectedColor(0xFF04d1aa);
        entity4.setNormalImage(R.mipmap.icon_home_tab_5_normal);
        entity4.setSelectedImage(R.mipmap.icon_home_tab_5_selected);

        list.add(entity1);
        list.add(entity2);
        list.add(entity3);
        list.add(entity4);
        return list;
    }
}
