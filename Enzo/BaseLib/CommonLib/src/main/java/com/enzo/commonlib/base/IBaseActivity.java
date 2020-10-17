package com.enzo.commonlib.base;

import android.os.Bundle;

/**
 * 文 件 名: IBaseActivity
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/28
 * 邮   箱: xiaofangyinwork@163.com
 */
public interface IBaseActivity {

    int getStatusBarColor();

    int getLayoutId();

    void initHeader();

    void initView();

    void initData(Bundle savedInstanceState);

    void initListener();
}
