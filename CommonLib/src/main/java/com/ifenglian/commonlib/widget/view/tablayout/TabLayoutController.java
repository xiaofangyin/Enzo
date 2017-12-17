package com.ifenglian.commonlib.widget.view.tablayout;

/**
 * 文 件 名: TabLayoutController
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/17
 * 邮   箱: xiaofy@ifenglian.com
 */
public interface TabLayoutController {
    void initData(String[] titles, int[][] iconRes);

    void setCurrentItem(int currentItem, boolean animate);

    void addMessageNum(int position, int messageNum);

    void setMessageNum(int position, int messageNum);

    void resetMessageNum(int position);

    void showRedPoint(int position);

    void hideRedPoint(int position);

    void showTabLayout();

    void hideTabLayout();
}
