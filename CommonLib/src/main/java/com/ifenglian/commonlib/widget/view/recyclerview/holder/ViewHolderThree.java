package com.ifenglian.commonlib.widget.view.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ifenglian.commonlib.R;
import com.ifenglian.commonlib.widget.view.recyclerview.viewType.Value3;

/**
 * 文 件 名: ViewHolderThree
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/4/23
 * 邮   箱: xiaofy@ifenglian.com
 */
public class ViewHolderThree extends BaseViewHolder<Value3> {

    public ViewHolderThree(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final Value3 model, int position, RecyclerView.Adapter adapter) {
        final TextView tv = (TextView) findView(R.id.tv3);
        tv.setText(model.getAge());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tv.getContext(), model.getAge(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
