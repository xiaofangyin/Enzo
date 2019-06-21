package com.enzo.commonlib.base;

import android.os.Bundle;
import android.view.View;

/**
 * 文 件 名: IBaseFragment
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/11/28
 * 邮   箱: xiaofangyinwork@163.com
 */
public interface IBaseFragment {

    int getLayoutId();

    void initView(View rootView);

    void initData(Bundle savedInstanceState);

    void initListener(View rootView);
}
