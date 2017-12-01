package com.ifenglian.commonlib.base;

/**
 * 文 件 名: IBaseActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/28
 * 邮   箱: xiaofy@ifenglian.com
 */
public interface IBaseActivity {

    int getLayoutId();

    void initView();

    void initData();

    void initListener();
}
