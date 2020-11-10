package com.enzo.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseRecyclerViewAdapter;
import com.enzo.commonlib.base.BaseViewHolder;
import com.enzo.main.R;
import com.enzo.main.config.SALeftMenuConfig;
import com.enzo.main.model.bean.LeftMenuChildBean;


/**
 * 文 件 名: LeftMenuChildAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SALeftMenuChildAdapter extends BaseRecyclerViewAdapter<LeftMenuChildBean> {

    private int currentMode;

    void setCurrentMode(int mode) {
        currentMode = mode;
    }

    @NonNull
    @Override
    public BaseViewHolder<LeftMenuChildBean> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.main_item_left_menu_child_layout, parent, false);
        return new LeftMenuChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<LeftMenuChildBean> holder, int position) {
        holder.setUpView(mData.get(position), position, this);
    }

    public static class LeftMenuChildHolder extends BaseViewHolder<LeftMenuChildBean> {

        private LinearLayout llLinearLayout;
        private ImageView ivLinearIcon;
        private TextView tvLinearName;

        private LinearLayout llGridLayout;
        private ImageView ivGridIcon;
        private TextView tvGridName;

        LeftMenuChildHolder(View itemView) {
            super(itemView);
            llLinearLayout = itemView.findViewById(R.id.left_menu_linear_layout);
            ivLinearIcon = itemView.findViewById(R.id.left_menu_linear_child_icon);
            tvLinearName = itemView.findViewById(R.id.left_menu_child_linear_name);

            llGridLayout = itemView.findViewById(R.id.left_menu_grid_layout);
            ivGridIcon = itemView.findViewById(R.id.left_menu_grid_child_icon);
            tvGridName = itemView.findViewById(R.id.left_menu_child_grid_name);
        }

        @Override
        public void setUpView(final LeftMenuChildBean model, int position, RecyclerView.Adapter adapter) {
            SALeftMenuChildAdapter childAdapter = (SALeftMenuChildAdapter) adapter;
            if (childAdapter.currentMode == 0) {
                llLinearLayout.setVisibility(View.VISIBLE);
                llGridLayout.setVisibility(View.GONE);
                ivLinearIcon.setImageResource(model.getIconWhite());
                tvLinearName.setText(model.getName());
            } else {
                llLinearLayout.setVisibility(View.GONE);
                llGridLayout.setVisibility(View.VISIBLE);
                ivGridIcon.setImageResource(model.getIconWhite());
                tvGridName.setText(model.getName());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SALeftMenuConfig.launch(view.getContext(),model);
                }
            });
        }
    }
}
