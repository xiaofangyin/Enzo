package com.enzo.module_a.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseRecyclerViewAdapter;
import com.enzo.commonlib.base.BaseViewHolder;
import com.enzo.module_a.R;

/**
 * 文 件 名: MAHomeAdapter
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/9
 * 邮   箱: xiaofywork@163.com
 */
public class MAHomeAdapter extends BaseRecyclerViewAdapter<String> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ma_item_home_,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    private static class HomeViewHolder extends BaseViewHolder<String>{

        public HomeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setUpView(String model, int position, RecyclerView.Adapter adapter) {

        }
    }

}
