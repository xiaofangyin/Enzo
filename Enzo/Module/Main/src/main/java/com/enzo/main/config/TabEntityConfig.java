package com.enzo.main.config;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.enzo.commonlib.widget.tablayout.TabEntity;
import com.enzo.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: TabEntityConfig
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/5/25
 * 邮   箱: xiaofangyinwork@163.com
 */
public class TabEntityConfig {

    public static List<TabEntity> getEntities(Context context) {
        List<TabEntity> list = new ArrayList<>();
        TabEntity entity1 = new TabEntity();
        entity1.setTitle("首页");
        entity1.setNormalColor(ContextCompat.getColor(context,R.color.color_666));
        entity1.setSelectedColor(ContextCompat.getColor(context,R.color.color_666));
        entity1.setNormalImage(R.mipmap.comui_tab_home);
        entity1.setSelectedImage(R.mipmap.comui_tab_home_selected);

        TabEntity entity2 = new TabEntity();
        entity2.setTitle("鱼塘");
        entity2.setNormalColor(ContextCompat.getColor(context,R.color.color_666));
        entity2.setSelectedColor(ContextCompat.getColor(context,R.color.color_666));
        entity2.setNormalImage(R.mipmap.comui_tab_pond);
        entity2.setSelectedImage(R.mipmap.comui_tab_pond_selected);

        TabEntity entity3 = new TabEntity();
        entity3.setTitle("消息");
        entity3.setNormalColor(ContextCompat.getColor(context,R.color.color_666));
        entity3.setSelectedColor(ContextCompat.getColor(context,R.color.color_666));
        entity3.setNormalImage(R.mipmap.comui_tab_message);
        entity3.setSelectedImage(R.mipmap.comui_tab_message_selected);

        TabEntity entity4 = new TabEntity();
        entity4.setTitle("我的");
        entity4.setNormalColor(ContextCompat.getColor(context,R.color.color_666));
        entity4.setSelectedColor(ContextCompat.getColor(context,R.color.color_666));
        entity4.setNormalImage(R.mipmap.comui_tab_person);
        entity4.setSelectedImage(R.mipmap.comui_tab_person_selected);

        list.add(entity1);
        list.add(entity2);
        list.add(entity3);
        list.add(entity4);
        return list;
    }
}
