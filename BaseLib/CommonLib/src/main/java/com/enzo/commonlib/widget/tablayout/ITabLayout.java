package com.enzo.commonlib.widget.tablayout;

import java.util.List;

/**
 * 文 件 名: ITabLayout
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/17
 * 邮   箱: xiaofangyinwork@163.com
 */
public interface ITabLayout {

    void initData(List<TabEntity> list);

    void setCurrentItem(int currentItem);

    int getCurrentItem();

    void addMessageNum(int position, int messageNum);

    void setMessageNum(int position, int messageNum);

    void resetMessageNum(int position);

    int getMessageNum(int position);

    void showRedPoint(int position);

    void hideRedPoint(int position);

    void showTabLayout();

    void hideTabLayout();
}
