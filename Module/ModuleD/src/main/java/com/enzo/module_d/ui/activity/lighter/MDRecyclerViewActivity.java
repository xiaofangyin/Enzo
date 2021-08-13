package com.enzo.module_d.ui.activity.lighter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseActivity;
import com.enzo.commonlib.utils.toast.ToastUtil;
import com.enzo.commonlib.widget.headerview.HeadWidget;
import com.enzo.commonlib.widget.lighter.Lighter;
import com.enzo.commonlib.widget.lighter.parameter.Direction;
import com.enzo.commonlib.widget.lighter.parameter.LighterParameter;
import com.enzo.module_d.R;

public class MDRecyclerViewActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.md_activity_lighter_recyclerview;
    }

    @Override
    public void initHeader() {
        super.initHeader();
        HeadWidget headWidget = findViewById(R.id.header_widget);
        headWidget.setTitle("引导");
        headWidget.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {
        MDLighterHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));
        initListView();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ToastUtil.show("Click to show highlight(点击显示高亮)~", Toast.LENGTH_LONG);
    }

    private void initListView() {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        Adapter adapter = new Adapter();
        adapter.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showGuide(view);
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void showGuide(View highlightedView) {
        if (highlightedView == null) {
            return;
        }

        Lighter.with((ViewGroup) findViewById(R.id.root))
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedView(highlightedView)
                        .setTipLayoutId(R.layout.md_layout_lighter_tip_5)
                        .setTipViewRelativeDirection(Direction.TOP)
                        .build())
                .show();
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private AdapterView.OnItemClickListener itemClickListener;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.md_layout_lighter_item_list, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
            viewHolder.imageView.setImageResource(MDLighterHelper.sPictureList.get(i));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(null, v, i, v.getId());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return MDLighterHelper.sPictureList.size();
        }

        public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
        }
    }
}
