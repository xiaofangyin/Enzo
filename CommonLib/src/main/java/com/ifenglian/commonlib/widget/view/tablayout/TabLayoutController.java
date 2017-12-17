package com.ifenglian.commonlib.widget.view.tablayout;

/**
 * 文 件 名: TabLayoutController
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/17
 * 邮   箱: xiaofy@ifenglian.com
 */
public interface TabLayoutController {
    void setData(String[] titles,int[][] iconRes);
    void setCurrentItem(int currentItem, boolean animate);

    void addMessageNum(int position, int messageNum);

    void showRedPoint(int position, boolean showRedPoint);

    void showTabLayout();

    void hideTabLayout();
}
