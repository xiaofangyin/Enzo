package com.ifenglian.commonlib.widget.view.recyclerview.factory;

import android.view.View;

import com.ifenglian.commonlib.widget.view.recyclerview.holder.BaseViewHolder;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.Value1;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.Value2;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.Value3;

/**
 * 文 件 名: BaseViewTypeFactory
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public interface BaseViewTypeFactory {

    int type(Value1 type);

    int type(Value2 type);

    int type(Value3 type);

    BaseViewHolder createViewHolder(View v, int viewType);
}
