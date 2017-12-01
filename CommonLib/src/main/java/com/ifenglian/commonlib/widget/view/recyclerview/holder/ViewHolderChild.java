package com.ifenglian.commonlib.widget.view.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.ValueChild;

/**
 * 文 件 名: ViewHolderChild
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class ViewHolderChild extends BaseViewHolder<ValueChild> {

    public ViewHolderChild(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(ValueChild model, int position, RecyclerView.Adapter adapter) {
        TextView tv = (TextView) findView(R.id.tv_child);
        tv.setText(model.getAddress());
    }
}
