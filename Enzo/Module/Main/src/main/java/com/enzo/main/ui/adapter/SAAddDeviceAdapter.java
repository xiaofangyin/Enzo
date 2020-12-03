package com.enzo.main.ui.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enzo.commonlib.base.BaseRecyclerViewAdapter;
import com.enzo.commonlib.base.BaseViewHolder;
import com.enzo.commonlib.utils.common.LogUtil;
import com.enzo.flkit.plugin.FLPluginBaseCell;
import com.enzo.flkit.plugin.FLPluginBaseObject;
import com.enzo.flkit.plugin.FLPluginCellStyle;
import com.enzo.main.R;

import java.util.LinkedList;

/**
 * 文 件 名: SAAddDeviceAdapter
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-05-29
 * 邮   箱: xiaofangyinwork@163.com
 */
public class SAAddDeviceAdapter extends BaseRecyclerViewAdapter<FLPluginBaseObject> {

    private final SparseArray<LinkedList<FLPluginBaseCell>> mViewCache;

    public SAAddDeviceAdapter() {
        mViewCache = new SparseArray<>();
    }

    @NonNull
    @Override
    public BaseViewHolder<FLPluginBaseObject> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_item_add_device, viewGroup, false);
        return new AddDeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<FLPluginBaseObject> baseViewHolder, int i) {
        baseViewHolder.setUpView(mData.get(i), i, this);
    }

    private class AddDeviceHolder extends BaseViewHolder<FLPluginBaseObject> {

        private final FrameLayout flContainer;

        AddDeviceHolder(View itemView) {
            super(itemView);
            flContainer = itemView.findViewById(R.id.sa_add_device_container);
        }

        @Override
        public void setUpView(FLPluginBaseObject model, int position, RecyclerView.Adapter adapter) {
            FLPluginBaseCell baseCell;
            if (flContainer.getChildCount() != 0) {
                FLPluginBaseCell view = (FLPluginBaseCell) flContainer.getChildAt(0);
                if (view.getType() == model.type) {
                    LogUtil.d("1 view.getType() == model.type");
                    baseCell = view;
                } else {
                    LogUtil.d("2 view.getType() != model.type");
                    ((ViewGroup) view.getParent()).removeAllViews();
                    putCell(view);
                    baseCell = getCell(model.type);
                }
            } else {
                LogUtil.d("3 flContainer.getChildCount() = 0");
                baseCell = getCell(model.type);
            }

            if (baseCell == null) {
                LogUtil.d("4 model.buildCellWithStyle");
                baseCell = model.buildCellWithStyle(FLPluginCellStyle.FLPluginCellStyleNormal);
            }
            if (baseCell != null) {
                baseCell.layoutWithModel(model);
                if (baseCell.getParent() == null) {
                    flContainer.addView(baseCell);
                }
            }

            if (!model.animated) {
                model.animated = true;
                itemView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.main_anim_recycler_item_in));
            } else {
                itemView.clearAnimation();
            }
        }

        private void putCell(FLPluginBaseCell cell) {
            if (mViewCache.get(cell.getType()) == null) {
                LinkedList<FLPluginBaseCell> linkedList = new LinkedList<>();
                linkedList.add(cell);
                mViewCache.put(cell.getType(), linkedList);
            } else {
                mViewCache.get(cell.getType()).add(cell);
            }
        }

        private FLPluginBaseCell getCell(int type) {
            if (mViewCache.indexOfKey(type) >= 0) {
                return mViewCache.get(type).poll();
            } else {
                return null;
            }
        }
    }
}
