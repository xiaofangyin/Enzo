package com.enzo.module_d.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.module_d.R;
import com.enzo.module_d.model.bean.Star;

import java.util.List;

public class MDStarAdapter extends RecyclerView.Adapter<MDStarAdapter.StarViewHolder> {

    private final List<Star> starList;


    public MDStarAdapter(List<Star> starList) {
        this.starList = starList;
    }

    /**
     * 是否是组的第一个item
     */
    public boolean isGroupHeader(int position) {
        if (position == 0) {
            return true;
        } else {
            String currentGroupName = getGroupName(position);
            String preGroupName = getGroupName(position - 1);
            return !preGroupName.equals(currentGroupName);
        }
    }

    public String getGroupName(int position) {
        return starList.get(position).getGroundName();
    }


    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.md_rv_item_star, parent, false);
        return new StarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        holder.tv.setText(starList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return starList == null ? 0 : starList.size();
    }

    public static class StarViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_star);
        }
    }
}
