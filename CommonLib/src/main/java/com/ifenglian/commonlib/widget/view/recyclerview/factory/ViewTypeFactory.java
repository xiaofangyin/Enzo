package com.ifenglian.commonlib.widget.view.recyclerview.factory;

import android.view.View;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.BaseViewHolder;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.ViewHolderOne;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.ViewHolderThree;
import com.ifenglian.commonlib.widget.view.recyclerview.holder.ViewHolderTwo;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.Value1;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.Value2;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.Value3;

/**
 * 文 件 名: ViewTypeFactory
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class ViewTypeFactory implements BaseViewTypeFactory {

    private final int oneId = R.layout.viewtype1;
    private final int twoId = R.layout.viewtype2;
    private final int threeId = R.layout.viewtype3;

    @Override
    public int type(Value1 type) {
        return oneId;
    }

    @Override
    public int type(Value2 type) {
        return twoId;
    }

    @Override
    public int type(Value3 type) {
        return threeId;
    }

    @Override
    public BaseViewHolder createViewHolder(View v, int viewType) {
        BaseViewHolder viewHolder = null;
        if (viewType == oneId)
            viewHolder = new ViewHolderOne(v);
        else if (viewType == twoId)
            viewHolder = new ViewHolderTwo(v);
        else if (viewType == threeId)
            viewHolder = new ViewHolderThree(v);
        return viewHolder;
    }
}
