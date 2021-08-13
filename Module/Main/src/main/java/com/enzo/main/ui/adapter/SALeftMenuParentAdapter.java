package com.enzo.main.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseRecyclerViewAdapter;
import com.enzo.commonlib.base.BaseViewHolder;
import com.enzo.main.R;
import com.enzo.main.model.bean.LeftMenuChildBean;
import com.enzo.main.model.bean.LeftMenuParentBean;
import com.enzo.main.util.ExpandableViewHoldersUtil;
import com.enzo.main.util.SPUtils;

import java.util.Iterator;

/**
 * 文 件 名: LeftMenuParentAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/11/18
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SALeftMenuParentAdapter extends BaseRecyclerViewAdapter<LeftMenuParentBean> {

    private ExpandableViewHoldersUtil.KeepOneH<LeftMenuHolder> keepOne;

    public SALeftMenuParentAdapter() {
        keepOne = new ExpandableViewHoldersUtil.KeepOneH<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.main_item_left_menu_parent_layout, parent, false);
        return new LeftMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setUpView(mData.get(position), position, this);
    }

    public class LeftMenuHolder extends BaseViewHolder<LeftMenuParentBean> implements ExpandableViewHoldersUtil.Expandable {

        private View topView;
        private View bottomView;
        private ImageView ivIcon;
        private TextView tvName;
        private ImageView ivArrow;
        private RecyclerView recyclerView;
        private SALeftMenuChildAdapter childAdapter;

        LeftMenuHolder(View itemView) {
            super(itemView);
            topView = itemView.findViewById(R.id.left_menu_top_view);
            bottomView = itemView.findViewById(R.id.left_menu_bottom_view);
            ivIcon = itemView.findViewById(R.id.left_menu_parent_icon);
            tvName = itemView.findViewById(R.id.left_menu_parent_name);
            ivArrow = itemView.findViewById(R.id.left_menu_iv_expend);
            recyclerView = itemView.findViewById(R.id.left_menu_child_recycler_view);
            childAdapter = new SALeftMenuChildAdapter();
            recyclerView.setAdapter(childAdapter);
        }

        @Override
        public void setUpView(final LeftMenuParentBean model, int position, RecyclerView.Adapter adapter) {
            topView.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            bottomView.setVisibility(position == adapter.getItemCount() - 1 ? View.VISIBLE : View.GONE);
            ivIcon.setImageResource(model.getIconWhiteId());
            tvName.setText(model.getName());
            int mode = SPUtils.getDisplayMode(getContext());
            if (mode == 0) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            }
            childAdapter.setCurrentMode(mode);
            Iterator<LeftMenuChildBean> it = model.getChildList().iterator();
            while (it.hasNext()) {
                LeftMenuChildBean childBean = it.next();
                if (!childBean.isEnable()) {
                    it.remove();
                }
            }
            childAdapter.setNewData(model.getChildList());

            keepOne.bind(this, position);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    keepOne.toggle(LeftMenuHolder.this);
                }
            });
        }

        @Override
        public View getExpandView() {
            return recyclerView;
        }

        @Override
        public View getArrow() {
            return ivArrow;
        }
    }
}
