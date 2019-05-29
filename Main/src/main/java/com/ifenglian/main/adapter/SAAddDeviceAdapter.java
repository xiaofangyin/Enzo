package com.ifenglian.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.enzo.commonlib.base.BaseRecyclerViewAdapter;
import com.enzo.commonlib.base.BaseViewHolder;
import com.enzo.commonlib.utils.common.LogUtil;
import com.ifenglian.flkit.FLPluginBaseCell;
import com.ifenglian.flkit.FLPluginBaseObject;
import com.ifenglian.flkit.FLPluginCellStyle;
import com.ifenglian.main.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 文 件 名: SAAddDeviceAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAAddDeviceAdapter extends BaseRecyclerViewAdapter<FLPluginBaseObject> {

    private Map<Integer, FLPluginBaseCell> mBaseCellCache;

    public SAAddDeviceAdapter() {
        mBaseCellCache = new HashMap<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sa_item_add_device, viewGroup, false);
        return new AddDeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.setUpView(mData.get(i), i, this);
    }

    private class AddDeviceHolder extends BaseViewHolder<FLPluginBaseObject> {

        private FrameLayout flContainer;

        AddDeviceHolder(View itemView) {
            super(itemView);
            flContainer = itemView.findViewById(R.id.sa_add_device_container);
        }

        @Override
        public void setUpView(FLPluginBaseObject model, int position, RecyclerView.Adapter adapter) {
            if (flContainer.getChildCount() != 0) {
                View view = flContainer.getChildAt(0);
                ((ViewGroup) view.getParent()).removeAllViews();
                mBaseCellCache.put(model.type, (FLPluginBaseCell) view);
            }

            final FLPluginBaseCell baseCell;
            if (mBaseCellCache.get(model.type) != null) {
                LogUtil.d("1 get base cell from cache...");
                baseCell = mBaseCellCache.remove(model.type);
            } else {
                LogUtil.d("2 get base cell from model build...");
                baseCell = model.buildCellWithStyle(FLPluginCellStyle.FLPluginCellStyleNormal);
            }
            if (baseCell != null) {
                baseCell.layoutWithModel(model);
                flContainer.addView(baseCell);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baseCell.cellPressed();
                    }
                });
            }
        }
    }
}
