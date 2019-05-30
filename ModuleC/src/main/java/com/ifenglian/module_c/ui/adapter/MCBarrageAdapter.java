package com.ifenglian.module_c.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enzo.commonlib.widget.barrage.adapter.BaseBarrageAdapter;
import com.enzo.commonlib.widget.barrage.model.BarrageData;
import com.ifenglian.module_c.R;

/**
 * 文 件 名: MCBarrageAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-30
 * 邮   箱: xiaofangyin@360.cn
 */
public class MCBarrageAdapter extends BaseBarrageAdapter<BarrageData> {

    public MCBarrageAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemViewType(BarrageData barrageData) {
        return barrageData.getType();
    }

    @Override
    protected BarrageViewHolder<BarrageData> onCreateViewHolder(View root, int type) {
        if (type == 0 || type == 1) {
            return new TextViewHolder(root);
        } else {
            return new BigHolder(root);
        }
    }

    @Override
    public int getItemLayout(BarrageData barrageData) {
        switch (barrageData.getType()) {
            case 0:
                return R.layout.barrage_item_text;
            case 1:
                return R.layout.barrage_item_text_vip;
            default:
                return R.layout.barrage_item_big;
        }
    }

    public static class TextViewHolder extends BaseBarrageAdapter.BarrageViewHolder<BarrageData> {

        private TextView mContent;

        TextViewHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.content);
        }

        @Override
        protected void onBind(BarrageData data) {
            mContent.setText(data.getContent());
        }
    }

    public static class BigHolder extends BaseBarrageAdapter.BarrageViewHolder<BarrageData> {

        private ImageView mHeadView;
        private TextView mContent;

        BigHolder(View itemView) {
            super(itemView);
            mHeadView = itemView.findViewById(R.id.image);
            mContent = itemView.findViewById(R.id.content);
        }

        @Override
        protected void onBind(BarrageData data) {
            mContent.setText(data.getContent());
        }
    }
}
